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
 * AdaMax (Kingma and Ba, 2015) is an adaptation of the Adam optimiser by the same authors using infinity norms (hence ‘max’). m 
 * is the exponential moving average of gradients, and v is the exponential moving average of past p-norm of gradients, 
 * approximated to the max function as seen below.
 * 
 * @author CBaba
 */
public class AdaMax extends Adam implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new AdaMax optimizer
     */
    public AdaMax()
    {
       decayRate1 = 0.9;
       decayRate2 = 0.999;
       epsilon = 1e-8;
       learningRate = 0.01;  
    }
    
    @Override
    protected double calculateWeightChanges(Connection connection)
    {
        double exponentialV = decayRate1 * ((ExpConnectionData)connection.getTrainingData()).getExponentialV() +
                             (1-decayRate1) * connection.getWeightGradient();
        double exponentialS = Math.max(decayRate2* ((ExpConnectionData)connection.getTrainingData()).getExponentialS(),
                                       Math.abs(connection.getWeightGradient()));
        
        ((ExpConnectionData)connection.getTrainingData()).setExponentialV(exponentialV);
        ((ExpConnectionData)connection.getTrainingData()).setExponentialS(exponentialS);
        
        exponentialV = exponentialV /(1- Math.pow(decayRate1, 2));
        //exponentialS = exponentialS /(1- Math.pow(decayRate2, 2));
               
        double weightChange = (-1.0) * learningRate * exponentialV / (exponentialS + epsilon);
        //weightChange = (-1.0) * learningRate * exponentialV / exponentialS;

        return weightChange;
    }
     
}
