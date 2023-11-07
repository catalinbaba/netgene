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

import java.io.Serializable;
import java.util.ArrayList;
import org.netgene.ga.gene.IntegerGene;

/**
 *
 * @author Catalin Baba
 */
public class IntegerChromosome extends NumericChromosome<IntegerGene> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create an instance of the IntegerChromosome 
     */     
    public IntegerChromosome()
    {
        chromosome = new ArrayList();
    }
    
     /**
     * Create a new IntegerChromosome from the specified chromosome
     * 
     * @param chromosome chromosome to set
     */
    public IntegerChromosome(ArrayList<IntegerGene> chromosome)
    {
        this.chromosome = chromosome;
    }
    
    /**
     * Create a new IntegerChromosome with random genes values
     * 
     * @param size - size of the chromosome
     */
    public IntegerChromosome(final int size)
    {
        chromosome = new ArrayList<>();
        for(int i=0; i<size; i++)
            chromosome.add(new IntegerGene());
    }
    
    /**
     * Create a new IntegerChromosome with random genes values 
     * between an specified value (inclusive) and another specified value (exclusive)
     * 
     * @param size - size of the chromosome
     * 
     * @param minRange inclusive value
     * 
     * @param maxRange exclusive value
     */
    public IntegerChromosome(final int size, final int minRange, final int maxRange)
    {
        chromosome = new ArrayList<>();
        for(int i=0; i<size; i++)
            chromosome.add(new IntegerGene(minRange, maxRange));
    }
    
    /**
     * Initializes a new instance of the IntegerChromosome class with the specified allele values for IntegerGenes
     * 
     * 
     * @param integerArray Integer array with the genes allele values
     */
    public IntegerChromosome(int [] integerArray)
    {
        chromosome = new ArrayList();
        for(int i=0; i<integerArray.length; i++)
             chromosome.add(new IntegerGene(integerArray[i]));
    }
    
     /**
     * Set IntegerChromosome 
     * 
     * @param chromosome to set
     */
    @Override
    public void setChromosome(ArrayList<IntegerGene> chromosome)
    {
        this.chromosome = chromosome;
    }
    
    /**
     * Get the chromosome
     * 
     * @return a list of BitGene
     */
    @Override
    public ArrayList<IntegerGene> getChromosome()
    {
        return this.chromosome;
    }
    
     /**
     * Get a gene from the chromosome
     * 
     * @param index gene index
     * 
     * @return IntegerGene at the specified index
     */
    @Override
    public IntegerGene getGene(int index)
    {
        return chromosome.get(index);
    }
    
    /**
     * Replaces the gene at the specified position in this chromosome with the specified gene.
     * 
     * @param index index of the gene to replace
     * 
     * @param gene gene to be stored at the specified position
     */
    @Override
    public void setGene(int index, IntegerGene gene) 
    {
        chromosome.set(index, (IntegerGene)gene);
    }
    
    /**
     * Appends the specified gene to the end of this chromosome.
     * 
     * @param gene to be added
     * 
     */
    @Override
    public void addGene(IntegerGene gene) 
    {
        chromosome.add((IntegerGene)gene);
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
        chromosome.add(index, gene);
    }
    
    /**
     * Return a int array for this BitChromosome
     * 
     * @return a int array
     */    
    public int[] toArray()
    {       
        int intArray[] = new int[chromosome.size()];   
        for(int i=0; i<chromosome.size(); i++)
            intArray[i] = chromosome.get(i).getAllele();
        return intArray;
    }
    
    /**
     * Return the chromosome length
     * 
     * @return chromosome length
     */
    @Override
    public int length()
    {
        return chromosome.size();
    }
    
    /**
     * Return the String representation of the chromosome
     * 
     * @return String representation of the chromosome
     */
    @Override
    public String toString()
    {
        String toString = "Chromosome: " + "\n";
        for(int i=0; i<chromosome.size(); i++)
        {
            toString = toString + "Gene " + i + " = " + chromosome.get(i).toString() + "\n";
        }
        return toString;
    }
    
    /**
     * Return a copy of this BitChromosome
     * 
     * @return the a copy of this BitChromosome
     */
    @Override
    public IntegerChromosome copy() 
    {
        IntegerChromosome copy = null;
        copy  = new IntegerChromosome();
        copy.chromosome = (ArrayList) chromosome.clone();
        return copy;
    }
    
    /**
     * Get the average value of two genes
     * 
     * @param thatChromosome chromosome
     * 
     * @return average chromosome 
     */
    @Override
    public IntegerChromosome average(Chromosome<IntegerGene> thatChromosome) {
        IntegerChromosome averageChromosome = new IntegerChromosome();
        for(int i=0; i<chromosome.size(); i++)
           averageChromosome.addGene(chromosome.get(i).average(thatChromosome.getGene(i)));
        return averageChromosome;
    }
    
    @Override
    public boolean contains(IntegerGene gene)
    {
        for(int i=0; i<chromosome.size(); i++)
            if(chromosome.get(i).getAllele().intValue() == gene.getAllele().intValue())
            {
                return true;
            }
        return false;
    }

}
