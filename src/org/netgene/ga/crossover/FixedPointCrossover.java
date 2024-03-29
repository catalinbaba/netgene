/*
 * Java Genetic Algorithm and Neural Network Library.
 * Copyright (c) @2020 @ Catalin Baba
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Catalin Baba (catalin.viorelbaba@gmail.com)
*/

package org.netgene.ga.crossover;

import java.io.Serializable;
import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.core.Offspring;
import org.netgene.ga.chromosome.PermutationChromosome;
import org.netgene.ga.exception.CrossoverException;

/**
 * In this crossover, a fixed crossover point is selected and the tails of its two parents are swapped to get new off-springs.
 * 
 * @author cbaba
 * 
 * @param <C> Chromosome type
 */
public final class FixedPointCrossover<C extends Chromosome<?>> extends CrossoverOperator<C> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int fixedPoint = 0;
    
    /**
     * Create a new FixedPointCrossover with a specific fixed point
     * 
     * @param fixedPoint fixed point
     */
    public FixedPointCrossover(int fixedPoint)
    {
        super();
        this.fixedPoint = fixedPoint;
    }
    
    /**
     * Create a new FixedPointCrossover with a specific fixed point and set if the one offspring is generated
     * 
     * @param fixedPoint fixed point
     * 
     * @param singleOffspring true if only one offspring is generated
     */
    public FixedPointCrossover(int fixedPoint, boolean singleOffspring)
    {
        super();
        this.fixedPoint = fixedPoint;
        super.singleOffspring = singleOffspring;
    }
    
     /**
     * Combine two individuals
     * 
     * @param x first individual to combine
     * 
     * @param y second individual to combine
     * 
     * @return offspring offspring
     * 
     * @throws CrossoverException if cannot recombine two individuals
     */    
    @Override
    public Offspring recombine(Individual x, Individual y) throws CrossoverException
    { 
        Chromosome firstOffspring = x.getChromosome().copy();
        Chromosome secondOffspring = y.getChromosome().copy();
        
        if(firstOffspring instanceof PermutationChromosome || secondOffspring instanceof PermutationChromosome)
            throw new CrossoverException("Cannot use Fixed Point Crossover for Permutation Chromosome. Only Permutation Crossover Operators are allowed");
        if(firstOffspring.length()!= secondOffspring.length())
            throw new CrossoverException("Cannot recombine chromosomes with different lengths");
        if(fixedPoint > firstOffspring.length())
            throw new CrossoverException("Fixed crossover point is greather than the cromosome length");
        if(fixedPoint < 0)
            throw new CrossoverException("Fixed crossover point is less than 0");
        Offspring offspring= new Offspring();
         
        for(int i=fixedPoint; i<firstOffspring.length(); i++)
            swap(firstOffspring, secondOffspring, i);
        
        if(!singleOffspring)
        {
            offspring.addOffspring(new Individual(firstOffspring));
            offspring.addOffspring(new Individual(secondOffspring));
        }
        else
        {
            offspring.addOffspring(new Individual(firstOffspring));
        }
        return offspring;
    }
    
}
