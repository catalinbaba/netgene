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
 * Nadam (Dozat, 2015) is an acronym for Nesterov and Adam optimiser. 
 * The Nesterov component, however, is a more efficient modification than its original implementation.
 * 
 * @author CBaba
 */
public class Nadam extends Adam implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new Nadam optimizer
     */
    public Nadam()
    {
        decayRate1 = 0.9;
        decayRate2 = 0.999;
        epsilon = 1e-7;
        learningRate = 0.1;  
    }
    
    @Override
    public double calculateWeightChanges(Connection connection)
    {
        double exponentialV = decayRate1 * ((ExpConnectionData)connection.getTrainingData()).getExponentialV() +
                             (1-decayRate1) * connection.getWeightGradient();
        double exponentialS = decayRate2 * ((ExpConnectionData)connection.getTrainingData()).getExponentialS() +
                             (1-decayRate2) * connection.getWeightGradient() * connection.getWeightGradient();
        
        ((ExpConnectionData)connection.getTrainingData()).setExponentialV(exponentialV);
        ((ExpConnectionData)connection.getTrainingData()).setExponentialS(exponentialS);
        
        exponentialV = exponentialV /(1- Math.pow(decayRate1, 2));
        exponentialS = exponentialS /(1- Math.pow(decayRate2, 2));
       
               
        double weightChange = (-1.0) * learningRate /(Math.sqrt(exponentialS) + epsilon) * 
                       (decayRate1 * exponentialV + (1-decayRate1)/(1- Math.pow(decayRate1, 2)) * connection.getWeightGradient());

        return weightChange;
    }
}
