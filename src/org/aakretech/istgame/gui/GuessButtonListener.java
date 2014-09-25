package org.aakretech.istgame.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * Listener that registers clicking on the Guess buttons
 *
 * @author Erlend Aakre
 */
public class GuessButtonListener extends MouseAdapter {

    private GameWindow gameWindow;

    /**
     * Creates a new listener
     *
     * @param gameWindow the main game window
     */
    public GuessButtonListener(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    /**
     * called to handle mouse presses on a guess button
     *
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int guess = (Integer) ((JLabel) e.getSource()).getClientProperty("guess");
        System.out.println("PLAYER " + gameWindow.getGame().getCurrentPlayer().getName() + " GUESSED " + guess);
        gameWindow.getGame().guess(guess);
        gameWindow.getBoardPanel().guessed();
        gameWindow.getControlPanel().guessed();
        gameWindow.checkWin();
    }
}