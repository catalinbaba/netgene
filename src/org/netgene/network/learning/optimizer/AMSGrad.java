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
import org.netgene.network.learning.data.ExpConnectionData;

/**
 * Another variant of Adam is the AMSGrad (Reddi et al., 2018). 
 * This variant revisits the adaptive learning rate component in Adam and changes it to ensure that
 * the current v is always larger than the v from the previous time step.
 * 
 * @author CBaba
 */
public class AMSGrad extends Adam implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private double decayRate1 = 0.9;
    private double decayRate2 = 0.999;
    private double epsilon = 1e-7;
    private double learningRate = 0.01; 
    
    /**
     * Create a new AMSGrad optimizer
     */
    public AMSGrad() 
    {
        decayRate1 = 0.9;
        decayRate2 = 0.999;
        epsilon = 1e-7;
        learningRate = 0.01; 
    }
       
    @Override
    protected double calculateWeightChanges(Connection connection)
    {
        double exponentialV = decayRate1 * ((ExpConnectionData)connection.getTrainingData()).getExponentialV() +
                             (1-decayRate1) * connection.getWeightGradient();
        double exponentialS = decayRate2 * ((ExpConnectionData)connection.getTrainingData()).getExponentialS() +
                             (1-decayRate2) * connection.getWeightGradient() * connection.getWeightGradient();
        
        ((ExpConnectionData)connection.getTrainingData()).setExponentialV(exponentialV);
        ((ExpConnectionData)connection.getTrainingData()).setExponentialS(exponentialS);
        
        exponentialS = Math.max(((ExpConnectionData)connection.getTrainingData()).getExponentialS2(), exponentialS);
        ((ExpConnectionData)connection.getTrainingData()).setExponentialS2(exponentialS);
               
        double weightChange = (-1.0) * (learningRate * exponentialV / (Math.sqrt(exponentialS) + epsilon));

        return weightChange;
    }
    
}
