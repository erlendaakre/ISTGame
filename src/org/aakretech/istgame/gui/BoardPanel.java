package org.aakretech.istgame.gui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import org.aakretech.istgame.logic.Game;

/**
 * Panel that shows the actual game boards with the coloured boxes
 *
 * @author Erlend Aakre
 */
public class BoardPanel extends JPanel implements Runnable {

    public static final int CELL_SIZE = 50;
    private static final int CELL_SPACE = 10;
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final Color COLOR_NULL = Color.GRAY;
    public static final Color COLOR_0 = Color.CYAN;
    public static final Color COLOR_1 = Color.YELLOW;

    private int boardWidth;
    private int boardHeight;
    private Map<Integer, Point> boardIndexToScreenCoordinateMap;
    private Game game;

    private int animX;
    private int animY;
    private String bgText;
    public static final Color BG_TEXT_COLOR = new Color(10,100,10);

    /**
     * Creates a new game panel
     *
     * @param game the game state
     */
    public BoardPanel(Game game) {
        super();
        this.game = game;
        boardWidth = calculateWidth();
        boardHeight = calculateHeight();
        this.boardIndexToScreenCoordinateMap = new HashMap<Integer, Point>(game.getBoard().size());
    }

    /**
     * Called when a player guesses
     */
    public void playerGuessed() {
        repaint();
    }

    /**
     * The run method that causes repaint to be drawn at regular intervals
     */
    public void run() {
        while(true) {
            repaint();

            try {
                Thread.sleep(1000/25);
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
        g2.fillRect(0, 0, boardWidth, boardHeight);

        animateBackground(g2);

        int x = CELL_SIZE + CELL_SPACE;
        int y = CELL_SIZE + CELL_SPACE;

        for (int i = 0; i < game.getBoardSize(); i++) {
            if (i != 0 && i % game.getBoardWidth() == 0) {
                x = CELL_SIZE + CELL_SPACE;
                y += CELL_SIZE + CELL_SPACE;
            }
            Integer cellVal = game.getCell(i);
            Color c = COLOR_BACKGROUND;
            if (cellVal == -1) {
                c = COLOR_NULL;
            } else if (cellVal == 0) {
                c = COLOR_0;
            } else if (cellVal == 1) {
                c = COLOR_1;
            }
            g2.setColor(c);
            g2.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            boardIndexToScreenCoordinateMap.put(i, new Point(x, y));

            x += CELL_SIZE + CELL_SPACE;
        }
    }

    /**
     * method call that draws and animates the background
     *
     * @param g2 the graphics context
     */
    private void animateBackground(Graphics2D g2) {

        if(bgText == null) {
            bgText = generateBinaryBackgroundString();
        }

        g2.setColor(BG_TEXT_COLOR.darker().darker());
        int y = -100 + 1;
        for (String line : bgText.split("\n")) {
            g2.drawString(line, animX + 1, y += animY + g2.getFontMetrics().getHeight());

        }

        y = -100;
        g2.setColor(BG_TEXT_COLOR);
        for (String line : bgText.split("\n")) {
            g2.drawString(line, animX , y += animY + g2.getFontMetrics().getHeight());

        }


        //animX++;
        animY++;
        if(animY > 25) {
            animY = 0;
            bgText = generateBinaryBackgroundString();
        }
    }

    /**
     * Generates a big string with random 0's and 1's
     *
     * @return a heavily spaced binary string
     */
    private String generateBinaryBackgroundString() {
        StringBuffer sb = new StringBuffer();
        for(int lines = 0; lines < 100; lines++) {
            StringBuffer line = new StringBuffer("                                                                   " +
                    "                                                    ");
            for(int i = 0; i < 10; i++) {
                line.setCharAt((int)(Math.random()*line.length()), Math.random() < 0.5D ? '0' : '1');
            }
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * Gets the board index to screen coordinate map
     *
     * @return a map for finding the positions of individual boxes
     */
    public Map<Integer, Point> getBoardIndexToScreenCoordinateMap() {
        return boardIndexToScreenCoordinateMap;
    }

    /**
     * Gets the size of the component
     *
     * @return the preferred/minimum size of the component
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(boardWidth, boardHeight);
    }

    /**
     * Calculates the width of the board
     *
     * @return the width of the board
     */
    private int calculateWidth() {
        return ((game.getBoardWidth() + 2) * (CELL_SIZE + CELL_SPACE)) - CELL_SPACE;
    }

    /**
     * Calculates the height of the board
     *
     * @return the height of the board
     */
    private int calculateHeight() {
        return ((game.getBoardHeight() + 2) * (CELL_SIZE + CELL_SPACE)) - CELL_SPACE;
    }
}