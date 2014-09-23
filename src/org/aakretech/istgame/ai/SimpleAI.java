package org.aakretech.istgame.ai;

import org.aakretech.istgame.logic.Game;
import org.aakretech.istgame.logic.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimpleAI extends Player {
    protected Game game;
    protected Random prng = new Random();

    public SimpleAI() {
        super("SMPL");
        this.human = false;
    }

    // Called first
    public int guess() {
        int guess = -1;
        if(game.getPercentageUncovered() > 15 + prng.nextInt(30)) {
            guess = 1;
            if(game.countUncovered(0) > game.countUncovered(1)) {
                guess = 0;
            }
        }
        return guess;
    }

    // Called if guess() returns -1
    public int flipTile() {
        ArrayList<Integer> indexes = new ArrayList<Integer>(game.getBoardSize());
        for(int i = 0; i < game.getBoardSize(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);
        for(int i : indexes) {
            if(! game.isCellUncovered(i)) {
                return i;
            }
        }
        throw new Error("AI did not find a legal tile to flip");
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}