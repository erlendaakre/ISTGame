package org.aakretech.istgame.logic;

/**
 * Represents a player of the game
 *
 * @author Erlend Aakre
 */
public class Player {

    protected String name;
    private int score;
    protected boolean human;

    /**
     * Creates a new named player
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.human = true;
    }

    /**
     * Gets the player name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the current score of the player
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds score to this player when guessing correctly
     * @param score score to add
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Removes score from this player if guessing incorrectly
     * @param score score to remove
     */
    public void substractScore(int score) {
        this.score -= score;
    }

    /**
     * Resets the score of this player
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * Checks if this player is human or computer
     * @return true if human player, false if AI
     */
    public boolean isHuman() {
        return human;
    }
}