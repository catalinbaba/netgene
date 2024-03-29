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
import org.netgene.ga.core.Offspring;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.chromosome.PermutationChromosome;
import org.netgene.ga.exception.CrossoverException;
import org.netgene.ga.exception.GaException;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author Catalin Baba
 * 
 * @param <C> Chromosome type
 * 
 */
public class Order1Crossover<C extends Chromosome<?>> extends CrossoverOperator<C> implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    //public int firstCrossoverPoint = 0;
    //public int secondCrossoverPoint = 0;
    
     /**
     * Create a new OnePointCrossover instance with a random point generated.
     * 
     */
    public Order1Crossover()
    {
        this.singleOffspring = false;
    }
    
    /**
     * Create a new OnePointCrossover instance with a random point generated and set if the one offspring is generated
     * 
     * @param singleOffspring generate a single offspring or not
     */
    public Order1Crossover(boolean singleOffspring)
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
    public Offspring recombine(Individual x, Individual y) throws GaException
    {
        if(!(x.getChromosome() instanceof PermutationChromosome) || !(y.getChromosome() instanceof PermutationChromosome))
            throw new CrossoverException("Order 1 Crossover can be used only for Permuatation Chromosome");
        
        if(x.getChromosome().length()!= y.getChromosome().length())
             throw new CrossoverException("Cannot recombine chromosomes with different lengths");
          
        Offspring offspring= new Offspring();
                         
        if(!singleOffspring)
        {
            offspring.addOffspring(new Individual(getOffspring(x,y)));
            offspring.addOffspring(new Individual(getOffspring(y,x)));
        }
        else
        {
            offspring.addOffspring(new Individual(getOffspring(x,y)));
        }
        return offspring;
        
    }
    
    /* 
    private PermutationChromosome getOffspring(Individual x, Individual y) throws GaException
    {
        PermutationChromosome firstParent = (PermutationChromosome)x.getChromosome().copy();
        PermutationChromosome secondParent = (PermutationChromosome)y.getChromosome().copy();
        
        PermutationChromosome offspring = new PermutationChromosome();
        
        for(int i=firstCrossoverPoint; i<secondCrossoverPoint; i++)
           secondParent.removeGene(firstParent.getGene(i).getAllele());
        for(int i=firstCrossoverPoint; i<secondCrossoverPoint; i++)
            secondParent.addGene(i, firstParent.getGene(i));
        
        return secondParent;
    }
    */
    
       
    private PermutationChromosome getOffspring(Individual x, Individual y) throws GaException
    {
        int firstCrossoverPoint = RandomUtils.nextInt(x.getChromosome().length()-1);
        int secondCrossoverPoint = RandomUtils.nextInt(firstCrossoverPoint+1, x.getChromosome().length());
        
        PermutationChromosome firstParent = (PermutationChromosome)x.getChromosome();
        PermutationChromosome secondParent = (PermutationChromosome)y.getChromosome();
        PermutationChromosome offspring = new PermutationChromosome();
        
        for(int i=firstCrossoverPoint; i<secondCrossoverPoint; i++)
            offspring.addGene(firstParent.getGene(i));
        
        //start from the righ
        if(secondCrossoverPoint !=  firstParent.length())  // if we don't have, start 
        {
            for(int i = secondCrossoverPoint; i<secondParent.length(); i++)
                if(!offspring.contains(secondParent.getGene(i)))
                    offspring.addGene(secondParent.getGene(i));
        }
        
        
        //we start from left
        int j = 0;
        for(int i = 0; i<secondCrossoverPoint; i++)
        {
            if(!offspring.contains(secondParent.getGene(i)))
            {
                //decide where to add
                if(offspring.length() < secondParent.length() - firstCrossoverPoint)
                {
                    offspring.addGene(secondParent.getGene(i));
                }
                else
                {
                    offspring.addGene(j, secondParent.getGene(i));
                    j++;
                }
            }
        }
        return offspring;
        
    }
    
    
    private int[] generateCrossoverPoints(int bound)
    {
        int firstCrossoverPoint = RandomUtils.nextInt(bound);
        int secondBound = bound - firstCrossoverPoint;
        int diff = RandomUtils.nextInt(secondBound+1);
        int secondCrossoverPoint = firstCrossoverPoint + diff;
        if(firstCrossoverPoint == secondCrossoverPoint)
           if(firstCrossoverPoint==0)
                secondCrossoverPoint++;
            else
                firstCrossoverPoint--;
        int crossPoints[] = {firstCrossoverPoint, secondCrossoverPoint};
        
        return crossPoints;
    }
    
}
