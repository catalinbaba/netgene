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
import java.util.ArrayList;
import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.Gene;
import org.netgene.utils.RandomUtils;

/**
 * In Inversion Mutation we select a subset of our genes and reverse their order.
 * 
 * @author Catalin Baba
 */
public class InversionMutator extends MutatorOperator implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new instance of InversionMutator
     */
    public InversionMutator() {}

    private void mutate(Chromosome chromosome)  
    {
        int firstIndex = RandomUtils.nextInt(chromosome.length()-1);
        int secondBound = chromosome.length()- firstIndex;
        int diff = RandomUtils.nextInt(secondBound);
        int secondIndex = firstIndex + diff;
        if(firstIndex !=0 && firstIndex == secondIndex)
            firstIndex--;
        double rand = RandomUtils.nextDouble();            
        if(rand < mutationRate) //is selected to be mutated
        {
            ArrayList<Gene> genes = new ArrayList();
            for(int i=secondIndex; i>=firstIndex; i--)
            {
                genes.add(chromosome.getGene(i));
            }
            for(int i=firstIndex; i<=secondIndex; i++)
            {
                chromosome.setGene(i, genes.get(i-firstIndex));
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
        mutate(individual.getChromosome());
    }
}
