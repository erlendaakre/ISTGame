package org.aakretech.istgame.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.aakretech.istgame.logic.Game;
import org.aakretech.istgame.logic.Player;

/**
 * Simple AI player that uses a basic formula to play the game
 * 
 * @author Erlend Aakre
 */
public class SimpleAI extends Player {

    protected Game game;
    protected Random prng = new Random();

    /**
     * Creates a new Simple AI
     */
    public SimpleAI() {
        super("SMPL");
        this.human = false;
    }

    /**
     * Creates a new Simple AI
     * @param name the name of the AI
     */
    public SimpleAI(String name) {
        super(name);
        this.human = false;
    }

    /**
     * Guesses which value is most common in the game
     * @return  the value to guess, or -1 to not guess
     */
    public int guess() {
        int guess = -1;
        if (game.getPercentageUncovered() > 15 + prng.nextInt(30)) {
            guess = 1;
            if (game.getUncoveredTileCount(0) > game.getUncoveredTileCount(1)) {
                guess = 0;
            }
        }
        return guess;
    }

    /**
     * chooses (completely by random) a box to reveal in the game
     * @return the index of a box to reveal
     */
    public int flipTile() {
        ArrayList<Integer> indexes = new ArrayList<Integer>(game.getBoardSize());
        for (int i = 0; i < game.getBoardSize(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);
        for (int i : indexes) {
            if (!game.isCellUncovered(i)) {
                return i;
            }
        }
        throw new Error("AI did not find a legal tile to flip");
    }

    /**
     * Sets the game this AI is playing
     * @param game the game to play
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets the game this AI is playing
     * @return the game in play
     */
    public Game getGame() {
        return game;
    }
}