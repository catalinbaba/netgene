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
import org.netgene.network.neuron.Neuron;

/**
 * Similar to Stochastic Gradient Descent. The difference is how node delta (neuron gradient si calculated)
 * Nod delta is calculated using previous neuron output value instead of neuron input value.
 * The method was introduced in Dave Miller's C code implementation - http://www.millermattson.com/dave/?p=54
 * 
 * @author CBaba
 */
public class MillerSGD extends MSGD implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new Miller Stochastic Gradient Descent
     */
    public MillerSGD() {}
    
    protected void calculateHiddenNeuronGradients(Neuron neuron)
    {
        if(neuron.hasInputConnection()) 
        {
            double nodeDelta = sumDoW(neuron) * neuron.getTransferFunction().getFunctionOutputDerivate(neuron.getOutputValue());  //use previous neuron output value instead of neuron input value
 
            for(Connection connection : neuron.getInputConnectionList())
                    connection.setWeightGradient(nodeDelta * connection.getFromNeuron().getOutputValue());
        }
    }
}
