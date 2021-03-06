package org.aakretech.istgame.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import org.aakretech.istgame.logic.Game;

/**
 * Listener for clicking on the tiles of the board
 * 
 * @author Erlend Aakre
 */
public class BoardMouseListener extends MouseAdapter {

    private Game game;
    private GameWindow gameWindow;

    /**
     * Creates a new listener for the game board
     * @param game the game in play
     * @param gameWindow the main game window being used to display the game
     */
    public BoardMouseListener(Game game, GameWindow gameWindow) {
        super();
        this.game = game;
        this.gameWindow = gameWindow;
    }

    /**
     * Handles mouse clicks and translates them into an index click and taking
     * the appropriate action
     * 
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mouseClicked(e);

        int indexClicked = findIndexClicked(e.getX(), e.getY());
        if (indexClicked != -1 && !game.isCellUncovered(indexClicked)) {
            game.uncoverCell(indexClicked);
            game.incrementPlayerIndex();

            gameWindow.checkForWinner();
            gameWindow.getControlPanel().updateScoreText(game.getScore());
            gameWindow.getControlPanel().setCurrentPlayerIndicator(game.getCurrentPlayer());
            gameWindow.getBoardPanel().repaint();


            if (!game.isCurrentPlayerHuman()) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200 + (int) (Math.random() * 300));
                        } catch (InterruptedException e1) { }

                        game.aiMove();         // TODO: why the seven hells is this called from the mouse listener?????
                        gameWindow.checkForWinner();
                        gameWindow.getControlPanel().updateScoreText(game.getScore());
                        gameWindow.getControlPanel().setCurrentPlayerIndicator(game.getCurrentPlayer());
                        gameWindow.getBoardPanel().repaint();

                    }
                });
            }
        }
    }

    /**
     * finds the index of the clicked box by the mouse click location
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the index of the box clicked, or -1 if click was not on a box
     */
    private int findIndexClicked(int x, int y) {
        for (Map.Entry<Integer, Point> entry : gameWindow.getBoardPanel().getBoardIndexToScreenCoordinateMap().entrySet()) {
            if (x >= entry.getValue().getX() && x <= entry.getValue().getX() + BoardPanel.CELL_SIZE
                    && y >= entry.getValue().getY() && y <= entry.getValue().getY() + BoardPanel.CELL_SIZE) {
                return entry.getKey();
            }
        }
        return -1;
    }
}