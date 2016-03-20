package org.aakretech.istgame.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.aakretech.istgame.ai.SimpleAI;

public class Game {

    private int goalScore;
    private int tileColourFavour; // random number between minFavour and maxFavour used for determining tile colour distribution
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

        currentPlayerIndex = prng.nextInt(players.size());

        startNewRound(true);
    }

    /**
     * Generates a new empty board and randomly sets the tileColourFavour
     *
     * @return the newly generated board
     */
    private ArrayList<Integer> generateEmptyBoard() {
        ArrayList<Integer> newBoard = new ArrayList<>(this.boardHeight * this.boardWidth);
        for (int i = 0; i < this.boardHeight * this.boardWidth; i++) {
            newBoard.add(-1);
        }

        tileColourFavour = prng.nextInt(maxFavour - minFavour) + minFavour;

        return newBoard;
    }

    /**
     * Guesses which colour is dominant, calculates scores and starts the next round
     *
     * @param guess the colour (0 = blue, 1 = yellow)
     */
    public void guess(int guess) {
        int score = getScore();

        // uncover entire board
        for (int i = 0; i < board.size(); i++) {
            uncoverCell(i);
        }

        if (getUncoveredTileCount(guess) > board.size() / 2) {
            getCurrentPlayer().addScore(score);
            startNewRound(true);
        } else {
            getCurrentPlayer().substractScore(score);
            startNewRound(false);
        }
    }

    public void startNewRound(boolean playerGuessedCorrect) {
        board = generateEmptyBoard();

        if(! playerGuessedCorrect) {
            incrementPlayerIndex();
        }

        // TODO: if AI's turn at new round, make it move
        // TODO: seem to remember this code would break pvp games?? TEST
        /* if(! getCurrentPlayer().isHuman()) {
         aiMove();
         }  */
    }

    /**
     * Cause the AI player to make it's move
     */
    public void aiMove() {
        SimpleAI ai = (SimpleAI) getCurrentPlayer();
        if (ai.getGame() == null) {
            ai.setGame(this);
        }

        //todo: could an AI try to

        int guess = ai.guess();
        if (guess == -1) {
            uncoverCell(ai.flipTile());
            incrementPlayerIndex();
        } else {
            guess(guess);
        }
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
     * @param value the colour (0 = blue, 1 = yellow)
     *
     * @return number of uncovered tiles of specified colour
     */
    public int getUncoveredTileCount(int value) {
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

    public List<Integer> getBoard() {
        return board;
    }

    public Integer getCell(int cellIndex) {
        return board.get(cellIndex);
    }

    public void uncoverCell(int cellIndex) {
        if (board.get(cellIndex) == -1) {
            int cellValue = 0;
            if (prng.nextInt(100) > tileColourFavour) {
                cellValue = 1;
            }
            board.set(cellIndex, cellValue);
        }
    }

    public void incrementPlayerIndex() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public boolean isCurrentPlayerHuman() { return getCurrentPlayer().isHuman(); }

    public boolean isCellUncovered(int cellIndex) {
        return board.get(cellIndex) != -1;
    }

    public int getBoardSize() {
        return board.size();
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