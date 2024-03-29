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
 * Adaptive gradient, or AdaGrad (Duchi et al., 2011), acts on the learning rate component by dividing the learning rate by the square root of v, 
 * which is the cumulative sum of current and past squared gradients (i.e. up to time t). 
 * 
 * @author CBaba
 */
public class AdaGrad extends SGD implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private double epsilon = 1e-7;

    /**
    *  Create a new AdaGrad optimizer
    */
    public AdaGrad()
    {
       learningRate = 0.01;  
    }
    
    @Override
    public double calculateWeightChanges(Connection connection)
    {
        double gradient = 0.0;
        double weightChange = 0.0;
        double squaredGrad = connection.getWeightGradient() * connection.getWeightGradient();
              
        weightChange = (-1.0) * (learningRate / Math.sqrt(squaredGrad + epsilon)) * connection.getWeightGradient();

        return weightChange;
    }
    
    /**
    * Get epsilon parameter
    * 
    * @return epsilon parameter
    */
    public double getEpsilon() {
        return epsilon;
    }

    /**
    * Set epsilon parameter
    * 
    * @param epsilon epsilon parameter
    */
    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
    
}
