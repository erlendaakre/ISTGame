package org.aakretech.istgame.gui;

import org.aakretech.istgame.logic.Game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class BoardMouseListener extends MouseAdapter {

    private Game game;
    private GameWindow gameWindow;

    public BoardMouseListener(Game game, GameWindow gameWindow) {
        super();
        this.game = game;
        this.gameWindow = gameWindow;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mouseClicked(e);

        int indexClicked = findIndexClicked(e.getX(), e.getY());
        if(indexClicked != -1 && !game.isCellUncovered(indexClicked)) {
            game.uncoverCell(indexClicked);
            final boolean humanIsNext = game.incrementPlayerIndex();

            gameWindow.checkWin();
            gameWindow.getControlPanel().updateText(game.getScore());
            gameWindow.getControlPanel().setCurrentPlayer(game.getCurrentPlayer());
            gameWindow.getBoardPanel().repaint();


            if(!humanIsNext) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300 + (int)(Math.random()*500));
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }


                        game.aiMove();
                        gameWindow.checkWin();
                        gameWindow.getControlPanel().updateText(game.getScore());
                        gameWindow.getControlPanel().setCurrentPlayer(game.getCurrentPlayer());
                        gameWindow.getBoardPanel().repaint();

                }
            });
            }
        }
    }

    private int findIndexClicked(int x, int y) {
        for (Map.Entry<Integer, Point> entry : gameWindow.getBoardPanel().getBoardIndexToScreenCoordinateMap().entrySet()) {
            if(x >= entry.getValue().getX() && x <= entry.getValue().getX() + gameWindow.getBoardPanel().CELLSIZE &&
                    y >= entry.getValue().getY() && y <= entry.getValue().getY() + gameWindow.getBoardPanel().CELLSIZE) {
                return entry.getKey();
            }
        }
        return -1;
    }
}