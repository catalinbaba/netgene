/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netgene.ga.mutator;

import java.io.Serializable;
import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class IntegerMutator extends MutatorOperator implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final int minRange;
    private final int maxRange;
    
    public IntegerMutator(final int minRange, final int maxRange)
    {
        this.minRange = minRange;
        this.maxRange = maxRange;
    }
    
    public int getMinRange()
    {
        return this.minRange;
    }
    
    public int getMaxRange()
    {
        return this.maxRange;
    }
    
    private void mutate(Chromosome chromosome)  
    {
       double rand = RandomUtils.nextDouble();
        if(rand < mutationRate) //is selected to be mutated
        {
            int randomIndex = RandomUtils.nextInt(chromosome.length()); //which gene to mutate
            int value = RandomUtils.nextInt(minRange, maxRange);
            IntegerGene gene = new IntegerGene(value);
            chromosome.setGene(randomIndex, gene);
        }
    }

     /**
     * Mutate an individual chromosome
     * 
     * @param individual individual to mutate
     */
    @Override
    public void mutate(Individual individual) throws MutatorException 
    {
        mutate(individual.getChromosome());
    }
    
}
