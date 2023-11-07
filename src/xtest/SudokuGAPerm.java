/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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
public class SudokuGAPerm{
       
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
     
    private static Population population = new Population();
    
    private static int chromosomeSize = 81;
    
    private static boolean isMuted = false;
    
    public static void main(String[] args) throws Exception
    {
        
        TournamentSelector selector = new TournamentSelector(10);
        //RouletteSelector selector = new RouletteSelector();
        //RankSelector selector = new RankSelector();
        //OnePointCrossover oc = new OnePointCrossover();
        //TwoPointCrossover oc = new TwoPointCrossover();
        CustomPermMutator mutator = new CustomPermMutator();
        //InversionMutator inm = new InversionMutator();
        //IntegerMutator im = new IntegerMutator();
        //UniformCrossover crossover = new UniformCrossover();
        //CustomMutator mutator = new CustomMutator();
        //mutator.setDoNotMutateList(getDoNotMutateList());
        CustomCrossover crossover = new CustomCrossover();
        mutator.setDoNotMutateList(getDoNotMutateList());
        
        GeneticAlgorithm ga = new GeneticConfiguration()
                                                  .setParentSelector(selector)
                                                  //.setElitismSize(2)
                                                  .setMutatorOperator(mutator)
                                                  .setCrossoverOperator(crossover)
                                                  //.setMutationRate(0.25)
                                                  //.setCrossoverRate(0.25)
                                                  //.skipMutation()
                                                  .setElitismSize(100)
                                                  .setMaxGeneration(100000)
                                                  .setThreadPoolSize(6)
                                                  .setTargetFitness(1)
                                                  .getAlgorithm();
        
        
        
         
        int populationSize = 10000;
        
        
        
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
        
      
        FitnessFunction exFitness = (individual) ->
        {
            //BitChromosome chromosome = individual.getChromosome();
            IntegerChromosome chromosome = (IntegerChromosome)individual.getChromosome();
            double initialFitness = individual.getFitnessScore();
            int array[] = chromosome.toArray();
            Sudoku sd = new Sudoku(array);
            //double fitness = sd.calculateFitnessScore();
          
            double fitness = sd.countDuplicates();
            
                
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

            if(duplicates <15)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.1);
                //ga.setCrossoverRate(1);
            }
            if(duplicates <=20)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.5);
                //ga.setCrossoverRate(1);
                CustomMutator mut = new CustomMutator();
                mut.setDoNotMutateList(getDoNotMutateList());
                ga.setMutatorOperator(mut);
                isMuted = true;
            }/*
            if(duplicates <=6)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.3);
                //ga.setCrossoverRate(1);
            }
            if(duplicates <=4)
            {
                //System.out.println("aici");
                ga.setMutationRate(0.5);
                //ga.setCrossoverRate(1);
            }
            */
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
    
    public static void printArray(int array[])
    {
        for(int i=0; i<array.length; i++)
        {
            System.out.print(array[i] + " ");
        }
        System.out.println("");
    }
    
   public static void fillUniqueDigits(int[] array) {
        Set<Integer> fixedDigits = new HashSet<>();
        for (int num : array) {
            if (num != 0) {
                fixedDigits.add(num);
            }
        }
        
        int index = 0;
        for (int i = 1; i <= 9; i++) {
            if (!fixedDigits.contains(i)) {
                while (array[index] != 0) {
                    index++;
                }
                array[index] = i;
                index++;
            }
        }
    }
   
   /*
   The fillUniqueDigits method takes the array as input and first identifies the fixed digits by iterating over the array and adding non-zero values to a fixedDigits set.

Then, it iterates from 1 to 9 and for each number that is not in the fixedDigits set, it searches for the next available index in the array (where the value is 0) and assigns the number to that index.

In the main method, you can call fillUniqueDigits passing the array you provided, and then print the modified array to see the result. The output will be the modified array with the fixed values unchanged and the remaining indices filled with the unique digits from 1 to 9.
   */
   
   
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

   
   /*
   In this updated code, the fillRandomUniqueDigits method is modified to fill the remaining indices with random unique digits. It uses a Random object to generate random numbers.

The method first identifies the fixed digits by iterating over the array and adding non-zero values to the fixedDigits set.

Then, it iterates over the array and checks for indices where the value is 0. For each such index, it generates a random digit using random.nextInt(9) + 1, which gives a random number between 1 and 9. It continues generating random digits until it finds one that is not present in the fixedDigits set. Finally, it assigns the random digit to the current index in the array.

In the main method, you can call fillRandomUniqueDigits passing the array you provided, and then print the modified array to see the result. The output will be the modified array with the fixed values unchanged and the remaining indices filled with random unique digits from 1 to 9.

  
  
  In this updated code, the fillRandomUniqueDigits method creates an array called remainingDigits to store the unique digits from 1 to 9 that are not present in the fixed digits. It uses a random index to select a digit from remainingDigits and fills the empty indices in the array with those random digits, while also performing a swap to remove the selected digit from remainingDigits.

Now, when you run the main method, the array will be filled with the fixed values unchanged and the remaining indices will be filled with random unique digits from 1 to 9, ensuring that no duplicates are present.






   */
    
}
