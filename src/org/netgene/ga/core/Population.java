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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.netgene.utils.RandomUtils;

/**
 * Population class represents a list of individuals
 * 
 * @author Catalin Baba
 */
public final class Population implements Serializable
{
    private static final long serialVersionUID = 1L;

    private List<Individual> population;
    
    private long generation = 0;
    
    /**
     * Create a new Population
     */
    public Population()
    {
        population = Collections.synchronizedList(new ArrayList<Individual>()); 
    }
    
    /**
     * Create a new Population with the specified size
     * 
     * @param size size of the population
     */
    public Population(int size)
    {
        population = Stream.generate(()->
                        {
                            return new Individual();
                        })
                        .limit(size).collect(Collectors.toList());
    }
      
    /**
    * Get a list of individuals from this population
    * 
    * @return a list of individuals
    */
    public List<Individual> getPopulation() {
        return population;
    }
    
    /**
     * Get the population size
     * 
     * @return population size
     */
    public int size()
    {
        return this.population.size();
    }
    
    /**
     * Get the individual at the specified index
     * 
     * @param index on the individual
     * 
     * @return individual at the specified index
     */
    public Individual getIndividual(int index)
    {
        return this.population.get(index);
    }
    
     /**
     * Replace an individual to this population at the specified index
     * 
     * @param index individual index
     * 
     * @param individual individual to be replaced
     */
    public void replaceIndividual(int index, Individual individual)
    {
        this.population.set(index, individual);
    }
        
    /**
     * Add an individual to this population at the specified index
     * 
     * @param index individual index
     * 
     * @param individual individual to be added
     */
    public void addIndividual(int index, Individual individual)
    {
        this.population.add(index, individual);
    }
    
    /**
     * Add an individual from this population 
     * 
     * @param individual individual to be added
     */
    public void addIndividual(Individual individual)
    {
        this.population.add(individual);
    }
    
    /**
     * Remove an individual from this population 
     * 
     * @param individual individual to be added
     */
    public void removeIndividual(Individual individual)
    {
        this.population.remove(individual);
    }
    
    
    /**
     * Remove an individual from this population at the specified index
     * 
     * @param index individual index
     */
    public void removeIndividual(int index)
    {
        this.population.remove(index);
    }
    
    /**
     * Get the generation number
     * 
     * @return generation number
     */
    public long getGeneration()
    {
        return generation;
    }
    
    /**
     * set the generation number
     * 
     * @param generation number
     */
    public void setGeneration(long generation)
    {
        this.generation = generation;
    }
    
    /**
     * Get the sum of all individuals fitness from this population
     * 
     * @return the total fitness of the population
     */
    public double populationFitness()
    {
        double populationFitness = 0.0;
        for(int i=0; i<population.size(); i++)
             populationFitness = populationFitness + population.get(i).getFitnessScore();
        return populationFitness;
    }
    
    /**
     * Sort individuals from this population by fitness
     */
    public void sort()
    {
        Collections.sort(population);
        Collections.reverse(population);
    }
    
    /**
     * Get a random individual from population
     * 
     * @return a random individual from population
     */
    public Individual getRandomIndividual()
    {
        return population.get(RandomUtils.nextInt(population.size()));
    }
    
    /**
     * Get the best individual from population (individual with the best fitness)
     * 
     * @return the best individual
     */
    public Individual getBestIndividual()
    {
        Individual bestIndividual = population.get(0);
        for(int i = 1; i < size(); i++)
            if(bestIndividual.getFitnessScore() < population.get(i).getFitnessScore())
                bestIndividual = population.get(i);
        return bestIndividual;
    }
    
    /**
     * Returns a sequential Stream with this population as its source.
     * 
     * @return a sequential Stream over the individuals
     */
    public Stream<Individual> stream()
    {
        return population.stream();
    }
    
     /**
     * Returns a parallel Stream with this Population as its source.
     * 
     * @return a parallel Stream over the individuals in this population
     */
    public Stream<Individual> parallelStream()
    {
        return population.parallelStream();
    }
    
    /**
     * Clear all individuals from population
     */
    public void clear()
    {
        population.clear();
    }
       
    /** 
     * Get a copy of the population
     * 
     * @return copy of population
     */
    @Override
    public Object clone()
    {
        Population clone = null;
        try 
        {
            clone = (Population) super.clone();
        } 
        catch (CloneNotSupportedException e) 
        {
            clone = new Population();
        }
        clone.population = population.stream().collect(Collectors.toList());
        return clone;
    }
    
    /**
     * Get String representation of this population
     * 
     * @return String representation of this populations
     */
    @Override
    public String toString()
    {
        
        String toString = "Population size: " + size() + "\n";
                
        for(int i=0; i<population.size(); i++)
            toString = toString + "Individual : " + i + "\n" + population.get(i).toString() + "\n";  
        return toString;
    }
   
}
