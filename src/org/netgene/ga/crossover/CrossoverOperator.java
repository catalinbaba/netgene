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

package org.netgene.ga.crossover;

import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.core.Offspring;
import org.netgene.ga.exception.CrossoverException;
import org.netgene.ga.gene.Gene;

/**
 *
 * @author cbaba
 * 
 * @param <C> chromosome type
 */
public abstract class CrossoverOperator<C extends Chromosome<?>> 
{
    protected boolean singleOffspring = false;
    
    public abstract Offspring recombine(Individual x, Individual y) throws CrossoverException;
    
    public void setSingleOff(boolean singleOffspring)
    {
        this.singleOffspring = singleOffspring;
    }
    
    public boolean hasSingleOffspring()
    {
        return singleOffspring;
    }
    
    protected void swap(Chromosome x, Chromosome y, int index)
    {
        Gene<C> temp = x.getGene(index);
        x.setGene(index, y.getGene(index));
        y.setGene(index, temp);
    }
}
