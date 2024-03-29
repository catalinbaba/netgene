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
import org.netgene.ga.exception.GaException;
import org.netgene.ga.gene.BitGene;
import org.netgene.ga.gene.DoubleGene;

/**
 *
 * @author Catalin Baba
 */
public class DoubleChromosome extends NumericChromosome<DoubleGene> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create an instance of the DoubleChromosome 
     */    
    public DoubleChromosome()
    {
        chromosome = new ArrayList<>();
    }
    
    /**
     * Create a new DoubleChromosome from the specified chromosome
     * 
     * @param chromosome chromosome to set
     */
    public DoubleChromosome(ArrayList<DoubleGene> chromosome)
    {
        this.chromosome = chromosome;
    }
    
    /**
     * Create a new DoubleChromosome with random genes values
     * 
     * @param size - size of the chromosome
     */
    public DoubleChromosome(final int size)
    {
        chromosome = new ArrayList<>();
        for(int i=0; i<size; i++)
            chromosome.add(new DoubleGene());
    }
    
    /**
     * Create a new DoubleChromosome with random genes values 
     * between an specified value (inclusive) and another specified value (exclusive)
     * 
     * @param size - size of the chromosome
     * 
     * @param minRange inclusive value
     * 
     * @param maxRange exclusive value
     */
    public DoubleChromosome(final int size, final double minRange, final double maxRange)
    {
        chromosome = new ArrayList<>();
        for(int i=0; i<size; i++)
            chromosome.add(new DoubleGene(minRange, maxRange));
    }
    
     /**
     * Initializes a new instance of the DoubleChromosome class with the specified allele values for DoubleGenes
     * 
     * 
     * @param doubleArray Double array with the genes allele values
     */
    public DoubleChromosome(Double ... doubleArray)
    {
        chromosome = new ArrayList();
        for(int i=0; i<doubleArray.length; i++)
             chromosome.add(new DoubleGene(doubleArray[i]));
    }
    
    /**
     * Set DoubleChromosome 
     * 
     * @param chromosome to set
     */
    @Override
    public void setChromosome(ArrayList<DoubleGene> chromosome)
    {
        this.chromosome = chromosome;
    }
    
    /**
     * Get the chromosome
     * 
     * @return a list of BitGene
     */
    @Override
    public ArrayList<DoubleGene> getChromosome()
    {
        return this.chromosome;
    }
    
    /**
     * Get a gene from the chromosome
     * 
     * @param index gene index
     * 
     * @return DoubleGene at the specified index
     */
    @Override
    public DoubleGene getGene(int index)
    {
        return (DoubleGene)chromosome.get(index);
    }
    
    /**
     * Replaces the gene at the specified position in this chromosome with the specified gene.
     * 
     * @param index index of the gene to replace
     * 
     * @param gene gene to be stored at the specified position
     */
    @Override
    public void setGene(int index, DoubleGene gene) throws GaException
    {
        chromosome.set(index, gene);
    }
    
    /**
     * Appends the specified gene to the end of this chromosome.
     * 
     * @param gene to be added
     * 
     */
    @Override
    public void addGene(DoubleGene gene) throws GaException
    {
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
    public void addGene(int index, DoubleGene gene) 
    {
        chromosome.add(index, gene);
    }
     
     /**
     * Return a double array for this BitChromosome
     * 
     * @return a double array
     */
    public double[] toArray()
    {       
        double doubleArray[] = new double[chromosome.size()];  
        DoubleGene gene;
        for(int i=0; i<chromosome.size(); i++)
            doubleArray[i] = ((DoubleGene)chromosome.get(i)).getAllele();
        return doubleArray;
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
            toString = toString + "Gene " + i + " = " + chromosome.get(i).toString() + "\n";
        return toString;
    }
    
    /**
     * Return a copy of this BitChromosome
     * 
     * @return the a copy of this BitChromosome
     */
    @Override
    public DoubleChromosome copy() 
    {
        DoubleChromosome copy = null;
        copy  = new DoubleChromosome();
        copy .chromosome = (ArrayList) chromosome.clone();
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
    public DoubleChromosome average(Chromosome<DoubleGene> thatChromosome) 
    {
        DoubleChromosome averageChromosome = new DoubleChromosome();
        for(int i=0; i<chromosome.size(); i++)
            averageChromosome.addGene(chromosome.get(i).average(thatChromosome.getGene(i)));
        return averageChromosome;
    }
    
    @Override
    public boolean contains(DoubleGene gene)
    {
        for(int i=0; i<chromosome.size(); i++)
            if(chromosome.get(i).getAllele().doubleValue() == gene.getAllele().doubleValue())
            {
                return true;
            }
        return false;
    }
}
