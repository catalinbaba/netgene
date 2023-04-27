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
 * Linear function
 * 
 * A straight line function where activation is proportional to input ( which is the weighted sum from neuron ).
 * 
 * @author CBaba
 */
public class Linear implements TransferFunction
{
    private double slope = 1.0;

     /**
     * Create a new instance of Linear
     */   
    public Linear() {}
    
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
        return slope * value;
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
        return slope;
    }
    
    /**
     * Get function slope
     * 
     * @return function slope
     */
    public double getSlope() {
        return slope;
    }

     /**
     * Set function slope
     * 
     * @param slope function slope
     */
    public void setSlope(double slope) {
        this.slope = slope;
    }
}
