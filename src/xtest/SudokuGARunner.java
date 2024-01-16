/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import java.util.ArrayList;
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Population;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.GenerationTracker;
import org.netgene.ga.GeneticAlgorithm;
import org.netgene.ga.GeneticConfiguration;
import org.netgene.ga.crossover.TwoPointCrossover;
import org.netgene.ga.fitness.FitnessFunction;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.ga.selection.parent.TournamentSelector;
import static xtest.SudokuGAPerm.fillRandomUniqueDigits;

/**
 *
 * @author cbaba
 */
public class SudokuGARunner 
{
    
    
          private int[] sudoku = {
        0,3,0,0,7,0,0,5,0,
        5,0,0,1,0,6,0,0,9,
        0,0,1,0,0,0,4,0,0,
        0,9,0,0,5,0,0,6,0,
        6,0,0,4,0,2,0,0,7,
        0,4,0,0,1,0,0,3,0,
        0,0,2,0,0,0,8,0,0,
        9,0,0,3,0,5,0,0,2,
        0,1,0,0,2,0,0,7,0};
   private Population population = new Population();
    
 
    
    public SudokuGARunner()
    {
        
    }
    
    public void run(int runNr) throws Exception
    {
        
        TournamentSelector selector = new TournamentSelector(10);
        //RouletteSelector selector = new RouletteSelector();
        //RankSelector selector = new RankSelector();
        //OnePointCrossover oc = new OnePointCrossover();
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
                                                  .setElitismSize(10)
                                                  //.setElitism(false)
                                                  .setMaxGeneration(10000)
                                                  .setThreadPoolSize(6)
                                                  .setTargetFitness(1)
                                                  .getAlgorithm();
        
        
        
         
        int populationSize = 5000;
        int chromosomeSize = 81;
        
        //for
        
        //Sudoku partialSolution = new Sudoku();
       
           
        for(int i=0; i<populationSize; i++)
        {
            IntegerChromosome chromosome = new IntegerChromosome(chromosomeSize, 1, 10);
            Individual individual = new Individual(chromosome);
            population.addIndividual(individual);
        }
        
              
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
             //System.out.println("Step: " + r.getGenerationNumber());
             //System.out.println("best fitness: " + r.getBestFitness());
             //System.out.println("lowest fitness: " + g.getPopulation().getIndividual(populationSize-1).getFitnessScore());
             
             
             IntegerChromosome chromosome = (IntegerChromosome)ga.getPopulation().getBestIndividual().getChromosome();
             int array[] = chromosome.toArray();
                         
             
            Sudoku solution = new Sudoku(array);
            double duplicates = solution.countDuplicates();
            ga.getPopulation().getBestIndividual().setCustomData(array);
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
                ga.setMutationRate(0.4);
                //ga.setCrossoverRate(1);
            }
            if(duplicates <=4)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.5);
                //ga.setCrossoverRate(1);
            }
            if(duplicates <=2)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.7);
                //ga.setCrossoverRate(1);
            }
            if(duplicates >15)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.05);
                //ga.setCrossoverRate(1);
            }
            if(g.getGeneration() > 9990)
            {
                System.out.println("Generation: " + g.getGeneration());
              System.out.println("Duplicates: " + solution.countDuplicates());
              System.out.println("Evaluation execution: " + r.getEvolutionDuration().toMillis() + "ms");
              System.out.println("Evolution execution: " + r.getEvolutionDuration().toMillis() + "ms");
              System.out.println("Evaluation execution: " + r.getEvaluationDuration().toMillis() + "ms");
              System.out.println("Generation execution: " + r.getGenerationDuration().toMillis()+ "ms");
              System.out.println("Best fitness: " +g.getPopulation().getBestIndividual().getFitnessScore());
              System.out.println("Lowest fitness: " + g.getPopulation().getIndividual(0).getFitnessScore());
              //System.out.println("custom data: " + g.getPopulation().getBestIndividual().getCustomData());
              //System.out.println("----------------------------------------------------");
            }
         };
        
        ga.setGenerationTracker(myTracker);
        //ga.setCustomStopCondition(customStopCondition);
        System.out.println("Start running run number: " + runNr);
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
        System.out.println("----------------------------------");
        System.out.println("");
        
        //solution.saveGame(fileSave);
        
        //System.out.println("generation: " + ga.getGeneration());
    }
    
    private ArrayList<Integer> getDoNotMutateList()
    {
        ArrayList<Integer> doNotMutateList = new ArrayList();
        for(int i=0; i<81; i++)
        {
            if(sudoku[i] != 0)
              doNotMutateList.add(i);
        }
        return doNotMutateList;
    }
}
