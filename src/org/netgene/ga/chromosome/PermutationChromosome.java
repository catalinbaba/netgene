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

package org.netgene.ga.chromosome;

import java.util.ArrayList;
import java.util.Collections;
import org.netgene.ga.exception.GaException;
import org.netgene.ga.gene.IntegerGene;

/**
 * Order 1 Crossover is a fairly simple permutation crossover. 
 * Basically, a swath of consecutive alleles from parent 1 drops down, 
 * and remaining values are placed in the child in the order which they appear in parent 2.
 * 
 * @author Catalin Baba
 */
public class PermutationChromosome extends IntegerChromosome
{
    /**
     * Initializes a new instance of the PermutationChromosome class with the specified chromosome length.IntegerGenes are created and added to the chromosome.
     * The gene will have allele values between 0 and specified value (exclusive)
     * The IntegerGenes are randomly distributed in the chromosome.
     * The most important property is that each value is never duplicated: each value is contained just once
     * 
     * @param size chromosome length
     */     
    public PermutationChromosome(int size)
    {
        chromosome = new ArrayList();
        for(int i=0; i<size; i++)
            chromosome.add(new IntegerGene(i));
        Collections.shuffle(chromosome);
    }
    
    /**
     * Initializes a new instance of the PermutationChromosome class with the specified chromosome length.IntegerGenes are created and added to the chromosome.
     * The gene will have allele values between start and specified value (exclusive)
     * The IntegerGenes are randomly distributed in the chromosome.
     * The most important property is that each value is never duplicated: each value is contained just once
     * 
     * @param size chromosome length
     * 
     * @param start value to start
     */     
    public PermutationChromosome(int size, int start)
    {
        chromosome = new ArrayList();
        for(int i=0; i<size; i++)
            chromosome.add(new IntegerGene(i+start));
        Collections.shuffle(chromosome);
    }
    
    /**
     * Initializes a new instance of the PermutationChromosome class with the specified allele values for IntegerGenes
     * 
     * 
     * @param integerArray Integer array with the genes allele values
     */
    public PermutationChromosome(Integer ... integerArray)
    {
        chromosome = new ArrayList();
        for (Integer integer : integerArray) {
            IntegerGene gene = new IntegerGene(integer);
            if(contains(gene))
                throw new GaException("Gene with the same allele value was already added to the chromosome. Values must not be repeated in a single chromosome.");
            chromosome.add(gene);
        }
    }
    
    /**
     * Create an instance of the PermutationChromosome 
     */     
    public PermutationChromosome()
    {
        chromosome = new ArrayList();
    }
    
     /**
     * Create a new PermutationChromosome from the specified chromosome
     * 
     * @param chromosome chromosome to set
     */
    public PermutationChromosome(ArrayList<IntegerGene> chromosome)
    {
        this.chromosome = chromosome;
    }
    
    /**
     * Replaces the gene at the specified position in this chromosome with the specified gene.
     * 
     * @param index index of the gene to replace
     * 
     * @param gene gene to be stored at the specified position
     */
    @Override
    public void setGene(int index, IntegerGene gene) throws GaException
    {
        if(contains(gene))
            throw new GaException("Gene with the same allele value was already added to the chromosome. Values must not be repeated in a single chromosome.");
        chromosome.set(index, gene);
    }
    
     /**
     * Appends the specified gene to the end of this chromosome.
     * 
     * @param gene to be added
     * 
     */
    @Override
    public void addGene(IntegerGene gene) throws GaException
    {
        if(contains(gene))
            throw new GaException("Gene with the same allele value was already added to the chromosome. Values must not be repeated in a single chromosome.");
        chromosome.add(gene);
    }
    
    /**
     * Inserts the specified gene at the specified position in this chromosome.
     * 
     * @param index index at which the specified gene is to be inserted
     * 
     * @param gene gene to be inserted
     */
    @Override
    public void addGene(int index, IntegerGene gene) 
    {
        if(contains(gene))
            throw new GaException("Gene with the same allele value was already added to the chromosome. Values must not be repeated in a single chromosome.");
        chromosome.add(index, gene);
    }
    
    /**
     * Remove a gene that contains the specified allele value
     * 
     * @param allele specified allele value
     */  
    public void removeGene(Integer allele) 
    {
        for(int i=0; i<chromosome.size(); i++)
        {
            IntegerGene gene = chromosome.get(i);
            if(gene.getAllele().intValue() == allele.intValue())
            {
                chromosome.remove(gene);
                return;
            }
        }
     }
    
    public boolean contains(IntegerGene toAdd)
    {
        return chromosome.stream().anyMatch((gene) -> {
            return gene.getAllele().intValue() == toAdd.getAllele().intValue();
        });
    }
   
    
     @Override
    public PermutationChromosome copy() 
    {
        PermutationChromosome copy = null;
        copy  = new PermutationChromosome();
        copy .chromosome = (ArrayList) chromosome.clone();
        return copy;
    }
    
}
