/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xsudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.core.Individual;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.Gene;
import org.netgene.ga.mutator.MutatorOperator;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class CustomMutator2 extends MutatorOperator
{

    @Override
    public void mutate(Individual individual) throws MutatorException {
        Chromosome chromosome = individual.getChromosome();
        
        List<Integer> nonZeroIndexes = new ArrayList<>();
        for (int i = 0; i < SudokuGame.sudoku.length; i++) {
            if (SudokuGame.sudoku[i] != 0) {
                nonZeroIndexes.add(i);
            }
        }

        Random random = new Random();
        int index1 = random.nextInt(nonZeroIndexes.size());
        int index2;
        do {
            index2 = random.nextInt(nonZeroIndexes.size());
        } while (index2 == index1);

        int firstIndex = nonZeroIndexes.get(index1);
        int secondIndex = nonZeroIndexes.get(index2);
        
        double rand = RandomUtils.nextDouble();
                
        if(rand < mutationRate) //is selected to be mutated
        {
            //System.out.println("mutate");
            Gene firstGene = chromosome.getGene(firstIndex);
                
            chromosome.setGene(firstIndex, chromosome.getGene(secondIndex));
            chromosome.setGene(secondIndex, firstGene);
        }
    }
    
}
