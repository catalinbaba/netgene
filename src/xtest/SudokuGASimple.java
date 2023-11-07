/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import java.util.ArrayList;
import org.netgene.ga.Individual;
import org.netgene.ga.Population;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.chromosome.*;
import org.netgene.ga.core.GenerationTracker;
import org.netgene.ga.core.GeneticAlgorithm;
import org.netgene.ga.core.GeneticConfiguration;
import org.netgene.ga.crossover.OnePointCrossover;
import org.netgene.ga.crossover.Order1Crossover;
import org.netgene.ga.crossover.TwoPointCrossover;
import org.netgene.ga.crossover.UniformCrossover;
import org.netgene.ga.fitness.FitnessFunction;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.ga.mutator.ScrambleMutator;
import org.netgene.ga.mutator.SwapMutator;
import org.netgene.ga.selection.parent.TournamentSelector;

/**
 *
 * @author cbaba
 */
public class SudokuGASimple {
       
//    private static int[] sudoku = {
//    5, 3, 0, 0, 7, 0, 0, 0, 0,
//    6, 0, 0, 1, 9, 5, 0, 0, 0,
//    0, 9, 8, 0, 0, 0, 0, 6, 0,
//    8, 0, 0, 0, 6, 0, 0, 0, 3,
//    4, 0, 0, 8, 0, 3, 0, 0, 1,
//    7, 0, 0, 0, 2, 0, 0, 0, 6,
//    0, 6, 0, 0, 0, 0, 2, 8, 0,
//    0, 0, 0, 4, 1, 9, 0, 0, 5,
//    0, 0, 0, 0, 8, 0, 0, 7, 9
//};
    
     private static int[] sudoku = {
    0, 8, 0, 0, 0, 0, 0, 9, 0,
    0, 0, 7, 5, 0, 2, 8, 0, 0,
    6, 0, 0, 8, 0, 7, 0, 0, 5,
    3, 7, 0, 0, 8, 0, 0, 5, 8,
    2, 0, 0, 0, 0, 0, 0, 0, 1,
    9, 5, 0, 0, 4, 0, 0, 3, 2,
    8, 0, 0, 1, 0, 4, 0, 0, 9,
    0, 0, 1, 9, 0, 3, 6, 0, 0,
    0, 4, 0, 0, 0, 0, 0, 2, 0
};
     
     
//      private static int[] sudoku = {
//    8, 0, 2, 0, 0, 3, 5, 1, 0,
//    0, 6, 0, 0, 9, 1, 0, 0, 3,
//    7, 0, 1, 0, 0, 0, 8, 9, 4,
//    6, 0, 8, 0, 0, 4, 0, 2, 1,
//    0, 0, 0, 2, 5, 8, 0, 6, 0,
//    9, 2, 0, 3, 1, 0, 4, 0, 0,
//    0, 0, 0, 4, 0, 2, 7, 8, 0,
//    0, 0, 5, 0, 8, 9, 0, 0, 0,
//    2, 0, 0, 0, 0, 7, 1, 0, 0
//};
     
//     private static int[] sudoku = {
//        0,3,0,0,7,0,0,5,0,
//        5,0,0,1,0,6,0,0,9,
//        0,0,1,0,0,0,4,0,0,
//        0,9,0,0,5,0,0,6,0,
//        6,0,0,4,0,2,0,0,7,
//        0,4,0,0,1,0,0,3,0,
//        0,0,2,0,0,0,8,0,0,
//        9,0,0,3,0,5,0,0,2,
//        0,1,0,0,2,0,0,7,0};
     
    private static Population population = new Population();
    
    public static void main(String[] args) throws Exception
    {
        
        TournamentSelector selector = new TournamentSelector(3);
        //RouletteSelector selector = new RouletteSelector();
        //RankSelector selector = new RankSelector();
        //OnePointCrossover oc = new OnePointCrossover();
        //TwoPointCrossover crossover = new TwoPointCrossover();
        //ScrambleMutator mutator = new ScrambleMutator();
        //InversionMutator inm = new InversionMutator();
        //IntegerMutator im = new IntegerMutator();
        UniformCrossover crossover = new UniformCrossover();
        CustomMutator mutator = new CustomMutator();
        mutator.setDoNotMutateList(getDoNotMutateList());
        //Order1Crossover crossover = new Order1Crossover();
        //SwapMutator mutator = new SwapMutator();
        
        
        GeneticAlgorithm ga = new GeneticConfiguration()
                                                  .setParentSelector(selector)
                                                  //.setElitismSize(2)
                                                  .setMutatorOperator(mutator)
                                                  .setCrossoverOperator(crossover)
                                                  //.setMutationRate(0.25)
                                                  //.setCrossoverRate(0.25)
                                                  //.skipMutation()
                                                  .setElitismSize(10)
                                                  .setMaxGeneration(10000)
                                                  .setThreadPoolSize(6)
                                                  .setTargetFitness(1)
                                                  .getAlgorithm();
        
        
        
         
        int populationSize = 5000;
        int chromosomeSize = 81;
        
        
        for(int i=0; i<populationSize; i++)
        {
            IntegerChromosome chromosome = new IntegerChromosome(chromosomeSize, 1, 10);
            //PermutationChromosome chromosome = new PermutationChromosome(9, 1);
            //chromosome.
            //IntegerChromosome chromosome = new IntegerChromosome(partialSolution.get1DArray());
            //System.out.println("sizeeee: " + chromosome.length());
            Individual individual = new Individual(chromosome);
            population.addIndividual(individual);
        }
        
      
        FitnessFunction exFitness = (individual) ->
        {
            //BitChromosome chromosome = individual.getChromosome();
            IntegerChromosome chromosome = (IntegerChromosome)individual.getChromosome();
            double initialFitness = individual.getFitnessScore();
            int array[] = chromosome.toArray();
            Sudoku sd = new Sudoku(array);
            //double fitness = sd.calculateFitnessScore();
            
            double fitness = sd.countDuplicates();
            //System.out.println("shared fitness: " + FitnessSharing.calculateSharedFitness(fitness, population));
            //double fitnessShared = FitnessSharing.calculateSharedFitness(fitness, population);    
            if(fitness==0) //avoid /0
                fitness = 1;
            fitness = 1/fitness;
            
            individual.setFitnessScore(fitness);
        };
        
               
        GenerationTracker myTracker = (g, r) ->
        {
            System.out.println("Step: " + r.getGenerationNumber());
            System.out.println("best fitness: " + r.getBestFitness());
            System.out.println("medium fitness: " + g.getPopulation().getIndividual(populationSize/2).getFitnessScore());
            System.out.println("lowest fitness: " + g.getPopulation().getIndividual(populationSize-1).getFitnessScore());
            //System.out.println("best individual: " + r.getBestIndividual());
            IntegerChromosome chromosome = (IntegerChromosome)ga.getPopulation().getBestIndividual().getChromosome();
            int array[] = chromosome.toArray();
            Sudoku solution = new Sudoku(array);
            double duplicates = solution.countDuplicates();
            if(duplicates <=15)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.1);
                //ga.setMutatorOperator(new SwapMutator());
                //ga.setCrossoverRate(1);
            }
            if(duplicates <10)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.2);
                //ga.setCrossoverRate(1);
            }
            if(duplicates <=6)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.3);
                //ga.setCrossoverRate(1);
            }
            if(duplicates <=4)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.4);
                //ga.setCrossoverRate(1);
            }
            if(duplicates <=2)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.5);
                //ga.setCrossoverRate(1);
            }
            if(duplicates >15)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.05);
                //ga.setCrossoverRate(1);
            }
//            if(duplicates <15)
//            {
//                IntegerChromosome chromosomer = new IntegerChromosome(81, 1, 10);
//                Individual individual = new Individual(chromosomer);
//                for(int j=0; j<getDoNotMutateList().size(); j++)
//                {
//                    chromosome.setGene(getDoNotMutateList().get(j), new IntegerGene(sudoku[getDoNotMutateList().get(j)]));
//                }
//                for(int i=2000; i<populationSize; i++)
//                {
//                    ga.getPopulation().replaceIndividual(i,individual);
//                }
//                System.out.println("population size: " + ga.getPopulation().size());
//                //ga.setCrossoverRate(1);
//            }
//            if(ga.getPopulation().getBestIndividual().getFitnessScore() >= 0.1)
//            {
//                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!new mutation rate");
//                ga.setMutationRate(0.8);
//                ga.setCrossoverRate(0.8);
//            }
            System.out.println("Duplicates: " + solution.countDuplicates());
            System.out.println("Evaluation execution: " + r.getEvolutionDuration().toMillis() + "ms");
            //System.out.println("----------------------------------------------------");
            //System.out.println("Step: " + g.getGeneration());
            //System.out.println("Evolution execution: " + r.getEvolutionDuration().toMillis() + "ms");
            //System.out.println("Evaluation execution: " + r.getEvaluationDuration().toMillis() + "ms");
            //System.out.println("Generation execution: " + r.getGenerationDuration().toMillis()+ "ms");
            //System.out.println("Best fitness: " +g.getPopulation().getBestIndividual().getFitnessScore());
            //System.out.println("lowest fitness: " + g.getPopulation().getIndividual(0).getFitnessScore());
            //System.out.println("custom data: " + g.getPopulation().getBestIndividual().getCustomData());
            Population population1 = g.getPopulation();
            System.out.println("----------------------------------------------------");
        };
        
        ga.setGenerationTracker(myTracker);
        //ga.setCustomStopCondition(customStopCondition);
        ga.evolve(population, exFitness);
        
        Individual bestIndividual = ga.getPopulation().getBestIndividual();
        
        double fitnessValue = ga.getPopulation().getBestIndividual().getFitnessScore();
        
        System.out.println("individual: " + ga.getPopulation().getBestIndividual());
        System.out.println("fitness value: " + fitnessValue);
            
        Individual bestIndividual1 = ga.getPopulation().getBestIndividual();
        IntegerChromosome chromosome = (IntegerChromosome)bestIndividual1.getChromosome();
        int array[] = chromosome.toArray();
        
        Sudoku solution = new Sudoku(array);
        
        Sudoku.print2DArray(solution.getTable());
        
        System.out.println("");
 
        //System.out.println("generation: " + ga.getGeneration());
    }
    
    private static ArrayList<Integer> getDoNotMutateList()
    {
        ArrayList<Integer> doNotMutateList = new ArrayList();
        for(int i=0; i<81; i++)
        {
            if(sudoku[i] != 0)
              doNotMutateList.add(i);
        }
        return doNotMutateList;
    }
    
//    private Individual generateIndividual()
//    {
//        
//    }
//    
    private Individual generateRandomIndividual()
    {
        IntegerChromosome chromosome = new IntegerChromosome(81, 1, 10);
        Individual individual = new Individual(chromosome);
        for(int j=0; j<getDoNotMutateList().size(); j++)
        {
            chromosome.setGene(getDoNotMutateList().get(j), new IntegerGene(sudoku[getDoNotMutateList().get(j)]));
        }
        return individual;
    }
}
