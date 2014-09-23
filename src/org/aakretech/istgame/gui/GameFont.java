package org.aakretech.istgame.gui;

import java.awt.*;
import java.io.FileInputStream;

public class GameFont {
    private Font baseFont;
    private Font playerFont;
    private Font scoreFont;
    private Font getWinFont;
    private Font getWinFontPlayerName;

    public GameFont() {
        try {
            baseFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("resources/PetMe1282Y.ttf"));
        }
        catch(Exception e) {
            System.err.println("ERROR LOADING FONT: " + e);
            baseFont = new Font("Dialog", Font.PLAIN, 12);
        }
    }

    public Font getPlayerFont() {
        if(playerFont == null) {
            playerFont = baseFont.deriveFont(18F);
        }
        return playerFont;
    }

    public Font getScoreFont() {
        if(scoreFont == null) {
            scoreFont = baseFont.deriveFont(30F);
        }
        return scoreFont;
    }

    public Font getWinFontPlayerName() {
        if(getWinFontPlayerName == null) {
            getWinFontPlayerName = baseFont.deriveFont(80F);
        }
        return getWinFontPlayerName;
    }

    public Font getWinFont() {
        if(getWinFont == null) {
            getWinFont = baseFont.deriveFont(50F);
        }
        return getWinFont;
    }
}
