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
import org.netgene.utils.RandomUtils;

/**
 * Rank Selection also works with negative fitness values and is mostly used when the individuals in the population have very close fitness values (this happens usually at the end of the run). 
 * 
 * Rank selection first ranks the population and then every chromosome receives fitness from this ranking. 
 * The worst will have fitness 1, second worst 2 etc. and the best will have fitness N (number of chromosomes in population).
 * After this all the chromosomes have a chance to be selected. But this method can lead to slower convergence, because the best chromosomes do not differ so much from other ones.
 * 
 * @author Catalin Baba
 */
public class RankSelector extends ParentSelector implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new RankSelector instance
     */
    public RankSelector() {}
               
    /**
     * Select individual from population
     * 
     * @param population the population from which the individual will be selected
     * 
     * @return selected individual
     */
    @Override
    protected Individual select(Population population)
    {
        int spinWheel = 0;
        int populationRank = population.size() * (population.size() + 1) / 2;
        int rouletteWheelPosition =  RandomUtils.nextInt(populationRank);
        
        for(int i=population.size(); i>0; i--)
        {
            spinWheel = spinWheel + i;
            if(spinWheel > rouletteWheelPosition)
            {
                return population.getIndividual(population.size()-i);
            }
        }
        return null; //unreachable code to make the compiler happy
    }
}
