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
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Population;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.fitness.FitnessFunction;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.ga.selection.parent.*;

/**
 *
 * @author cbaba
 */
public class SudokuGA_run 
{
    
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
          
    private static int populationSize = 1000;
          
          
        public static void main(String [] args)
        {
            Population population = new Population();
            
            
            TournamentSelector selector = new TournamentSelector(10);
            
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
                Individual individual = new Individual(integerChromosome);
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
        
        //fitness function
        
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
}
