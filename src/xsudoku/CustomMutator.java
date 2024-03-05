/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xsudoku;

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
public class CustomMutator extends MutatorOperator
{

    @Override
    public void mutate(Individual individual) throws MutatorException {
        Chromosome chromosome = individual.getChromosome();
        int firstIndex = RandomUtils.nextInt(chromosome.length());
        int secondIndex = RandomUtils.nextInt(chromosome.length());
        double rand = RandomUtils.nextDouble();
        if(firstIndex == secondIndex)  //if both positions are the same
        {
            if(firstIndex == 0)
                secondIndex = firstIndex + 1; 
            else
                secondIndex = firstIndex - 1; 
        }
        if(firstIndex == 1 || secondIndex ==1)
        {
            return;
        }
        if(rand < mutationRate) //is selected to be mutated
        {
            //System.out.println("mutate");
            Gene firstGene = chromosome.getGene(firstIndex);
                
            chromosome.setGene(firstIndex, chromosome.getGene(secondIndex));
            chromosome.setGene(secondIndex, firstGene);
        }
    }
    
}
