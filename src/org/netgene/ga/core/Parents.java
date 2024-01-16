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

/**
 * Parents class holds two individuals
 * 
 * @author Catalin Baba
 */
public class Parents implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final Individual firstParent;
    private final Individual secondParent;
   
    /**
     * Create a new instance of a Couple
     * 
     * @param firstIndividual first individual
     * 
     * @param secondIndividual second individual
     * 
     */
    public Parents(Individual firstIndividual, Individual secondIndividual)
    {
        this.firstParent = firstIndividual;
        this.secondParent = secondIndividual;
    }
    
    /**
     * Get the first individual (parent)
     * 
     * @return first parent
     */
    public Individual getFirstParent() 
    {
        return firstParent;
    }

    /**
     * Get the second individual (parent)
     * 
     * @return second parent
     */
    public Individual getSecondParent() 
    {
        return secondParent;
    }
   
    /**
     * Get both parents as an ArrayList
     * 
     * @return ArrayList with parents
     */
    public ArrayList<Individual> getParents()
    {
        ArrayList<Individual> array = new ArrayList();
        array.add(firstParent);
        array.add(secondParent);
        return array;
    }
    
}
