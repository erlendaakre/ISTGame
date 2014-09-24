package org.aakretech.istgame.ai;

/**
 * Simple AI player that never guesses, but only reveals random boxes.
 * Used as opponent for training other AI players.
 * 
 * @author Erlend Aakre
 */
public class FlipOnlyAI extends SimpleAI {

    @Override
    public int guess() {
        return -1;
    }
}
