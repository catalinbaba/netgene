/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Population;
import org.netgene.ga.chromosome.*;
import org.netgene.ga.GenerationTracker;
import org.netgene.ga.GeneticAlgorithm;
import org.netgene.ga.GeneticConfiguration;
import org.netgene.ga.crossover.*;
import org.netgene.ga.fitness.*;
import org.netgene.ga.gene.*;

import org.netgene.ga.selection.parent.*;
import static xtest.SudokuGAPerm.fillRandomUniqueDigits;

/**
 *
 * @author cbaba
 */
public class SudokuGA 
{
 
//    
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
    
//     private static int[] sudoku = {
//    0, 8, 0, 0, 0, 0, 0, 9, 0,
//    0, 0, 7, 5, 0, 2, 8, 0, 0,
//    6, 0, 0, 8, 0, 7, 0, 0, 5,
//    3, 7, 0, 0, 8, 0, 0, 5, 8,
//    2, 0, 0, 0, 0, 0, 0, 0, 1,
//    9, 5, 0, 0, 4, 0, 0, 3, 2,
//    8, 0, 0, 1, 0, 4, 0, 0, 9,
//    0, 0, 1, 9, 0, 3, 6, 0, 0,
//    0, 4, 0, 0, 0, 0, 0, 2, 0
//};
     
     
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
     
//     static int[] sudoku = {
//            5, 0, 0, 0, 0, 0, 0, 4, 0,
//            0, 0, 0, 1, 0, 0, 0, 2, 0,
//            0, 0, 9, 0, 0, 0, 0, 0, 0,
//            0, 7, 0, 0, 0, 3, 0, 0, 0,
//            0, 0, 0, 8, 0, 4, 0, 0, 0,
//            0, 0, 0, 7, 0, 0, 0, 9, 0,
//            0, 0, 0, 0, 0, 0, 8, 0, 0,
//            0, 9, 0, 0, 0, 1, 0, 0, 0,
//            0, 3, 0, 0, 0, 0, 0, 0, 6
//        };
     
     
          private static int[] sudoku = {
        0,3,0,0,7,0,0,5,0,
        5,0,0,1,0,6,0,0,9,
        0,0,1,0,0,0,4,0,0,
        0,9,0,0,5,0,0,6,0,
        6,0,0,4,0,2,0,0,7,
        0,4,0,0,1,0,0,3,0,
        0,0,2,0,0,0,8,0,0,
        9,0,0,3,0,5,0,0,2,
        0,1,0,0,2,0,0,7,0};
          
          private static int staleCount = 0;
          private static double prevFitness = 0;
          private static int populationSize = 500;
          private static int chromosomeSize = 81;
     
     
    private static Population population = new Population();
    private static String fileLoad = "sudoku4.txt";
    private static String fileSave = "sudoku5.txt";
    
    public static void main(String[] args) throws Exception
    {
        
        //TournamentSelector selector = new TournamentSelector(10);
        //CompetitionSelector selector = new CompetitionSelector();
        //RouletteSelector selector = new RouletteSelector();
        RankSelector selector = new RankSelector();
        //CustomPyCrossover crossover = new CustomPyCrossover();
        TwoPointCrossover crossover = new TwoPointCrossover();
        //SwapMutator sm = new SwapMutator();
        //InversionMutator inm = new InversionMutator();
        //IntegerMutator im = new IntegerMutator();
        //UniformCrossover crossover = new UniformCrossover();
        CustomMutator mutator = new CustomMutator();
        mutator.setDoNotMutateList(getDoNotMutateList());
        
        
        GeneticAlgorithm ga = new GeneticConfiguration()
                                                  .setParentSelector(selector)
                                                  //.setElitismSize(2)
                                                  .setMutatorOperator(mutator)
                                                  .setCrossoverOperator(crossover)
                                                  //.setMutationRate(0.25)
                                                  //.setCrossoverRate(0.25)
                                                  //.skipMutation()
                                                  .setElitismSize(120)
                                                  //.setElitism(false)
                                                  .setMaxGeneration(1000)
                                                  .setThreadPoolSize(6)
                                                  .setTargetFitness(1)
                                                  .getAlgorithm();
        
        
        
         
        
   
        for(int i=0; i<populationSize; i++)
        {
            
            IntegerChromosome integerChromosome = new IntegerChromosome();
            int row = 0;
            for(int j=0; j<9; j++)
            {
                row = j;
                int array[] = new int[9];
                for(int k=0; k<9; k++)
                {
                    array[k] = sudoku[row*9 + k];
                }
                fillRandomUniqueDigits(array);
                for(int k=0; k<9; k++)
                {
                    integerChromosome.addGene(new IntegerGene(array[k]));
                }
            }
            //System.out.println("sizeeee: " + integerChromosome.length());
            Individual individual = new Individual(integerChromosome);
            population.addIndividual(individual);
        }
        
         //population.addIndividual(new Individual(new IntegerChromosome(partialSolution.get1DArray())));
        
        //for(int i=9990; i<populationSize; i++)
        //{
        //    population.addIndividual(new Individual(new IntegerChromosome(partialSolution.get1DArray())));
        //}

        //System.out.println("population size: " + population.size());
        
        //add fixed values
        for(int i=0; i<populationSize; i++)
        {
            Individual individual = population.getIndividual(i);
            IntegerChromosome chromosome = (IntegerChromosome)individual.getChromosome();
            for(int j=0; j<getDoNotMutateList().size(); j++)
            {
                chromosome.setGene(getDoNotMutateList().get(j), new IntegerGene(sudoku[getDoNotMutateList().get(j)]));
            }
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
            if(fitness==0) //avoid /0
                fitness = 1;
            fitness = 1/fitness;
            
            individual.setFitnessScore(fitness);
        };
        
               
         GenerationTracker myTracker;
         myTracker = (g, r) ->
         {
             System.out.println("Step: " + r.getGenerationNumber());
             System.out.println("best fitness: " + r.getBestFitness());
             System.out.println("lowest fitness: " + g.getPopulation().getIndividual(populationSize-1).getFitnessScore());
             //System.out.println("best individual: " + r.getBestIndividual());
             
             
             IntegerChromosome chromosome = (IntegerChromosome)ga.getPopulation().getBestIndividual().getChromosome();
             int array[] = chromosome.toArray();
                         
             
             Sudoku solution = new Sudoku(array);
             double duplicates = solution.countDuplicates();
             ga.getPopulation().getBestIndividual().setCustomData(array);
             
            double fitness = r.getBestFitness();
            if(prevFitness == fitness)
            {
                staleCount++;
            }
            else
            {
                
                staleCount = 0;
                prevFitness = fitness;
            }
            double sigma = 0.5;
            double mutationRate = Math.abs(new Random().nextGaussian() * sigma);
        
            while (mutationRate > 1) {
            mutationRate = Math.abs(new Random().nextGaussian() * sigma);
            }
            
            ga.setMutationRate(mutationRate);
            
            
              System.out.println("Duplicates: " + solution.countDuplicates());
              System.out.println("Evaluation execution: " + r.getEvolutionDuration().toMillis() + "ms");
              
              
            if(staleCount >= 100)
            {
                System.out.println("The population has gone stale. Re-seeding...");
                sleep(4);
                ga.setMutationRate(0.5);
                initializePopulation();
                ga.setPopulation(population);
                //Population population = ga.getPopulation();
                
                staleCount = 0;
                  Individual bestIndividual1 = ga.getPopulation().getBestIndividual();
                IntegerChromosome ch = (IntegerChromosome)bestIndividual1.getChromosome();
                int arrays[] = ch.toArray();
        
                Sudoku solution1 = new Sudoku(arrays);
        
                Sudoku.print2DArray(solution1.getTable());
                
            }
              
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
        
        System.out.println("--------------------------------------------------");
//        for(int i=0; i<ga.getPopulation().size(); i++)
//        {
//            System.out.println("Fitness value: " + ga.getPopulation().getIndividual(i).getFitnessScore());
//        }
        
//        for(int i=0; i<ga.getPopulation().size(); i++)
//        {
//            System.out.println("Fitness value: " + ga.getPopulation().getIndividual(i));
//        }
        
        
        Individual bestIndividual1 = ga.getPopulation().getBestIndividual();
        IntegerChromosome chromosome = (IntegerChromosome)bestIndividual1.getChromosome();
        int array[] = chromosome.toArray();
        
        Sudoku solution = new Sudoku(array);
        
        Sudoku.print2DArray(solution.getTable());
        
        System.out.println("");
        
        solution.saveGame(fileSave);
        
        //System.out.println("generation: " + ga.getGeneration());
    }
    
    private static void initializePopulation()
    {
        population.clear();
        for(int i=0; i<populationSize; i++)
        {
           
            IntegerChromosome integerChromosome = new IntegerChromosome();
            int row = 0;
            for(int j=0; j<9; j++)
            {
                row = j;
                int array[] = new int[9];
                for(int k=0; k<9; k++)
                {
                    array[k] = sudoku[row*9 + k];
                }
                fillRandomUniqueDigits(array);
                for(int k=0; k<9; k++)
                {
                    integerChromosome.addGene(new IntegerGene(array[k]));
                }
            }
            //System.out.println("sizeeee: " + integerChromosome.length());
            Individual individual = new Individual(integerChromosome);
            population.addIndividual(individual);
        }
        
       
        for(int i=0; i<populationSize; i++)
        {
            Individual individual = population.getIndividual(i);
            IntegerChromosome chromosome = (IntegerChromosome)individual.getChromosome();
            for(int j=0; j<getDoNotMutateList().size(); j++)
            {
                chromosome.setGene(getDoNotMutateList().get(j), new IntegerGene(sudoku[getDoNotMutateList().get(j)]));
            }
        }
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
    
    public static boolean arraysAreEqual(int[] array1, int[] array2) {
    if (array1 == null || array2 == null) {
        return false;
    }

    if (array1.length != array2.length) {
        return false;
    }

    for (int i = 0; i < array1.length; i++) {
        if (array1[i] != array2[i]) {
            return false;
        }
    }

    return true;
}
    public static void getDifferingIndices(int[] array1, int[] array2) {
    List<Integer> differingIndices = new ArrayList<>();

    if (array1 == null || array2 == null) {
        System.out.println("are null");
        return;
    }

    int minLength = Math.min(array1.length, array2.length);

    for (int i = 0; i < minLength; i++) {
        if (array1[i] != array2[i]) {
            differingIndices.add(i);
        }
    }

    // If the arrays have different lengths, add the remaining indices
    for (int i = minLength; i < array1.length; i++) {
        differingIndices.add(i);
    }

    for (int i = minLength; i < array2.length; i++) {
        differingIndices.add(i);
    }
    
    for(int i=0; i<differingIndices.size(); i++)
    {
        System.out.println("index: " + differingIndices.get(i));
    }
    
    
    

    //return differingIndices;
}
    
    
    public static void printArray(int array[])
    {
        for(int i=0; i<array.length; i++)
        {
            System.out.print(array[i] + " ");
        }
        System.out.println("");
    }
    
    private static void sleep(int sec)
    {
              try {
                  Thread.sleep(sec);
              } catch (InterruptedException ex) {
                  Logger.getLogger(SudokuGA.class.getName()).log(Level.SEVERE, null, ex);
              }
    }
}
