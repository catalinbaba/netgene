/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netgene.ga.mutator;

import org.netgene.ga.core.Individual;
import org.netgene.ga.exception.MutatorException;

/**
 *
 * @author Catalin Baba
 */
public abstract class MutatorOperator 
{
    protected double mutationRate = 0.05;  //equivalent to 5%
    
    public abstract void mutate(Individual individual) throws MutatorException;
    
    public void setMutationRate(double mutationRate) throws MutatorException
    {
        if(mutationRate < 0 || mutationRate > 1.0)
            throw new MutatorException("Mutation rate must take a value between 0.0 and 1.0");
        this.mutationRate = mutationRate;
    }

    public double getMutationRate() 
    {
        return mutationRate;
    }
}
