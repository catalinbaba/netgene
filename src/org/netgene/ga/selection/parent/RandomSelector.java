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
import org.netgene.ga.exception.SelectionException;
import org.netgene.utils.RandomUtils;

/**
 * In this strategy we randomly select parents from the existing population. 
 * There is no selection pressure towards fitter individuals and therefore this strategy is usually avoided.
 * 
 * @author Catalin Baba
 */
public class RandomSelector extends ParentSelector implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new RandomSelector instance
     */
    public RandomSelector() {}
       
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
        int individualNr = RandomUtils.nextInt(population.size());
        return population.getIndividual(individualNr);
    }
}
