package org.aakretech.istgame.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.aakretech.istgame.ai.SimpleAI;

public class Game {

    private int goalScore;
    private int favour;
    private int maxFavour;
    private int minFavour;
    private List<Integer> board;
    private List<Player> players;
    private int currentPlayerIndex;
    private int boardWidth;
    private int boardHeight;
    private Random prng;

    public Game(int boardWidth, int boardHeight, List<Player> players, int goalScore, int minFavour, int maxFavour) {
        this.maxFavour = maxFavour;
        this.minFavour = minFavour;
        this.goalScore = goalScore;
        this.players = players;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        prng = new Random();

        currentPlayerIndex = -1;

        resetGame();
    }

    private ArrayList<Integer> generateBoard() {

        ArrayList<Integer> newBoard = new ArrayList<Integer>(this.boardHeight * this.boardWidth);
        for (int i = 0; i < this.boardHeight * this.boardWidth; i++) {
            newBoard.add(-1);
        }
        return newBoard;
    }

    /**
     * Guesses which colour is dominant
     *
     * @param guess the colour (0 = blue, 1 = yellow)
     */
    public void guess(int guess) {
        int score = getScore();

        // uncover entire board
        for (int i = 0; i < board.size(); i++) {
            uncoverCell(i);
        }

        if (countUncovered(guess) > board.size() / 2) {
            getCurrentPlayer().addScore(score);
        } else {
            getCurrentPlayer().substractScore(score);
        }

        resetGame();
    }

    public void resetGame() {
        board = generateBoard();
        favour = prng.nextInt(maxFavour - minFavour) + minFavour;

        if (currentPlayerIndex == -1) {
            currentPlayerIndex = prng.nextInt(players.size());
        } else {
            /*if(!incrementPlayerIndex()) {
             aiMove();
             }  */
        }

        /* if(! getCurrentPlayer().isHuman()) {
         aiMove();
         }  */
    }

    public void aiMove() {
        SimpleAI ai = (SimpleAI) getCurrentPlayer();
        if (ai.getGame() == null) {
            ai.setGame(this);
        }

        int guess = ai.guess();
        if (guess == -1) {
            uncoverCell(ai.flipTile());
            incrementPlayerIndex();
        } else {
            guess(guess);
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public double getPercentageUncovered() {
        int total = board.size();
        int uncovered = 0;
        for (int i : board) {
            if (i != -1) {
                uncovered++;
            }
        }
        return (100D / total) * uncovered;
    }

    /**
     * Counts the number of uncovered tiles for a specific colour
     *
     * @param value the colour (0 = blue, 1 = yellow)   * @param value
     *
     * @return number of uncovered tiles of specified colour
     */
    public int countUncovered(int value) {
        int res = 0;
        for (int i : board) {
            if (i == value) {
                res++;
            }
        }
        return res;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getScore() {
        return 100 - (int) getPercentageUncovered();
    }

    public int getBoardSize() {
        return board.size();
    }

    public List<Integer> getBoard() {
        return board;
    }

    public Integer getCell(int cellIndex) {
        return board.get(cellIndex);
    }

    public void uncoverCell(int cellIndex) {
        if (board.get(cellIndex) == -1) {
            int cellValue = 0;
            if (prng.nextInt(100) > favour) {
                cellValue = 1;
            }
            board.set(cellIndex, cellValue);
        }
    }

    public boolean incrementPlayerIndex() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
        return getCurrentPlayer().isHuman();
    }

    public boolean isCellUncovered(int cellIndex) {
        return board.get(cellIndex) != -1;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getGoalScore() {
        return goalScore;
    }
}