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
import java.util.stream.Stream;

/**
 *
 * @author Catalin Baba
 */
public class Offspring implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final ArrayList<Individual> offspring = new ArrayList();
       
    /**
     * Create a instance of an Offspring
     */
    public Offspring() {}

    
    /**
     * Add a new offspring 
     * 
     * @param individual individual to add
     */
    public void addOffspring(Individual individual)
    {
        this.offspring.add(individual);
    }
    
    /**
     *  Get a new offspring of the specified index
     * 
     * @param index offspring index
     * 
     * @return offspring
     */
    public Individual getOffspring(int index)
    {
        return offspring.get(index);
    }
    
    /**
     * Get the total number of offspring
     * 
     * @return number of offspring
     */
    public int getSize()
    {
        return offspring.size();
    }
    
     /**
     * Returns a sequential Stream with this Offspring as its source.
     * 
     * @return a sequential Stream over the individuals
     */
    public Stream<Individual> stream()
    {
        return offspring.stream();
    }
    
     /**
     * Returns a list of individuals
     * 
     * @return list of individuals
     */
    public ArrayList<Individual> getIndividuals()
    {
        return offspring;
    }
}
