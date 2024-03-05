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

package org.netgene.ga.core;

import java.io.Serializable;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.gene.Gene;

/**
 *
 * @author Catalin Baba
 * 
 * @param <G> the type of gene in this individual
 */
public class Individual<G extends Gene<?>>  implements  Comparable, Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Chromosome<G> chromosome;
    
    private transient Object customData;

    private transient double fitnessValue = 0.0;
    
    private boolean elite = false;
    
    public void setElite(boolean elite)
    {
        this.elite = elite;
    }
    
    public boolean isElite()
    {
        return this.elite;
    }
    
    /**
     * create a new instance of Individual
     */
    public Individual() {}

    /**
     * create a new instance of individual with the specified chromosome
     * 
     * @param chromosome Individual chromosome
     */
    public Individual(Chromosome<G> chromosome)
    {
        this.chromosome = chromosome;
    }
    
    /**
     * Set individual fitness value
     * 
     * @param fitnessValue fitness value to be set
     */
    public void setFitnessScore(double fitnessValue)
    {
        this.fitnessValue = fitnessValue;
    }
    
     /**
     * Get individual's fitness value
     * 
     * @return fitness value
     */
    public double getFitnessScore()
    {
        return this.fitnessValue;
    }
    
     /**
     * Get individual's chromosome
     * 
     * @return individual chromosome
     */
    public Chromosome<G> getChromosome() {
        return chromosome;
    }

     /**
     * Set individual's chromosome
     * 
     * @param chromosome individual's chromosome
     */
    public void setChromosome(Chromosome<G> chromosome) {
        this.chromosome = chromosome;
    }
    
    /**
     * Store some custom data that can be used during the evolution process
     * 
     * @param customData to be stored
     * 
     */
    public void setCustomData(Object customData)
    {
        this.customData = customData;
    }
    
     /**
     * Get stored custom data that can be used during the evolution process
     * 
     * @return customData to be stored
     * 
     */
    public Object getCustomData()
    {
        return this.customData;
    }

    /**
     * Compare two individuals by fitness value
     * 
     * @param anotherIndividual individual to compare with
     * 
     * @return a negative integer, zero, or a positive integer as this individual fitness is less than, equal to, or greater than the specified individual.
     */
    @Override
    public int compareTo(Object anotherIndividual) 
    {
        if(this.getFitnessScore()>((Individual)anotherIndividual).getFitnessScore())
          return 1;
        else if(this.getFitnessScore()<((Individual)anotherIndividual).getFitnessScore())
          return -1;
        return 0;
    }
    
    /**
     * String representation of the individual
     * 
     * @return String representation of the individual
     */
    @Override
    public String toString()
    {
        String toString = "Fitness: " + fitnessValue + "\n";
        toString = toString + chromosome.toString();
        return toString;
    }
}
