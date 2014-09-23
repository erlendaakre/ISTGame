package org.aakretech.istgame;

import org.aakretech.istgame.ai.SimpleAI;
import org.aakretech.istgame.gui.GameWindow;
import org.aakretech.istgame.logic.Game;
import org.aakretech.istgame.logic.Player;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	    Player p1 = new Player("P1");
        //Player p2 = new Player("P2");
        Player p2 = new SimpleAI();

        List<Player> players = new ArrayList<Player>(2);
        players.add(p1);
        players.add(p2);

        Game g = new Game(5,5, players, 250, 20, 80);

        ((SimpleAI)p2).setGame(g);

        GameWindow gui = new GameWindow(g);
        gui.pack();
        gui.setVisible(true);
    }
}
