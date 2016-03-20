package org.aakretech.istgame.ai.genetic;

import org.aakretech.istgame.ai.SimpleAI;

public class GeneticAI extends SimpleAI {

    protected double gene_percentage_uncovered;
    protected double gene_number_of_yellow;
    protected double gene_number_of_blue;
    protected double gene_number_total;
    private int fitness;

    public GeneticAI(double g1, double g2, double g3, double g4) {
        super("GEN");
        gene_percentage_uncovered = g1;
        gene_number_of_yellow = g2;
        gene_number_of_blue = g3;
        gene_number_total = g4;
    }

    public int guess() {
        int guess = -1;
        if (game.getPercentageUncovered() >= gene_percentage_uncovered) {
            guess = 1;
            if (game.getUncoveredTileCount(0) > gene_number_of_yellow) {
                guess = 0;
            } else if (game.getUncoveredTileCount(1) < gene_number_of_blue ) {
                guess = -1;
            }

            if (game.getUncoveredTileCount(0) + game.getUncoveredTileCount(1) < gene_number_total) {
                guess = -1;
            }
        }
        return guess;
    }

    public void randomizeGenes() {
        gene_percentage_uncovered = prng.nextDouble() * 1000;
        gene_number_of_yellow = prng.nextDouble() * 250;
        gene_number_of_blue = prng.nextDouble() * 250;
        gene_number_total = prng.nextDouble() * 250;

        fitness = 1;
    }

    public int getFitness() {
        if (fitness < 1) {
            return 1;
        }
        return fitness;
    }

    public int evaluateFitness(int numberOfGames, int scoreGoal) {
        FitnessCalculator.evaluateFitness(this, numberOfGames, scoreGoal);
        return getFitness();
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}