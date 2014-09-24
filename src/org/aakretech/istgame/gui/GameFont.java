package org.aakretech.istgame.gui;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Handles all the different fonts used by the game
 *
 * @author Erlend Aakre
 */
public class GameFont {

    private Font baseFont;
    private Font playerFont;
    private Font scoreFont;
    private Font getWinFont;
    private Font getWinFontPlayerName;

    /**
     * Initialises the game font used
     */
    public GameFont() {
        try {
            baseFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("resources/PetMe1282Y.ttf"));
        } catch (FontFormatException | IOException e) {
            System.err.println("ERROR LOADING FONT: " + e);
            baseFont = new Font("Dialog", Font.PLAIN, 12);
        }
    }

    /**
     * Gets the font for the player name and player score
     * @return player name and score font
     */
    public Font getPlayerFont() {
        if (playerFont == null) {
            playerFont = baseFont.deriveFont(18F);
        }
        return playerFont;
    }

    /**
     * Gets the font displaying remaining points for the round 
     * @return big score font 
     */
    public Font getScoreFont() {
        if (scoreFont == null) {
            scoreFont = baseFont.deriveFont(30F);
        }
        return scoreFont;
    }

    /**
     * Gets the font for the winner player name
     * @return big player name font 
     */
    public Font getWinFontPlayerName() {
        if (getWinFontPlayerName == null) {
            getWinFontPlayerName = baseFont.deriveFont(80F);
        }
        return getWinFontPlayerName;
    }

    /**
     * Gets the font for displaying the word "Win" in the winner panel
     * @return the winner font
     */
    public Font getWinFont() {
        if (getWinFont == null) {
            getWinFont = baseFont.deriveFont(50F);
        }
        return getWinFont;
    }
}