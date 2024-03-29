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
import org.netgene.utils.RandomUtils;

/**
 * Is a generalization of the one-point crossover wherein alternating segments are swapped to get new off-springs.
 * 
 * @author Catalin Baba
 * 
 * @param <C> chromosome type
 */
public class TwoPointCrossover<C extends Chromosome<?>> extends CrossoverOperator<C> implements Serializable
{    
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new TwoPointCrossover instance with two random points generated
     * 
     */
    public TwoPointCrossover()
    {
        this.singleOffspring = false;
    }
    
    /**
     * Create a new TwoPointCrossover instance with a two random points generated and set if the one offspring is generated
     * 
     * @param singleOffspring generate a single offspring or not
     */
    public TwoPointCrossover(boolean singleOffspring)
    {
        this.singleOffspring = singleOffspring;
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
            throw new CrossoverException("Cannot use Two Point Crossover for Permutation Chromosome. Only Permutation Crossover Operators are allowed");
        if(firstOffspring.length()!= secondOffspring.length())
            throw new CrossoverException("Cannot recombine chromosomes with different lengths");
        if(firstOffspring.length() < 3)
            throw new CrossoverException("Multi point crossover cannot work if chromozome length is lower than 3");
        
        Offspring offspring= new Offspring();
                       
        //generateCrossoverPoints
        int firstCrossoverPoint = RandomUtils.nextInt(firstOffspring.length());
        int secondBound = firstOffspring.length()- firstCrossoverPoint;
        int diff = RandomUtils.nextInt(secondBound+1);
        int secondCrossoverPoint = firstCrossoverPoint + diff;
        if(firstCrossoverPoint == secondCrossoverPoint)
           if(firstCrossoverPoint==0)
                secondCrossoverPoint++;
            else
                firstCrossoverPoint--;
        
        for(int i=firstCrossoverPoint; i<secondCrossoverPoint; i++)
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
    
    /*
    private void generateCrossoverPoints(int bound)
    {
        firstCrossoverPoint = RandomUtils.nextInt(bound);
        int secondBound = bound - firstCrossoverPoint;
        int diff = RandomUtils.nextInt(secondBound+1);
        secondCrossoverPoint = firstCrossoverPoint + diff;
        if(firstCrossoverPoint == secondCrossoverPoint)
           if(firstCrossoverPoint==0)
                secondCrossoverPoint++;
            else
                firstCrossoverPoint--;
    }
*/
        
}
