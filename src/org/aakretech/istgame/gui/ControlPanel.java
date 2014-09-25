package org.aakretech.istgame.gui;

import java.awt.*;
import javax.swing.*;
import org.aakretech.istgame.logic.Player;

/**
 * The control panel at the top of the GUI, shows player score, remaining
 * score for game, and has two coloured boxes used for making the guess.
 *
 * @author Erlend
 */
public final class ControlPanel extends JPanel {

    private GameWindow gameWindow;
    private int panelWidth;
    private int panelHeight;
    public static final Color COLOR_BACKGROUND = Color.DARK_GRAY;
    public static final Color COLOR_TEXT = Color.LIGHT_GRAY;
    public static final Color COLOR_TEXT_CURRENT_PLAYER = Color.RED;
    private JLabel p1Label;
    private JLabel p2Label;
    private JLabel p1LabelCurrentPlayer;
    private JLabel p2LabelCurrentPlayer;
    private JLabel scoreLabel;
    private JLabel guessYellowButton;
    private JLabel guessBlueButton;
    private GuessButtonListener buttonListener;

    /**
     * Creates the control panel
     *
     * @param gameWindow the main game window this panel is controlling
     */
    public ControlPanel(GameWindow gameWindow) {
        super();
        this.gameWindow = gameWindow;
        panelWidth = 250;
        panelHeight = 54;

        this.setBackground(COLOR_BACKGROUND);
        this.setLayout(null);

        p1Label = new JLabel(gameWindow.getGame().getPlayers().get(0).getName() + ": " + gameWindow.getGame().getPlayers().get(0).getScore());
        p1Label.setFont(GameWindow.GAMEFONT.getPlayerFont());
        p1Label.setForeground(COLOR_TEXT);
        p1Label.setBounds(35, 2, 120, 20);

        p1LabelCurrentPlayer = new JLabel(">>>");
        p1LabelCurrentPlayer.setFont(GameWindow.GAMEFONT.getPlayerFont());
        p1LabelCurrentPlayer.setForeground(COLOR_TEXT_CURRENT_PLAYER);
        p1LabelCurrentPlayer.setBounds(2, 2, 35, 20);


        p2Label = new JLabel(gameWindow.getGame().getPlayers().get(1).getName() + ": " + gameWindow.getGame().getPlayers().get(1).getScore());
        p2Label.setFont(GameWindow.GAMEFONT.getPlayerFont());
        p2Label.setForeground(COLOR_TEXT);
        p2Label.setBounds(35, 30, 120, 20);

        p2LabelCurrentPlayer = new JLabel(">>>");
        p2LabelCurrentPlayer.setFont(GameWindow.GAMEFONT.getPlayerFont());
        p2LabelCurrentPlayer.setForeground(COLOR_TEXT_CURRENT_PLAYER);
        p2LabelCurrentPlayer.setBounds(2, 30, 35, 20);

        guessYellowButton = new JLabel();
        guessYellowButton.putClientProperty("guess", 1);
        guessYellowButton.setOpaque(true);
        guessYellowButton.setBackground(BoardPanel.COLOR_1);
        guessYellowButton.setBounds(155, 2, 50, 50);

        guessBlueButton = new JLabel();
        guessBlueButton.putClientProperty("guess", 0);
        guessBlueButton.setOpaque(true);
        guessBlueButton.setBackground(BoardPanel.COLOR_0);
        guessBlueButton.setBounds(208, 2, 50, 50);

        buttonListener = new GuessButtonListener(gameWindow);
        guessYellowButton.addMouseListener(buttonListener);
        guessBlueButton.addMouseListener(buttonListener);

        scoreLabel = new JLabel("100");
        scoreLabel.setFont(GameWindow.GAMEFONT.getScoreFont());
        scoreLabel.setForeground(COLOR_TEXT);
        scoreLabel.setBounds(355, 2, 100, 50);

        add(p1Label);
        add(p2Label);
        add(p1LabelCurrentPlayer);
        add(p2LabelCurrentPlayer);
        add(guessYellowButton);
        add(guessBlueButton);
        add(scoreLabel);

        this.setCurrentPlayerIndicator(gameWindow.getGame().getCurrentPlayer());

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    /**
     * Called when the game is over to stop further input
     */
    public void gameComplete() {
        guessBlueButton.removeMouseListener(buttonListener);
        guessYellowButton.removeMouseListener(buttonListener);
    }

    /**
     * Updates ALL the controller text (player scores, and score left of this round of the game)
     *
     * @param newScore the new score remaining on this round
     */
    public void updateText(int newScore) {
        p1Label.setText(gameWindow.getGame().getPlayers().get(0).getName() + ": " + gameWindow.getGame().getPlayers().get(0).getScore());
        p2Label.setText(gameWindow.getGame().getPlayers().get(1).getName() + ": " + gameWindow.getGame().getPlayers().get(1).getScore());
        scoreLabel.setText("" + newScore);
    }

    /**
     * Sets the current player indicator (shows which player is currently taking it's turn)
     *
     * @param player the player whose turn it currently is
     */
    public void setCurrentPlayerIndicator(Player player) {
        if (player == gameWindow.getGame().getPlayers().get(0)) {
            p1LabelCurrentPlayer.setVisible(true);
            p2LabelCurrentPlayer.setVisible(false);
        } else if (player == gameWindow.getGame().getPlayers().get(1)) {
            p1LabelCurrentPlayer.setVisible(false);
            p2LabelCurrentPlayer.setVisible(true);
        }
    }

    /**
     * Calls when a guess was made, resets game score to 100
     */
    public void guessed() {
        updateText(100);
        setCurrentPlayerIndicator(gameWindow.getGame().getCurrentPlayer());
    }
}