package com.example.ejemplo01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;



public class GameActivity extends AppCompatActivity {

    private Button[] buttons;
    private int[] board;

    private int player = 1;
    private int computer = 2;

    private int empty = 0;

    private boolean gameActive = true;

    private int[][] winningPositions = new int[][]{{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        buttons = new Button[9];
        board = new int[9];

        for(int i = 0; i < 9; i++) {
            String buttonId = "button_" + i;
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = findViewById(resourceId);
            board[i] = empty;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Button) v).getText().toString().equals("")) {
                        int tag = Integer.parseInt(v.getTag().toString());
                        updateBoard(tag, player);
                        checkWinner();
                        if (gameActive) {
                            computerMove();
                        }
                    }
                }
            });
        }
    }

    private void computerMove() {
        int bestPosition = findBestMove();
        updateBoard(bestPosition, computer);
        checkWinner();
    }

    private int findBestMove() {
        int bestMove = -1;
        int bestScore = -1000;
        for (int i = 0; i < 9; i++) {
            if (board[i] == empty) {
                board[i] = computer;
                int score = minimax(board, 0, false);
                board[i] = empty;
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    private int minimax(int[] board, int depth, boolean isMaximizing) {
        int result = checkGameState(board);
        if (result != -2) {
            return result;
        }
        if (isMaximizing) {
            int bestScore = -1000;
            for (int i = 0; i < 9; i++) {
                if (board[i] == empty) {
                    board[i] = computer;
                    int score = minimax(board, depth + 1, false);
                    board[i] = empty;
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int i = 0; i < 9; i++) {
                if (board[i] == empty) {
                    board[i] = player;
                    int score = minimax(board, depth + 1, true);
                    board[i] = empty;
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    private void updateBoard(int position, int player) {
        board[position] = player;
        if (player == 1) {
            buttons[position].setText("X");
        } else {
            buttons[position].setText("O");
        }
    }


        private void checkWinner() {
            int winner = -1; // -1 significa que aún no hay un ganador

            // Comprobamos las filas
            for (int i = 0; i < 9; i += 3) {
                if (buttons[i].getText().equals(buttons[i + 1].getText()) &&
                        buttons[i].getText().equals(buttons[i + 2].getText()) &&
                        !buttons[i].getText().equals("")) {
                    winner = i;
                    break;
                }
            }

            // Comprobamos las columnas
            if (winner == -1) {
                for (int i = 0; i < 3; i++) {
                    if (buttons[i].getText().equals(buttons[i + 3].getText()) &&
                            buttons[i].getText().equals(buttons[i + 6].getText()) &&
                            !buttons[i].getText().equals("")) {
                        winner = i;
                        break;
                    }
                }
            }

            // Comprobamos las diagonales
            if (winner == -1) {
                if (buttons[0].getText().equals(buttons[4].getText()) &&
                        buttons[0].getText().equals(buttons[8].getText()) &&
                        !buttons[0].getText().equals("")) {
                    winner = 0;
                } else if (buttons[2].getText().equals(buttons[4].getText()) &&
                        buttons[2].getText().equals(buttons[6].getText()) &&
                        !buttons[2].getText().equals("")) {
                    winner = 2;
                }
            }

            // Si hay un ganador, actualizamos la UI y terminamos el juego
            if (winner != -1) {
                disableButtons();
                if (winner == 0) {
                    Toast.makeText(getApplicationContext(), "X es el ganador!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "O es el ganador!", Toast.LENGTH_SHORT).show();
                }
            }

            // Si no hay un ganador y se han realizado 9 jugadas, entonces el juego termina en empate
            if (winner == -1 && turn_count == 9) {
                Toast.makeText(getApplicationContext(), "Empate!", Toast.LENGTH_SHORT).show();
            }
        }
