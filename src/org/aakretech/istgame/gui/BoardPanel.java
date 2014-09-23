package org.aakretech.istgame.gui;

import org.aakretech.istgame.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BoardPanel extends JPanel {

    public static final int CELLSIZE = 50;
    private static final int CELLSPACE = 10;

    private int boardWidth;
    private int boardHeight;

    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final Color COLOR_NULL = Color.GRAY;
    public static final Color COLOR_0 = Color.CYAN;
    public static final Color COLOR_1 = Color.YELLOW;

    private Map<Integer, Point> boardIndexToScreenCoordinateMap;

    private Game game;

    public BoardPanel(Game game) {
        super();
        this.game = game;
        boardWidth = calculateWidth();
        boardHeight = calculateHeight();
        this.boardIndexToScreenCoordinateMap = new HashMap<Integer, Point>(game.getBoard().size());
    }

    public void guessed() {
        repaint();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(COLOR_BACKGROUND);
        g2.fillRect(0, 0, boardWidth, boardHeight);

        int x = CELLSIZE + CELLSPACE;
        int y = CELLSIZE + CELLSPACE;

        for(int i = 0; i < game.getBoardSize(); i++) {
            if(i != 0 && i % game.getBoardWidth() == 0) {
                x = CELLSIZE + CELLSPACE;
                y += CELLSIZE + CELLSPACE;
            }
            Integer cellVal = game.getCell(i);
            Color c = COLOR_BACKGROUND;
            if(cellVal == -1) {
                c = COLOR_NULL;
            }
            else if(cellVal == 0) {
                c = COLOR_0;
            }
            else if(cellVal == 1) {
                c = COLOR_1;
            }
            g2.setColor(c);
            g2.fillRect(x, y, CELLSIZE, CELLSIZE);
            boardIndexToScreenCoordinateMap.put(i, new Point(x, y));

            x += CELLSIZE + CELLSPACE;
        }
    }

    public Map<Integer, Point> getBoardIndexToScreenCoordinateMap() {
        return boardIndexToScreenCoordinateMap;
    }


    public Dimension getPreferredSize() {
        return new Dimension(boardWidth, boardHeight);
    }

    private int calculateWidth() {
        return ((game.getBoardWidth()+2)*(CELLSIZE+ CELLSPACE))- CELLSPACE;
    }
    private int calculateHeight() {
        return ((game.getBoardHeight()+2)*(CELLSIZE+ CELLSPACE))- CELLSPACE;
    }
}