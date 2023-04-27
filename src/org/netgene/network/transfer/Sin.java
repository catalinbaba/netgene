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

package org.netgene.network.transfer;

/**
 * Sinus function 
 * 
 * @author CBaba
 */
public class Sin implements TransferFunction
{
     /**
     * Create a new instance of Sinus
     */ 
    public Sin() {}

    /**
     * Get the function output
     * 
     * @param value input value
     * 
     * @return function output value
     * 
     */
    @Override
    public double getFunctionOutput(double value) 
    {
        return Math.sin(value);
    }

    /**
     * Get function derivate output
     * 
     * @param value input value
     * 
     * @return function derivate
     */
    @Override
    public double getFunctionOutputDerivate(double value) 
    {
        return Math.cos(value);
    }
    
    
}
