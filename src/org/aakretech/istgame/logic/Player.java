package org.aakretech.istgame.logic;

public class Player {
    private String name;
    private int score;
    protected boolean human;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.human = true;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void substractScore(int score) {
        this.score -= score;
    }

    public void resetScore() {
        score = 0;
    }

    public boolean isHuman() {
        return human;
    }
}