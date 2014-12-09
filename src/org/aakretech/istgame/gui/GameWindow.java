package org.aakretech.istgame.gui;

import java.awt.*;
import javax.swing.*;
import org.aakretech.istgame.logic.Game;
import org.aakretech.istgame.logic.Player;

/**
 * The game window holding all the GUI components
 * 
 * @author Erlend Aakre
 */
public class GameWindow extends JFrame {

    private Game game;
    public static final GameFont GAMEFONT = new GameFont();
    private ControlPanel controlPanel;
    private BoardPanel gamePanel;

    /**
     * Displays the start / splash screen
     */
    public void initSplash() {
        getContentPane().removeAll();
        SplashPanel splashPanel = new SplashPanel(this);

        getContentPane().add(splashPanel);

        Thread splashAnimThread = new Thread(splashPanel);
        splashAnimThread.start();

        pack();
        setVisible(true);
    }

    /**
     * Displays the actual game
     *
     * @param game the game to display
     */
    public void initGame(Game game) {
        getContentPane().removeAll();
        this.game = game;

        getContentPane().setLayout(new BorderLayout());

        controlPanel = new ControlPanel(this);
        getContentPane().add(controlPanel, BorderLayout.NORTH);

        gamePanel = new BoardPanel(game);
        gamePanel.addMouseListener(new BoardMouseListener(game, this));
        getContentPane().add(gamePanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread animThread = new Thread(gamePanel);
        animThread.start();
    }

    /**
     * Checks the game state to see if there is a winner, if so the winner panel is displayed
     */
    public void checkWin() {
        for (Player p : game.getPlayers()) {
            if (p.getScore() >= game.getGoalScore()) {
                controlPanel.gameComplete();
                remove(gamePanel);
                add(new WinnerPanel(p, gamePanel.getWidth(), gamePanel.getHeight()));
            }
        }
    }

    /**
     * Gets the panel with the actual game board
     *
     * @return the board panel
     */
    public BoardPanel getBoardPanel() {
        return gamePanel;
    }

    /**
     * Gets the panel with the game controls (score, guess buttons)
     *
     * @return the control panel
     */
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    /**
     * Gets the game state
     *
     * @return the game object
     */
    public Game getGame() {
        return game;
    }
}