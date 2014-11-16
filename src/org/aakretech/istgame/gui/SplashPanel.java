package org.aakretech.istgame.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SplashPanel extends JPanel implements Runnable {

    private static final GameFont GAMEFONT = new GameFont();
    public static final Color COLOR_BACKGROUND = Color.BLACK;


    JButton pvpButton;
    JButton pvcpuButton;
    private Color bgTextColor = new Color(10,100,10);

    private int color = 15000;

    ArrayList<AnimLine> lines;



    public SplashPanel() {
        lines = new ArrayList<>(10);

        for(int i = 0; i < 10; i++) {
            AnimLine line = new AnimLine(1,i);
            color += 240;
            line.color = new Color(color);
            lines.add(line);
        }


        setLayout(null);
        setPreferredSize(new Dimension(600, 220));

        pvpButton = new JButton("PVP");
        pvpButton.setFont(GAMEFONT.getWinFont());
        pvpButton.setBounds(120,50, 120, 80);

        pvcpuButton = new JButton("CPU");
        pvcpuButton.setFont(GAMEFONT.getWinFont());
        pvcpuButton.setBounds(360,50, 120, 80);

        add(pvpButton);
        add(pvcpuButton);
    }


/**
 * The run method that causes repaint to be drawn at regular intervals
 */
    public void run() {
        while(true) {
            repaint();

            try {
                Thread.sleep(1000/41);
            } catch (InterruptedException e) { }
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

        g2.setColor(COLOR_BACKGROUND);
        g2.fillRect(0, 0, getWidth(), getHeight());

        bgAnim(g2);


     //   this.paintComponents(g);

    }

    /**
     * method call that draws and animates the background
     *
     * @param g2 the graphics context
     */
    private void bgAnim(Graphics2D g2) {
        int offset = 75;

      //  g2.setColor(bgTextColor.darker().darker());

        for (int i = 0; i < lines.size(); i++) {
            AnimLine line = lines.get(i);

            int speed = line.speed + (line.y / 10) + 1;
            line.y += speed;
            g2.setColor(line.color);
            g2.drawLine(0, line.y + offset,  getWidth(), line.y + offset);

            if(line.y > getHeight()) {
                line.y = 1;
                line.speed = 1;
                color += 240;
                System.out.println("COLOR " + color);
                line.color = new Color(color);
            }
            line.y++;

        }
    }

}
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