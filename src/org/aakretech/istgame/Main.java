package org.aakretech.istgame;

import java.util.ArrayList;
import java.util.List;
import org.aakretech.istgame.ai.SimpleAI;
import org.aakretech.istgame.gui.GameWindow;
import org.aakretech.istgame.gui.SplashPanel;
import org.aakretech.istgame.logic.Game;
import org.aakretech.istgame.logic.Player;

import javax.swing.*;

/**
 * Main class, creates and displays the graphical user interface.
 *
 * @author Erlend Aakre
 */
public class Main {

    public static void main(String[] args) {

        SplashPanel splashPanel = new SplashPanel();
        JFrame splashFrame = new JFrame();
        splashFrame.add(splashPanel);
        splashFrame.pack();

        Thread splashAnimThread = new Thread(splashPanel);
        splashAnimThread.start();
        splashFrame.setVisible(true);

        /*
        Player p1 = new Player("P1");
        //Player p2 = new Player("P2");
        Player p2 = new SimpleAI();


        List<Player> players = new ArrayList<Player>(2);
        players.add(p1);
        players.add(p2);

        Game g = new Game(5, 5, players, 250, 20, 80);

        ((SimpleAI) p2).setGame(g);

        GameWindow gui = new GameWindow(g);
        gui.pack();
        gui.setVisible(true);
        */
    }
}