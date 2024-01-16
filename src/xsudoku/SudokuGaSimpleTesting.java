/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xsudoku;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Population;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.GenerationTracker;
import org.netgene.ga.GeneticAlgorithm;
import org.netgene.ga.GeneticConfiguration;
import org.netgene.ga.crossover.*;
import org.netgene.ga.fitness.FitnessFunction;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.ga.mutator.*;
import org.netgene.ga.selection.parent.*;
import xtest.SudokuGA;


/**
 *
 * @author cbaba
 */
public class SudokuGaSimpleTesting {
    
    private static int populationSize = 500;
    private static int chromosomeSize = 81;
     
     
    private static Population population = new Population();
    private static double prevFitness = 0;
    private static int staleCount = 0;
    private static int incr = 0;
    private static int elitismSize = 120;
    
    
    public static void main(String[] args) {
        
        
        //TournamentSelector selector = new TournamentSelector(10);
        RankSelector selector = new RankSelector();
        TwoPointCrossover crossover = new TwoPointCrossover();
        //IntegerMutator mutator = new IntegerMutator(1,10);
        SwapMutator mutator = new SwapMutator();
        //CustomMutator mutator = new CustomMutator();
        //InversionMutator mutator = new InversionMutator();
       
        GeneticAlgorithm ga = new GeneticConfiguration()
                                                  .setParentSelector(selector)
                                                  .setMutatorOperator(mutator)
                                                  .setCrossoverOperator(crossover)
                                                  .setElitismSize(elitismSize)
                                                  .setMaxGeneration(2000)
                                                  .setThreadPoolSize(6)
                                                  .setTargetFitness(1)
                                                  //.setMutationRate(0.3)
                                                  //.setCrossoverRate(0.9)
                                                  .getAlgorithm();
        
//        for(int i=0; i<populationSize; i++)
//        {
//            
//            IntegerChromosome integerChromosome = new IntegerChromosome(chromosomeSize, 1, 10);
//            Individual individual = new Individual(integerChromosome);
//            population.addIndividual(individual);
//        }
        
        for(int i=0; i<populationSize; i++)
        {
            
            IntegerChromosome integerChromosome = new IntegerChromosome();
            int row = 0;
            for(int j=0; j<9; j++)
            {
                row = j;
                int array[] = new int[9];
                fillRandomUniqueDigits(array);
                for(int k=0; k<9; k++)
                {
                    integerChromosome.addGene(new IntegerGene(array[k]));
                }
            }
            Individual individual = new Individual(integerChromosome);
            population.addIndividual(individual);
        }
        
        FitnessFunction exFitness = (individual) ->
        {
            //BitChromosome chromosome = individual.getChromosome();
            IntegerChromosome chromosome = (IntegerChromosome)individual.getChromosome();
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
            IntegerChromosome chromosome = (IntegerChromosome)ga.getPopulation().getBestIndividual().getChromosome();
            int array[] = chromosome.toArray();
            Sudoku solution = new Sudoku(array);
            double duplicates = solution.countDuplicates();

//            if(duplicates <15)
//            {
//                //System.out.println("aici");
//                ga.setMutationRate(0.5);
//                //ga.changeMutatorOperator(new IntegerMutator(1,10));
//                //ga.setCrossoverRate(1);
//            }
            
//             double sigma = 0.5;
//            double mutationRate = Math.abs(new Random().nextGaussian() * sigma);
//        
//            while (mutationRate > 1) {
//            mutationRate = Math.abs(new Random().nextGaussian() * sigma);
//            }
//            
//            ga.setMutationRate(mutationRate);
            
            
            
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
            
             if(staleCount >= 100)
            {
                System.out.println("The population has gone stale. Re-seeding...");
                sleep(4);
                ga.setMutationRate(0.5);
                initializePopulation();
                ga.setPopulation(population);
                incr++;
                //Population population = ga.getPopulation();
                
                staleCount = 0;
                Individual bestIndividual1 = ga.getPopulation().getBestIndividual();
                IntegerChromosome ch = (IntegerChromosome)bestIndividual1.getChromosome();
                int arrays[] = ch.toArray();
        
                Sudoku solution1 = new Sudoku(arrays);
        
                Sudoku.print2DArray(solution1.getTable());
                
            }
            
            
            
            System.out.println("Duplicates: " + solution.countDuplicates());
            System.out.println("Evaluation execution: " + r.getEvolutionDuration().toMillis() + "ms");
            System.out.println("----------------------------------------------------");
        };
        
        ga.setGenerationTracker(myTracker);
        //ga.setCustomStopCondition(customStopCondition);
        ga.evolve(population, exFitness);
        
        
        
        System.out.println("--------------------------------------------------");
        
//        for(int i=0; i<10; i++)
//        {
//            Individual indv = ga.getPopulation().getIndividual(i);
//            
//            IntegerChromosome ch = (IntegerChromosome)indv.getChromosome();
//            int array2[] = ch.toArray();
//        
//            Sudoku sol = new Sudoku(array2);
//        
//            Sudoku.print2DArray(sol.getTable());
//            
//            System.out.println("Fitness: " + ga.getPopulation().getIndividual(i).getFitnessScore());
//           
//            System.out.println("--------------------------------------------------");
//            
//        }
        
        System.out.println("");
        
        Individual bestIndividual = ga.getPopulation().getBestIndividual();
        
        double fitnessValue = ga.getPopulation().getBestIndividual().getFitnessScore();
        
        System.out.println("individual: " + ga.getPopulation().getBestIndividual());
        System.out.println("fitness value: " + fitnessValue);
        
        IntegerChromosome chromosome = (IntegerChromosome)bestIndividual.getChromosome();
        int array[] = chromosome.toArray();
        
        Sudoku solution = new Sudoku(array);
        
        Sudoku.print2DArray(solution.getTable());
        
        System.out.println("incr: " + incr);
        
        System.out.println("--------------------------------------------------");
        
    }
    
    public static void fillRandomUniqueDigits(int[] array) {
        Set<Integer> fixedDigits = new HashSet<>();
        for (int num : array) {
            if (num != 0) {
                fixedDigits.add(num);
            }
        }

        int[] remainingDigits = new int[9 - fixedDigits.size()];
        int index = 0;
        for (int i = 1; i <= 9; i++) {
            if (!fixedDigits.contains(i)) {
                remainingDigits[index] = i;
                index++;
            }
        }

        Random random = new Random();
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == 0) {
                int randomIndex = random.nextInt(index);
                array[i] = remainingDigits[randomIndex];
                remainingDigits[randomIndex] = remainingDigits[index - 1];
                index--;
            }
        }
    }
    
     private static void initializePopulation()
    {
        //population.clear();
        for(int i=elitismSize/2; i<populationSize; i++)
        {
           
            IntegerChromosome integerChromosome = new IntegerChromosome();
            int row = 0;
            for(int j=0; j<9; j++)
            {
                row = j;
                int array[] = new int[9];
                fillRandomUniqueDigits(array);
                for(int k=0; k<9; k++)
                {
                    integerChromosome.addGene(new IntegerGene(array[k]));
                }
            }
            //System.out.println("sizeeee: " + integerChromosome.length());
            Individual individual = new Individual(integerChromosome);
            population.replaceIndividual(i, individual);
            //System.out.println("POP SIZE: " + population.size());
        }
        
       
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