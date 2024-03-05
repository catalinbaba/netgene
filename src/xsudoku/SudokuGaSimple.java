/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xsudoku;

import org.netgene.ga.GenerationResult;
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Population;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.GenerationTracker;
import org.netgene.ga.GeneticAlgorithm;
import org.netgene.ga.GeneticConfiguration;
import org.netgene.ga.crossover.*;
import org.netgene.ga.fitness.FitnessFunction;
import org.netgene.ga.mutator.*;
import org.netgene.ga.selection.parent.*;


/**
 *
 * @author cbaba
 */
public class SudokuGaSimple 
{
    
    private static int populationSize = 1000;
    private static int chromosomeSize = 81;
     
     
    private static Population population = new Population();
    
    
    
    public static void main(String[] args) {
        
        
        TournamentSelector selector = new TournamentSelector(10);
        //RankSelector selector = new RankSelector();
        TwoPointCrossover crossover = new TwoPointCrossover();
        //Order1Crossover crossover = new Order1Crossover();
        //UniformCrossover crossover = new UniformCrossover();
        //IntegerMutator mutator = new IntegerMutator(1,10);
        SwapMutator mutator = new SwapMutator();
        //InversionMutator mutator = new InversionMutator();
        //ScrambleMutator mutator = new ScrambleMutator();
        crossover.setSingleOff(true);
        
        GeneticAlgorithm ga = new GeneticConfiguration()
                                                  .setParentSelector(selector)
                                                  .setMutatorOperator(mutator)
                                                  .setCrossoverOperator(crossover)
                                                  .setElitismSize(10)
                                                  .setMaxGeneration(10000)
                                                  .setThreadPoolSize(6)
                                                  .setTargetFitness(1)
                                                  //.setMutationRate(1.0)
                                                  //.skipCrossover()
                                                  //.setCrossoverRate(0.9)
                                                  .getAlgorithm();
        
        for(int i=0; i<populationSize; i++)
        {
            
            IntegerChromosome integerChromosome = new IntegerChromosome(chromosomeSize, 1, 10);
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
        //boolean done = false;
        myTracker = new GenerationTracker() {
            boolean done = false;
            int generation = 0;
            @Override
            public void track(GeneticAlgorithm g, GenerationResult r) {
                System.out.println("Step: " + r.getGenerationNumber());
                System.out.println("best fitness: " + r.getBestFitness());
                System.out.println("lowest fitness: " + g.getPopulation().getIndividual(populationSize-1).getFitnessScore());
                IntegerChromosome chromosome = (IntegerChromosome)ga.getPopulation().getBestIndividual().getChromosome();
                int array[] = chromosome.toArray();
                Sudoku solution = new Sudoku(array);
                double duplicates = solution.countDuplicates();
                generation++;
                if(duplicates <16)
                {
                    //System.out.println("aici");
                    ga.setMutationRate(0.8);
                    //ga.changeMutatorOperator(new IntegerMutator(1,10));
                    //ga.setCrossoverRate(1);
                }
                if(duplicates <= 6)
                {
                    //ga.setMutationRate(1.0);
                    if(generation ==20)
                    {
                        Population population = g.getPopulation();
                        for(int i=populationSize/2; i<populationSize; i++)
                        {
                            IntegerChromosome integerChromosome = new IntegerChromosome(chromosomeSize, 1, 10);
                            Individual individual = new Individual(integerChromosome);
                            population.replaceIndividual(i, individual);
                        }
                        //done = true;
                        generation =0;
                    }
                    
                }
              
                
                
                System.out.println("Duplicates: " + solution.countDuplicates());
                System.out.println("Evaluation execution: " + r.getEvolutionDuration().toMillis() + "ms");
                System.out.println("----------------------------------------------------");
            }
        };
        
        ga.setGenerationTracker(myTracker);
        //ga.setCustomStopCondition(customStopCondition);
        ga.evolve(population, exFitness);
        
        
        
        System.out.println("--------------------------------------------------");
        /*
        for(int i=0; i<populationSize/2; i++)
        {
            Individual indv = ga.getPopulation().getIndividual(i);
            
            IntegerChromosome ch = (IntegerChromosome)indv.getChromosome();
            int array2[] = ch.toArray();
        
            Sudoku sol = new Sudoku(array2);
        
            Sudoku.print2DArray(sol.getTable());
            
            System.out.println("Fitness: " + ga.getPopulation().getIndividual(i).getFitnessScore());
           
            System.out.println("--------------------------------------------------");
            
        }
        */
        System.out.println("");
        
        Individual bestIndividual = ga.getPopulation().getBestIndividual();
        
        double fitnessValue = ga.getPopulation().getBestIndividual().getFitnessScore();
        
        System.out.println("individual: " + ga.getPopulation().getBestIndividual());
        System.out.println("fitness value: " + fitnessValue);
        
        IntegerChromosome chromosome = (IntegerChromosome)bestIndividual.getChromosome();
        int array[] = chromosome.toArray();
        
        Sudoku solution = new Sudoku(array);
        
        Sudoku.print2DArray(solution.getTable());
        
        System.out.println("--------------------------------------------------");
        
    }
}
