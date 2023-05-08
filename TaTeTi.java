package com.example.ejemplo01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaTeTi {
    private final int BOARD_SIZE = 3;
    private Cell[][] board = new Cell[BOARD_SIZE][BOARD_SIZE];
    private Player currentPlayer;
    private String player1Name;
    private final String player2Name = "Computadora";
    private Random random = new Random();

    public TaTeTi(String player1Name) {
        this.player1Name = player1Name;
        initBoard();
        currentPlayer = Player.X;
    }

    public void initBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    public void switchCurrentPlayer() {
        if (currentPlayer == Player.X) {
            currentPlayer = Player.O;
        } else {
            currentPlayer = Player.X;
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Cell getCell(int row, int col) {
        return board[row - 1][col - 1];
    }

    public GameState getGameState() {
        // Verificar si algún jugador ha ganado
        for (int i = 0; i < BOARD_SIZE; i++) {
            // Verificar filas
            if (board[i][0].getState() == board[i][1].getState() && board[i][1].getState() == board[i][2].getState() && board[i][0].getState() != CellState.EMPTY) {
                return board[i][0].getState() == CellState.X ? GameState.X_WON : GameState.O_WON;
            }

            // Verificar columnas
            if (board[0][i].getState() == board[1][i].getState() && board[1][i].getState() == board[2][i].getState() && board[0][i].getState() != CellState.EMPTY) {
                return board[0][i].getState() == CellState.X ? GameState.X_WON : GameState.O_WON;
            }
        }

        // Verificar diagonales
        if (board[0][0].getState() == board[1][1].getState() && board[1][1].getState() == board[2][2].getState() && board[0][0].getState() != CellState.EMPTY) {
            return board[0][0].getState() == CellState.X ? GameState.X_WON : GameState.O_WON;
        }
        if (board[0][2].getState() == board[1][1].getState() && board[1][1].getState() == board[2][0].getState() && board[0][2].getState() != CellState.EMPTY) {
            return board[0][2].getState() == CellState.X ? GameState.X_WON : GameState.O_WON;
        }

        // Verificar si hay empate
        boolean emptyCellExists = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].getState() == CellState.EMPTY) {
                    emptyCellExists = true;
                    break;
                }
            }

        }
        if (!emptyCellExists) {
            return GameState.TIE;
        }

        // Si no hay ganador ni empate, el juego sigue en progreso
        return GameState.IN_PROGRESS;
    }

    public boolean play(int row, int col) {
        if (board[row - 1][col - 1].getState() != CellState.EMPTY) {
            return false;
        }
        board[row - 1][col - 1].setState(currentPlayer.getCellState());
        switchCurrentPlayer();
        return true;
    }

    public boolean playComputer() {
        List<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].getState() == CellState.EMPTY) {
                    emptyCells.add(board[i][j]);
                }
            }
        }
        if (emptyCells.isEmpty()) {
            return false;
        }
        Cell randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
        randomCell.setState(currentPlayer.getCellState());
        switchCurrentPlayer();
        return true;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public enum GameState {
        IN_PROGRESS, X_WON, O_WON, TIE
    }

    public enum Player {
        X(CellState.X), O(CellState.O);

        private final CellState cellState;

        Player(CellState cellState) {
            this.cellState = cellState;
        }

        public CellState getCellState() {
            return cellState;
        }
    }

    public enum CellState {
        X, O, EMPTY
    }

    public static class Cell {
        private int row;
        private int col;
        private CellState state;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            this.state = CellState.EMPTY;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public CellState getState() {
            return state;
        }

        public void setState(CellState state) {
            this.state = state;
        }
    }
}
