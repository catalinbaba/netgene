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
import org.netgene.ga.chromosome.PermutationChromosome;
import org.netgene.ga.core.Offspring;
import org.netgene.ga.exception.CrossoverException;
import org.netgene.utils.RandomUtils;

/**
 * In a uniform crossover, we don’t divide the chromosome into segments, rather we treat each gene separately. 
 * In this, we essentially flip a coin for each chromosome to decide whether or not it’ll be included in the off-spring.
 * 
 * @author Catalin Baba
 * 
 * @param <C> chromosome type
 */
public class UniformCrossover<C extends Chromosome<?>> extends CrossoverOperator<C> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private double probability = 0.5;
     
    /**
     * Create a new UniformCrossover instance
     * 
     */
    public UniformCrossover() {}
    
    /**
     * Create a new UniformCrossover instance and set if returns a single offspring or not
     * 
     * @param singleOffspring set single offspring
     */
    public UniformCrossover(boolean singleOffspring)
    {
        this.singleOffspring = singleOffspring;
    }
       
    /**
     * Create a new UniformCrossover instance and set probability
     * 
     * @param probability probability to swap
     * 
     * @throws CrossoverException if probability does not have a value between 0.1 and 1.0
     */
    public UniformCrossover(double probability) throws CrossoverException
    {
        if(probability < 0 || probability > 1.0)
            throw new CrossoverException("Probability must take a value between 0.0 and 1.0");
        this.probability = probability;
    }
    
    /**
     * Create a new UniformCrossover instance with specified probability and set single offspring mode
     * 
     * @param probability probability to swap
     * 
     * @param singleOffspring set single offspring
     * 
     * @throws CrossoverException if probability does not have a value between 0.1 and 1.0
     */
    public UniformCrossover(double probability, boolean singleOffspring) throws CrossoverException
    {
        this.singleOffspring = singleOffspring;
        if(probability < 0.1 && probability > 1.0)
            throw new CrossoverException("Probability must take a value between 0.1 and 1.0");
        this.probability = probability;
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
            throw new CrossoverException("Cannot use Uniform Crossover for Permutation Chromosome. Only Permutation Crossover Operators are allowed");
        if(firstOffspring.length()!= secondOffspring.length())
            throw new CrossoverException("Cannot recombine chromosomes with different lengths.");
                
        Offspring offspring= new Offspring();
        
        for(int i=0; i<firstOffspring.length(); i++)
            if(RandomUtils.nextDouble() < probability)
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
    
    /**
     * Get crossover probability
     * 
     * @return probability
     */
    public double getProbability()
    {
        return this.probability;
    }
    
}
