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
 * Hard Simoid function
 * 
 * Instead of calculating the sigmoid as defined above, you can use an approximation of the sigmoid.  
 * When trained, the hard sigmoid can generate very similar outputs to the sigmoid, but is much easier computationally to calculate. 
 * 
 * @author CBaba
 */
public class HardSigmoid implements TransferFunction
{
    private double slope = 0.2;
    private double limit = 3;
    
    /**
     * Create a new instance of hard Sigmoid
     */
    public HardSigmoid() {}
    
     /**
     * Create a new instance of hard Sigmoid
     * 
     * @param slope specify function slope
     */
    public HardSigmoid(double slope)
    {
        this.slope = slope;
    }
    
    
     /**
     * Create a new instance of hard Sigmoid
     * 
     * @param slope specify function slope
     * 
     * @param limit set a limit
     */
    public HardSigmoid(double slope, double limit)
    {
        this.slope = 0.2;
        this.limit = 2.5;
    }

     
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
        if(value > limit )
            return 1;
        else if(value < -limit)
            return 0;
        else
            return slope * value + 0.5;
        
    }

    /**
     * Get function derivate output
     * @param value input value
     * @return function derivate
     */
    @Override
    public double getFunctionOutputDerivate(double value) 
    {
        if(value > limit || value < -limit )
            return 0;
        else
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
