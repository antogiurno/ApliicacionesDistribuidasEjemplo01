package com.example.ejemplo01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] button = new Button[3][3];
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    private int player1Points;
    private int player2Points;

    private boolean player1Turn = true;

    private int roundCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                button[i][j] = findViewById(resID);
                button[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;

            if(!player1Turn) {
                makeMove();
            }
        }
    }

    private void makeMove() {
        int[] move = minimax(2, "O");
        button[move[0]][move[1]].setText("O");
        player1Turn = true;
        roundCount++;

        if (checkForWin()) {
            player2Wins();
        } else if (roundCount == 9) {
            draw();
        }
    }

    private int[] minimax(int depth, String player) {
        if (checkForWin() && player.equals("O")) {
            return new int[]{-1, -1, 1};
        } else if (checkForWin() && player.equals("X")) {
            return new int[]{-1, -1, -1};
        } else if (roundCount == 9) {
            return new int[]{-1, -1, 0};
        }

        int[] bestMove = new int[3];

        if (player.equals("O")) {
            bestMove[2] = -1000;
