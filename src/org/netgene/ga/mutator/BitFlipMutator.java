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
import org.netgene.ga.chromosome.BitChromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.BitGene;
import org.netgene.utils.RandomUtils;

/**
 * In this bit flip mutation, we select one or more random bits and flip them. This is used for binary encoded GAs.
 * 
 * @author Catalin Baba
 */
public class BitFlipMutator extends MutatorOperator implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new instance of BitFlipMutator
     */
    public BitFlipMutator() {}
    
       
    private void mutate(BitChromosome chromosome)
    {
        for(int i=0 ;i<chromosome.length(); i++)
        {
            double rand = RandomUtils.nextDouble();
            if(rand < mutationRate) //is selected to be mutated
            {
                BitGene gene = new BitGene(!chromosome.getGene(i).getAllele());
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
    public void mutate(Individual individual) throws MutatorException 
    {
        if(!(individual.getChromosome() instanceof BitChromosome))
            throw new MutatorException("Bit Flip Mutator is applicable only for BitChromosome");
        mutate((BitChromosome)individual.getChromosome());
    }
    
   
}
