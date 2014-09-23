package org.aakretech.istgame.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonListener extends MouseAdapter {

    private GameWindow gameWindow;

    public ButtonListener(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void mousePressed(MouseEvent e) {
        int guess = (Integer)((JLabel)e.getSource()).getClientProperty("guess");
        System.out.println("PLAYER " + gameWindow.getGame().getCurrentPlayer().getName() + " GUESSED " + guess);
        gameWindow.getGame().guess(guess);
        gameWindow.getBoardPanel().guessed();
        gameWindow.getControlPanel().guessed();
        gameWindow.checkWin();
    }
}