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
import java.util.stream.Stream;
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Population;
import org.netgene.ga.exception.SelectionException;

/**
 * In K-Way tournament selection, we select K individuals from the population at random and select the best out of these to become a parent. 
 * The same process is repeated for selecting the next parent. 
 * Tournament Selection is also extremely popular in literature as it can even work with negative fitness values.
 * 
 * @author Catalin Baba
 */
public class TournamentSelector extends ParentSelector implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int tournamentSize = 1;
    
    /**
     * Create a new TournamentSelector instance and specify the tournament size
     * 
     * @param tournamentSize tournament size
     */    
    public TournamentSelector(int tournamentSize) throws SelectionException
    {
        if(tournamentSize == 0)
            throw new SelectionException("Tournament size cannot be lower than 1");
        this.tournamentSize = tournamentSize;
    }
    
               
    /**
     * Select individual from population
     * 
     * @param population the population from which the individual will be selected
     * 
     * @return selected individual
     */
    @Override
    protected Individual select(Population population) throws SelectionException
    {
        if(population.size() == 0)
            throw new SelectionException("Population size is 0! Cannot select parents.");
        if(population.size() <= tournamentSize)
            throw new SelectionException("Tournament size cannot be equal or higher than the population size!");
        Population selectFrom = (Population)population.clone();
        Individual selected = Stream.generate(() -> {
                                    Individual ind =selectFrom.getRandomIndividual();
                                    selectFrom.removeIndividual(ind);  //ensure to not select the same individual
                                    return ind;
                                })
                                .limit(tournamentSize)
                                .max(Individual::compareTo)
                                .get();
        return selected;
    }
    
   
}
