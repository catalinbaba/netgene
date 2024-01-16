/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import java.util.ArrayList;
import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.Gene;
import org.netgene.ga.mutator.MutatorOperator;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class CustomPermMutator extends MutatorOperator
{
    private ArrayList<Integer> doNotMutate = new ArrayList();
    
    protected double sigma = 0.5;
    
    
    public void setDoNotMutateList(ArrayList doNotMutate)
    {
        this.doNotMutate = doNotMutate;
    }
    
    
     private void mutateSwap(Individual individual)
    {
        Chromosome chromosome = (IntegerChromosome)individual.getChromosome();
        int firstIndex = RandomUtils.nextInt(0,9);
        int secondIndex = RandomUtils.nextInt(0,9);
        if(firstIndex == secondIndex)  //if both positions are the same
        {
            if(firstIndex == 0)
                secondIndex = firstIndex + 1; 
            else
                secondIndex = firstIndex - 1; 
        }
        int row = RandomUtils.nextInt(9);
        firstIndex = row*9 + firstIndex;
        secondIndex = row*9 + secondIndex;
        double rand = RandomUtils.nextDouble();
        if(rand < mutationRate) //is selected to be mutated
        {
            if(doNotMutate.contains(firstIndex) || doNotMutate.contains(secondIndex))
                return;
            
            Gene firstGene = chromosome.getGene(firstIndex);
             
            chromosome.setGene(firstIndex, chromosome.getGene(secondIndex));
            chromosome.setGene(secondIndex, firstGene);
        }
    }

    @Override
    public void mutate(Individual individual) throws MutatorException {
       mutateSwap(individual);
    }
}
