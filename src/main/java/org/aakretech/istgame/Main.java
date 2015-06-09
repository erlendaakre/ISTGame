package org.aakretech.istgame;

import org.aakretech.istgame.gui.GameWindow;

/**
 * Main class, launches the game window and displays the splash/start screen
 *
 * @author Erlend Aakre
 * @author www.aakretech.org
 */
public class Main {

    public static void main(String[] args) {

        GameWindow gui = new GameWindow();
        gui.showSplashScreen();
    }
}