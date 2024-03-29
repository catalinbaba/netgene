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

package org.netgene.ga.selection.parent;

import java.io.Serializable;
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Population;
import org.netgene.ga.core.Parents;
import org.netgene.ga.exception.SelectionException;

/**
 *
 * @author Catalin Baba
 */
public abstract class ParentSelector implements Serializable
{
    private static final long serialVersionUID = 1L;
 
    protected boolean incestPrevention = true;
    
    /**
     * Set incest prevention - prevent to select as parents the same individuals
     * 
     * @param incestPrevention true if incest prevention is set
     */
    public void setIncestPrevention(boolean incestPrevention)
    {
        this.incestPrevention = incestPrevention;
    }
    
     /**
     * Get incest prevention
     * 
     * @return incestPrevention incest prevention
     */
    public boolean getIncestPrevention()
    {
        return this.incestPrevention;
    }
    
    /**
     * Select individual from population
     * 
     * @param population the population from which the individual will be selected
     * 
     * @return selected individual
     */
    public Individual selectIndividual(Population population) throws SelectionException
    {
        Population selectFrom = (Population)population.clone();
        return select(selectFrom);
    }
    
     /**
     * Select parent from population
     * 
     * @param population the population from which the parents will be selected
     * 
     * @return selected parents
     */
    public Parents selectParents(Population population) throws SelectionException
    {
        Individual firstIndividual = select(population);
        Individual secondIndividual;
        if(incestPrevention)
        {
            Population selectFrom = (Population)population.clone();
            selectFrom.removeIndividual(firstIndividual);
            secondIndividual = select(selectFrom);
        }
        else
        {
            secondIndividual = select(population);
        }
        
        return new Parents(firstIndividual, secondIndividual);
    }
     
    protected abstract Individual select(Population population) throws SelectionException;
 
 
}
