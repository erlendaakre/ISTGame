package org.aakretech.istgame.ai.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Darwin {

    private List<GeneticAI> population;
    private double totalFitness;
    private Random prng;

    private int popSize;
    private int elitism;
    private int goalScore;
    private int numberOfGamesToPlay;
    private int maxGenerations;
    private int currentGeneration;
    private double mutationRate;
    private double uniformityRate;
    private int crossOverOffspring;


    public Darwin(int popSize, int elitism, int goalScore, int numberOfGamesToPlay, int maxGenerations, double mutationRate,
                  double uniformityRate, int crossOverOffspring) {
        prng = new Random();
        population = new ArrayList<GeneticAI>(popSize);

        this.popSize = popSize;
        this.elitism = elitism;
        this.goalScore = goalScore;
        this.numberOfGamesToPlay = numberOfGamesToPlay;
        this.maxGenerations = maxGenerations;
        this.mutationRate = mutationRate;
        this.uniformityRate = uniformityRate;
        this.crossOverOffspring = crossOverOffspring;
    }

    public static void main(String[] main) {
        Darwin darwin = new Darwin(100, 6, 500, 5, 50, 0.01D, 0.5D, 2);

        darwin.initPopulation();
        darwin.evaluate();
        darwin.printFitnessEvaltuation();

        for (int generation = 0; generation < darwin.maxGenerations; generation++) {
            System.out.println("Evolving generation: " + (generation + 1));
            darwin.currentGeneration = generation + 1;
            darwin.evolvePopulation();
            darwin.evaluate();

        }

        darwin.evaluate();
        darwin.printFitnessEvaltuation();

        darwin.printFittestIndividual();
    }

    public void evolvePopulation() {
        List<GeneticAI> newPopulaion = new ArrayList<GeneticAI>(popSize);

        //Select Elitism
        List<GeneticAI> elite = new ArrayList<GeneticAI>(elitism);
        for (int i = 0; i < elitism; i++) {
            GeneticAI eliteAI = getFittestIndividual();
            elite.add(eliteAI);
            population.remove(eliteAI);
        }

        // Allow elite to crossOver and mutate
        population.addAll(elite);

        int count = elitism;
        while (count <= popSize) {
            GeneticAI individual1 = rouletteWheelSelection();
            GeneticAI individual2 = rouletteWheelSelection();

            List<GeneticAI> offSpring = crossOver(individual1, individual2);
            for (GeneticAI child : offSpring) {
                newPopulaion.add(child);
                count++;
            }
        }

        for (int i = 0; i < newPopulaion.size(); i++) {
            mutate(newPopulaion.get(i));
        }

        population = newPopulaion;
        population.addAll(elite);
    }

    public List<GeneticAI> crossOver(GeneticAI parent1, GeneticAI parent2) {
        List<GeneticAI> offspring = new ArrayList<GeneticAI>(crossOverOffspring);
        for (int i = 0; i < crossOverOffspring; i++) {
            GeneticAI child = new GeneticAI(0, 0, 0, 0, 0);
            offspring.add(child);

            if (prng.nextDouble() < uniformityRate) {
                child.gene_percentage_uncovered = parent1.gene_percentage_uncovered;
            } else {
                child.gene_percentage_uncovered = parent2.gene_percentage_uncovered;
            }

            if (prng.nextDouble() < uniformityRate) {
                child.gene_number_of_blue = parent1.gene_number_of_blue;
            } else {
                child.gene_number_of_blue = parent2.gene_number_of_blue;
            }

            if (prng.nextDouble() < uniformityRate) {
                child.gene_number_of_yellow = parent1.gene_number_of_yellow;
            } else {
                child.gene_number_of_yellow = parent2.gene_number_of_yellow;
            }

            if (prng.nextDouble() < uniformityRate) {
                child.gene_number_total = parent1.gene_number_total;
            } else {
                child.gene_number_total = parent2.gene_number_total;
            }

            if (prng.nextDouble() < uniformityRate) {
                child.gene_random = parent1.gene_random;
            } else {
                child.gene_random = parent2.gene_random;
            }
        }

        return offspring;
    }

    public void mutate(GeneticAI individual) {
        if (prng.nextDouble() <= mutationRate) {
            double mutationFactor = prng.nextDouble() + 0.5D;
            individual.gene_percentage_uncovered *= mutationFactor;
        }
        if (prng.nextDouble() <= mutationRate) {
            double mutationFactor = prng.nextDouble() + 0.5D;
            individual.gene_number_of_yellow *= mutationFactor;
        }
        if (prng.nextDouble() <= mutationRate) {
            double mutationFactor = prng.nextDouble() + 0.5D;
            individual.gene_number_of_blue *= mutationFactor;
        }
        if (prng.nextDouble() <= mutationRate) {
            double mutationFactor = prng.nextDouble() + 0.5D;
            individual.gene_number_total *= mutationFactor;
        }
        if (prng.nextDouble() <= mutationRate) {
            double mutationFactor = prng.nextDouble() + 0.5D;
            individual.gene_random *= mutationFactor;
        }
    }

    public GeneticAI rouletteWheelSelection() {
        //System.out.println("Selecting individual!  total fitness: " + totalFitness + " size: " + population.size());
        double randNum = prng.nextDouble() * totalFitness;
        int i;
        for (i = 0; i < popSize && randNum > 0; ++i) {
            randNum -= population.get(i).getFitness();
        }
        return population.get(i - 1);
    }

    public GeneticAI getFittestIndividual() {
        GeneticAI fittest = population.get(0);
        for (GeneticAI ai : population) {
            if (ai.getFitness() > fittest.getFitness()) {
                fittest = ai;
            }
        }
        return fittest;
    }

    private void evaluate() {
        totalFitness = 0.0D;
        for (GeneticAI individual : population) {
            totalFitness += individual.evaluateFitness(numberOfGamesToPlay, goalScore);
        }
    }

    private void printFitnessEvaltuation() {
        System.out.println("Evaluation of population fitness : " + population.size());
        System.out.println("Generations                      : " + currentGeneration);
        System.out.println("total fitness                    : " + totalFitness);
        System.out.println("avg fitness                      : " + totalFitness / population.size());
        System.out.println("Fittest individual               : " + getFittestIndividual().getFitness());
    }

    private void printFittestIndividual() {
        GeneticAI ai = getFittestIndividual();
        System.out.println("Fittest Individual");
        System.out.println("==================");
        System.out.println("Fitness score : " + ai.getFitness());
        System.out.println("PU GENE       : " + ai.gene_percentage_uncovered);
        System.out.println("NT GENE       : " + ai.gene_number_total);
        System.out.println("NB GENE       : " + ai.gene_number_of_blue);
        System.out.println("NY GENE       : " + ai.gene_number_of_yellow);
        System.out.println("R  GENE       : " + ai.gene_random);
    }

    private void initPopulation() {
        System.out.println("Initializing Population : " + popSize);
        for (int i = 0; i < popSize; i++) {
            GeneticAI individual = new GeneticAI(0, 0, 0, 0, 0);
            individual.randomizeGenes();
            population.add(individual);
        }
        System.out.println("Population initialized  : " + population.size());
    }

}
