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
 *
 * ReLU is the most commonly used activation function in neural networks, especially in CNNs
 * 
 * ReLU is linear (identity) for all positive values, and zero for all negative values
 * his means that:
 * It’s cheap to compute as there is no complicated math. The model can therefore take less time to train or run.
 * It converges faster. Linearity means that the slope doesn’t plateau, or “saturate,” when x gets large. It doesn’t have the vanishing gradient problem suffered by other activation functions like sigmoid or tanh.
 * It’s sparsely activated. Since ReLU is zero for all negative inputs, it’s likely for any given unit to not activate at all. This is often desirable (see below).
 * 
 * @author CBaba
 */
public class Relu implements TransferFunction
{
    /**
     * Create a new instance of Relu
     */   
    public Relu() {}
    
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
        return Math.max(0, value);
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
        if(value > 0)
            return 1;
        else
             return 0;
    }
}
