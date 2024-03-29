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

package org.netgene.ga.gene;

import java.io.Serializable;
import org.netgene.utils.RandomUtils;


/**
 * Implementation of a IntegerGene.
 * 
 * @author Catalin Baba
 */
public final class IntegerGene extends NumericGene<Integer> implements Comparable<IntegerGene>, Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final Integer allele;
    
     /**
     * Creates a new instance of integerGene with the given value
     * 
     * @param allele value to set
     */
    public IntegerGene(final Integer allele) 
    {
        this.allele = allele;
    }
    
    /**
     * Creates a new instance of integerGene with a random value
     * 
     */
    public IntegerGene() 
    {
        this.allele = RandomUtils.nextInt();
    }
    
     /**
     * Creates a new instance of integerGene with a random value - uniformly distributed int value between minRange (inclusive) and maxRange (exclusive)
     * 
     * @param minRange - inclusive value
     * 
     * @param maxRange - exclusive value
     */
    public IntegerGene(final int minRange, final int maxRange) 
    {
        this.allele = RandomUtils.nextInt(minRange, maxRange);
    }
    
    /**
     * Get allele value 
     * 
     * @return allele
     */
    @Override
    public Integer getAllele() {
        return allele;
    }
        
    @Override
    public String toString() {
        return allele.toString();
    }
 
    @Override
    public int compareTo(final IntegerGene anotherGene) {
        return Integer.compare(allele, anotherGene.getAllele());
    }
   
    /**
    * Create a copy of this gene
    *
    * @return copy of this gene
    */
    @Override
    public IntegerGene copy()
    {
        return new IntegerGene(allele);
    }
    
    /**
    * Create a new gene from the given value.
    *
    * @param allele set allele value
    * 
    * @return new gene from the given value
    */
    @Override
    public IntegerGene newInstance(final Number allele)
    {
        return new IntegerGene((Integer)allele);
    }
    
    @Override
    public IntegerGene average(final Gene<Number> gene) {
        return new IntegerGene((allele + (Integer)gene.getAllele())/2);
    }
}
