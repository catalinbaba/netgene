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
import org.netgene.ga.gene.BitGene;

/**
 *
 * @author Catalin Baba
 */
public class BitChromosome extends Chromosome<BitGene> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * Create a new BitChromosome instance
    */    
    public BitChromosome()
    {
        chromosome = new ArrayList<>();
    }
    
    /**
    * Create a new BitChromosome instance with random genes values
    * 
     * @param size - size of the chromosome
    */    
    public BitChromosome(final int size)
    {
        chromosome = new ArrayList<>();
        for(int i=0; i<size; i++)
            chromosome.add(new BitGene());
    }
    
    /**
     * Create a new BitChromosome from the specified chromosome
     * 
     * @param chromosome chromosome to set
     */
    public BitChromosome(ArrayList<BitGene> chromosome)
    {
        this.chromosome = chromosome;
    }
    
    /**
     * Initializes a new instance of the BitChromosome class with the specified allele values for BitGenes
     * 
     * 
     * @param booleaneArray Boolean array with the genes allele values
     */
    public BitChromosome(Boolean ... booleaneArray)
    {
        chromosome = new ArrayList();
        for(int i=0; i<booleaneArray.length; i++)
             chromosome.add(new BitGene(booleaneArray[i]));
    }
    
    /**
     * Set BitChromosome 
     * 
     * @param chromosome to set
     */
    @Override
    public void setChromosome(ArrayList<BitGene> chromosome)
    {
        this.chromosome = chromosome;
    }
    
    /**
     * Get the chromosome
     * 
     * @return a list of BitGene
     */
    @Override
    public ArrayList<BitGene> getChromosome()
    {
        return this.chromosome;
    }
    
    /**
     * Get a gene from the chromosome
     * 
     * @param index gene index
     * 
     * @return BitGene at the specified index
     */
    @Override
    public BitGene getGene(int index)
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
    public void setGene(int index, BitGene gene) 
    {
        chromosome.set(index, (BitGene)gene);
    }
    
     /**
     * Appends the specified gene to the end of this chromosome.
     * 
     * @param gene to be added
     * 
     */
    @Override
    public void addGene(BitGene gene) 
    {
        chromosome.add((BitGene)gene);
    }
    
    /**
     * Inserts the specified gene at the specified position in this chromosome.
     * 
     * @param index index at which the specified gene is to be inserted
     * 
     * @param gene gene to be inserted
     */
    @Override
    public void addGene(int index,BitGene gene) 
    {
        chromosome.add(index, gene);
    }
    
    /**
     * Create a new instance of a BitChromosome
     * 
     * @return BitChromosome
     */
    public BitChromosome newInstance()
    {
        return new BitChromosome();
    }
    
    /**
     * Return a boolean array for this BitChromosome
     * 
     * @return a boolean array
     */
    public boolean[] toArray()
    {       
        boolean booleanArray[] = new boolean[chromosome.size()];   
        for(int i=0; i<chromosome.size(); i++)
            booleanArray[i] = chromosome.get(i).getAllele();
        return booleanArray;
    }
    
    @Override
    public boolean contains(BitGene gene)
    {
        for(int i=0; i<chromosome.size(); i++)
            if(chromosome.get(i).getAllele().booleanValue() == gene.getAllele().booleanValue())
            {
                return true;
            }
        return false;
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
    public BitChromosome copy() 
    {
        BitChromosome copy  = new BitChromosome();
        copy .chromosome = (ArrayList) chromosome.clone();
        return copy;
    }
}
