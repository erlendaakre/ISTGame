package org.aakretech.istgame.gui;

import org.aakretech.istgame.logic.Player;

import javax.swing.*;
import java.awt.*;

public class WinnerPanel extends JPanel {

    private int width;
    private int height;
    private Player winner;

    public WinnerPanel(Player winner, int width, int height) {
        this.winner = winner;
        this.width = width;
        this.height = height;

        setLayout(null);

        JLabel winPlayerNameLabel = new JLabel(winner.getName());
        winPlayerNameLabel.setFont(GameWindow.GAMEFONT.getWinFontPlayerName());
        winPlayerNameLabel.setBounds(width/2 - (int)winPlayerNameLabel.getPreferredSize().getWidth()/2,
                100,
                (int)winPlayerNameLabel.getPreferredSize().getWidth(),
                (int)winPlayerNameLabel.getPreferredSize().getHeight());


        JLabel winLabel = new JLabel("WINS!");
        winLabel.setFont(GameWindow.GAMEFONT.getWinFont());
        winLabel.setBounds(width/2 - (int)winLabel.getPreferredSize().getWidth()/2,
                100 + (int)winPlayerNameLabel.getPreferredSize().getHeight() + 20,
                (int)winLabel.getPreferredSize().getWidth(),
                (int)winLabel.getPreferredSize().getHeight());


        add(winPlayerNameLabel);
        add(winLabel);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        GradientPaint bg = new GradientPaint(0,0,Color.DARK_GRAY,0, width*2,Color.GREEN);
        g2.setPaint(bg);
        g2.fillRect(0, 0, width, height);
    }

}
