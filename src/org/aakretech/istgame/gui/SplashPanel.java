package org.aakretech.istgame.gui;

import org.aakretech.istgame.ai.genetic.Darwin;
import org.aakretech.istgame.ai.genetic.GeneticAI;
import org.aakretech.istgame.logic.Game;
import org.aakretech.istgame.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class SplashPanel extends JPanel implements Runnable {

    private static final GameFont GAMEFONT = new GameFont();
    public static final Random PRNG = new Random();
    public static final int MAX_GENERATIONS = 50;

    JButton pvpButton;
    JButton pvcpuButton;

    private int color = 15000;

    ArrayList<AnimLine> lines;
    ArrayList<EvolveCircle> circles;

    private GameWindow gameWindow;
    private boolean evolving; // true if currently evolving AI player
    private Darwin darwin;


    public SplashPanel(final GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        lines = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            AnimLine line = new AnimLine(1, i);
            color += 240;
            line.color = new Color(color);
            lines.add(line);
        }


        setLayout(null);
        setPreferredSize(new Dimension(600, 220));

        circles = new ArrayList<>(50);
        for(int i = 0; i < 50; i++) {
            EvolveCircle c = new EvolveCircle(PRNG.nextInt(600), PRNG.nextInt(220), PRNG.nextInt(20)+20, BoardPanel.BG_TEXT_COLOR);
            circles.add(c);
        }

        pvpButton = new JButton("PVP");
        pvpButton.setFont(GAMEFONT.getWinFont());
        pvpButton.setBounds(120, 50, 120, 80);

        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player p1 = new Player("P1");
                Player p2 = new Player("P2");

                java.util.List<Player> players = new ArrayList<Player>(2);
                players.add(p1);
                players.add(p2);

                Game g = new Game(5, 5, players, 250, 20, 80);
                gameWindow.showAndStartGame(g);
            }
        });

        pvcpuButton = new JButton("CPU");
        pvcpuButton.setFont(GAMEFONT.getWinFont());
        pvcpuButton.setBounds(360, 50, 120, 80);

        pvcpuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evolveAi();
            }
        });

        add(pvpButton);
        add(pvcpuButton);
    }

    private void evolveAi() {
        evolving = true;
        removeAll();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Player p1 = new Player("P1");

                darwin = new Darwin(100, 5, 500, 10, MAX_GENERATIONS, 0.015D, 0.5D, 2);
                Player p2 = darwin.evolvePlayer(25);

                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                java.util.List<Player> players = new ArrayList<Player>(2);
                players.add(p1);
                players.add(p2);

                Game g = new Game(5, 5, players, 250, 20, 80);
                ((GeneticAI) p2).setGame(g);

                evolving = false;
                gameWindow.showAndStartGame(g);
            }
        });

        t.start();
    }

    /**
     * The run method that causes repaint to be drawn at regular intervals
     */
    public void run() {
        while (true) {
            repaint();

            try {
                Thread.sleep(1000 / 41);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Custom painting that draws a background and the boxes
     *
     * @param g the graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(BoardPanel.COLOR_BACKGROUND);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (evolving) {
            evolveAnim(g2);
        } else {
            bgAnim(g2);
        }
    }

    /**
     * method call that draws and animates the background
     *
     * @param g2 the graphics context
     */
    private void bgAnim(Graphics2D g2) {
        int offset = 75;

        for (int i = 0; i < lines.size(); i++) {
            AnimLine line = lines.get(i);

            int speed = line.speed + (line.y / 10) + 1;
            line.y += speed;
            g2.setColor(line.color);
            g2.drawLine(0, line.y + offset, getWidth(), line.y + offset);

            if (line.y > getHeight()) {
                line.y = 1;
                line.speed = 1;
                color += 240;
                line.color = new Color(color);
            }
            line.y++;

        }
    }

    /**
     * method call that draws/animates the background while evolving CPU player
     *
     * @param g2 the graphics context
     */
    private void evolveAnim(Graphics2D g2) {
        for (int i = 0; i < circles.size(); i++) {
            EvolveCircle c = circles.get(i);
            g2.setColor(c.color);
            Point p = c.getLocation(darwin.getCurrentGeneration());
            g2.drawOval(p.x, p.y, c.size, c.size);
        }

        g2.setColor(Color.GREEN);
        g2.drawString("Evolving player....", 200, 50);
    }
}

/**
 * Class representing an animated line shown in the background
 */
class AnimLine {
    public int y;
    public int speed;
    public int animy = 0;
    public Color color;

    public AnimLine(int y, int speed) {
        this.y = y;
        this.speed = speed;
    }
}

class EvolveCircle {
    public int x;
    public int y;
    public int size;
    public Color color;

    public EvolveCircle(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public Point getLocation(int generation) {
        Point p = new Point();
        int range = SplashPanel.MAX_GENERATIONS - generation;
        if(range < 1) {
            range = 1;
        }
        p.x = x + (SplashPanel.PRNG.nextInt(range*2) - range);
        p.y = y + (SplashPanel.PRNG.nextInt(range*2) - range);
        return p;
    }
}