package org.aakretech.istgame.ai.genetic;

import java.util.ArrayList;
import java.util.List;

import org.aakretech.istgame.ai.FlipOnlyAI;
import org.aakretech.istgame.logic.Game;
import org.aakretech.istgame.logic.Player;

public class FitnessCalculator {

    private static final int MAXMOVES = 5000;

    // TODO: this fitness function is pretty shit

    public static void evaluateFitness(GeneticAI individual, int numberOfGames, int gameScore) {
//        System.out.println("\t\tEvaluating fitness for individual with fitness: " + individual.getFitness());
        int fitness = 0;
        for (int i = 0; i < numberOfGames; i++) {
//              System.out.println("\t\t\tPlaying game " + i + " individual fitness: " + individual.getFitness());
            List<Player> players = new ArrayList<Player>(2);
            players.add(new FlipOnlyAI());
            players.add(individual);
            Game game = new Game(5, 5, players, gameScore, 20, 80);
            individual.setGame(game);
            int move = 1;
            while (game.getPercentageUncovered() < 100 && individual.getScore() < gameScore && move < MAXMOVES) {
//                System.out.print("Move " + move + "   % " + game.getPercentageUncovered() + "     :");
                game.aiMove();
                move++;
            }
            game.startNewRound(true);
//            System.out.println("\t\t\tAdding score " + individual.getScore() + " to fitness: " + fitness);
            fitness += individual.getScore();
            individual.resetScore();
        }

//        System.out.println("\t\tIndividual fitness = " + fitness);
        individual.setFitness(fitness);
    }
}
