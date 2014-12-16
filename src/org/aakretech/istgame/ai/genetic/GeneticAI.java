package org.aakretech.istgame.ai.genetic;

import org.aakretech.istgame.ai.SimpleAI;

public class GeneticAI extends SimpleAI {

    protected double gene_percentage_uncovered;
    protected double gene_number_of_yellow;
    protected double gene_number_of_blue;
    protected double gene_number_total;
    protected double gene_random;
    private int fitness;

    public GeneticAI(double g1, double g2, double g3, double g4, double g5) {
        super("GEN");
        gene_percentage_uncovered = g1;
        gene_number_of_yellow = g2;
        gene_number_of_blue = g3;
        gene_number_total = g4;
        gene_random = g5;
    }

    public int guess() {
        int guess = -1;
        double random = prng.nextDouble() * gene_random;
        if (game.getPercentageUncovered() >= gene_percentage_uncovered * gene_random) {
            guess = 1;
            if (game.countUncovered(0) > gene_number_of_yellow * random) {
                guess = 0;
            } else if (game.countUncovered(1) < gene_number_of_blue * gene_random) {
                guess = -1;
            }

            if (game.countUncovered(0) + game.countUncovered(1) < gene_number_total * gene_random) {
                guess = -1;
            }
        }
        return guess;
    }

    public void randomizeGenes() {
        gene_percentage_uncovered = prng.nextDouble() * 100;
        gene_number_of_yellow = prng.nextDouble() * 25;
        gene_number_of_blue = prng.nextDouble() * 25;
        gene_number_total = prng.nextDouble() * 25;
        gene_random = prng.nextDouble() * 10;

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