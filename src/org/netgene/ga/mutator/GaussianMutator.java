/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netgene.ga.mutator;

import java.io.Serializable;
import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.DoubleChromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.DoubleGene;
import org.netgene.utils.RandomUtils;

/**
 * The GaussianMutator class performs the mutation of a NumericGene. 
 * This mutator picks a new value based on a Gaussian distribution around the current value of the gene. 
 * 
 * @author Catalin Baba
 */
public class GaussianMutator extends MutatorOperator implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected double sigma = 0.5;
      
    /**
     * Create a new GaussianMutator instance
     */
    public GaussianMutator(){}
    
    /**
     * Create a new GaussianMutator instance with the specified standard deviation
     * 
     * @param sigma standard deviation
     */
    public GaussianMutator(double sigma)
    {
        this.sigma = sigma;
    }

    /**
     * Set standard deviation
     * 
     * @param sigma standard deviation
     */
    public void setStandardDeviation(double sigma)
    {
        this.sigma = sigma;
    }
    
    /**
     * Get standard deviation
     * 
     * @return standard deviation
     */
    public double getStandardDeviation()
    {
        return this.sigma;   
    }
          
    private void mutate(DoubleChromosome chromosome)
    {
        for(int i=0 ;i<chromosome.length(); i++)
        {
            double rand = RandomUtils.nextDouble();
            if(rand < mutationRate) //is selected to be mutated
            {
                double delta = RandomUtils.nextGaussian(this.sigma);
                double value = delta + chromosome.getGene(i).getAllele();
                DoubleGene gene = new DoubleGene(value);
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
        if(!(individual.getChromosome() instanceof DoubleChromosome))
            throw new MutatorException("Gaussian Mutator is applicable only for double chromosomes");
        mutate((DoubleChromosome)individual.getChromosome());
    }
    
}
