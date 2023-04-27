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
 * Exponential Linear Unit or its widely known name ELU was introduced. 
 * Researchs reveal that the function tend to converge cost to zero faster and produce more accurate results. 
 * Different to other activation functions, ELU has a extra alpha constant which should be positive number.
 * 
 * @author CBaba
 */
public class Elu implements TransferFunction
{
    private double alpha = 1.0;
   
    /**
     * Create a new instance of Elu
     */
    public Elu() {}
    
    /**
     * Get the function output
     * 
     * @param value input value
     * @return function output value
     * 
     */
    @Override
    public double getFunctionOutput(double value)
    {
        if(value >= 0)
           return value;
        else
           return alpha * (Math.exp(value) - 1);
    }
    
    /**
     * Get function derivate output
     * @param value input value
     * @return function derivate
     */
    @Override
    public double getFunctionOutputDerivate(double value)
    {
        if(value > 0)
            return 1;
        else
            return alpha * Math.exp(value);
    }
    
    /**
     * Get alpha parameter 
     * 
     * @return alpha parameter
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Set alpha parameter 
     * 
     * @param alpha parameter
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
}
