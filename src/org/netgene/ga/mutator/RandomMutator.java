/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netgene.ga.mutator;

import java.io.Serializable;
import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.DoubleChromosome;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.chromosome.NumericChromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.DoubleGene;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.utils.RandomUtils;

/**
 * In random resetting mutation, we select one or more genes  and replace their values with another random value from their given ranges.
 * 
 * @author Catalin Baba
 */
public class RandomMutator extends GaussianMutator implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private static int range = 1;
    
    /**
     * Create a new GaussianMutator instance
     */
    public RandomMutator() {}
    
    /**
     * Create a new RandomMutator instance by setting the mutation rate
     * 
     * @param mutationRate mutation rate
     */       
    public RandomMutator(double mutationRate)
    {
        this.mutationRate = mutationRate;
    }
       
    private void mutate(DoubleChromosome chromosome)
    {
        for(int i=0 ;i<chromosome.length(); i++)
        {
            double rand = RandomUtils.nextDouble();
            if(rand < mutationRate) //is selected to be mutated
            {
                double delta = RandomUtils.nextGaussian(this.sigma);
                DoubleGene gene = new DoubleGene(delta);
                    
                chromosome.setGene(i, gene);
            }
        }
    }
    
    private void mutate(IntegerChromosome chromosome)
    {
        for(int i=0 ;i<chromosome.length(); i++)
        {
            double rand = RandomUtils.nextDouble();
            if(rand < mutationRate) //is selected to be mutated
            {
                double delta = RandomUtils.nextGaussian(this.sigma)*10;
                IntegerGene gene = new IntegerGene((int)delta);
                    
                chromosome.setGene(i, gene);
            }
        }
    }
    
     /**
     * Mutate an individual chromosome
     * 
     * @param individual individual to mutate
     */
    @Override
    public void mutate(Individual individual) throws MutatorException {
        if(!(individual.getChromosome() instanceof NumericChromosome))
            throw new MutatorException("Random Mutator is applicable only for numeric chromosomes");
        if(individual.getChromosome() instanceof DoubleChromosome)
            mutate((DoubleChromosome)individual.getChromosome());
        if(individual.getChromosome() instanceof IntegerChromosome)
            mutate((IntegerChromosome)individual.getChromosome());
    }
  
}
