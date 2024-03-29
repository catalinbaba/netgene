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

package org.netgene.network.learning.optimizer;

import java.io.Serializable;
import org.netgene.network.connection.Connection;


/**
 * Instead of depending only on the current gradient to update the weight, gradient descent with momentum (Polyak, 1964) 
 * replaces the current gradient with m (“momentum”), which is an aggregate of gradients. 
 * This aggregate is the exponential moving average of current and past gradients (i.e. up to time t). 
 * 
 * @author CBaba
 */
public class MSGD extends SGD implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected double momentum = 0.5; //momentum default value
    
    /**
     * Create a new momentum stochastic gradient descent optimizer
     */
    public MSGD() {}
    
    /**
     * Set momentum value
     * 
     * @param momentum momentum value 
     */
    public void setMomentum(double momentum)
    {
        this.momentum = momentum;
    }
    
    /**
     * Get momentum value
     * 
     * @return momentum value 
     */
    public double getMomentum()
    {
        return this.momentum;
    }
           
       
    @Override
    protected double calculateWeightChanges(Connection connection)
    {
        double weightChange = (-1.0) *  //negative 
                       learningRate *     // learning rate parameter
                       connection.getWeightGradient() +
                        momentum *  //momentum
                       connection.getWeightChange();
        return weightChange;
    }
    
}
