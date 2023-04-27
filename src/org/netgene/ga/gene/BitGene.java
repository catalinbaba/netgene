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
 * Implementation of a BitGene.
 * 
 * @author Catalin Baba
 */
public final class BitGene implements Gene<Boolean>, Comparable<BitGene>, Serializable 
{
    private static final long serialVersionUID = 1L;
    
    private final Boolean allele;
    
    /**
     * Creates a new instance of BiteGene with the given value
     * 
     * @param allele value to set
     */
    public BitGene(final boolean allele) 
    {
        this.allele = allele;
    }
    
     /**
     * Creates a new instance of BiteGene with a random value
     * 
     */
     
    public BitGene() 
    {
        this.allele = RandomUtils.nextBoolean();
    }
    
    /**
     * Get allele value 
     * 
     * @return allele
     */
    @Override
    public Boolean getAllele() {
        return allele;
    }
    
    @Override
    public String toString() {
        return allele.toString();
    }
    
    @Override
    public int compareTo(final BitGene anotherGene) {
        return Boolean.compare(allele, anotherGene.getAllele());
    }

    /**
    * Create a copy of this gene
    *
    * @return copy of this gene
    */
    @Override
    public BitGene copy() {
        return new BitGene(allele);
    }
    
    /**
    * Create a new gene from the given value.
    *
    * @param allele set allele value
    * 
    * @return new gene from the given value
    */
    @Override
    public BitGene newInstance(final Boolean allele)
    {
        return new BitGene(allele);
    }
}
