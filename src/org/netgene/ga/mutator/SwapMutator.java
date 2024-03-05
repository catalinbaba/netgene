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

package org.netgene.ga.mutator;

import java.io.Serializable;
import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.Gene;
import org.netgene.utils.RandomUtils;

/**
 * In swap mutation, we select two positions on the chromosome at random, and interchange the values. 
 * 
 * @author Catalin Baba
 */
public class SwapMutator extends MutatorOperator implements Serializable
{
    private static final long serialVersionUID = 1L;
   
     /**
     * Create a new SwapMutator instance
     */
    public SwapMutator() {}

    
    private void mutate(Chromosome chromosome) 
    {
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
        if(rand < mutationRate) //is selected to be mutated
        {
            //System.out.println("mutate");
            Gene firstGene = chromosome.getGene(firstIndex);
                
            chromosome.setGene(firstIndex, chromosome.getGene(secondIndex));
            chromosome.setGene(secondIndex, firstGene);
        }
    }
    
//    private void mutate2(Chromosome chromosome) 
//    {
//        for(int i=0 ;i<chromosome.length()/2; i++)
//        {
//            double rand = RandomUtils.nextDouble();
//           
//            if(rand < mutationRate) //is selected to be mutated
//            {
//                
//                Gene firstGene = chromosome.getGene(i);
//                
//                chromosome.setGene(i, chromosome.getGene(chromosome.length()/2+i));
//                chromosome.setGene(chromosome.length()/2+i, firstGene);
//            }
//        }
//    }
    
     @Override
    public void mutate(Individual individual) throws MutatorException {
        mutate(individual.getChromosome());
    }
}
