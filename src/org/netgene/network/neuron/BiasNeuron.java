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

package org.netgene.network.neuron;

import java.io.Serializable;
import org.netgene.network.connection.Connection;

/**
 * The bias neuron is a special neuron added to each layer in the neural network, which simply stores the value of 1 and it has no connections
 * 
 * @author CBaba
 */
public class BiasNeuron extends Neuron implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     *  Creates an instance of a bias Neuron
     */
    public BiasNeuron() 
    {
        super();
    }
      
    /**
        * Get bias neuron output value.
        * 
        * @return bias neuron output value
        * 
    */
    @Override
    public double getOutputValue()
    {
        return 1.0;
    }
    
     /**
        * Add new input connection to bias neuron
        * 
        * @param connection
        *       connection to set
    */
    @Override
    public void addInputConnection(Connection connection)
    {
        //do nothing - bias neuron can not have connections
    }
    
    /**
     * Always return false for bias neurons
     * 
     * @return false for bias neurons
     */    
    @Override
    public boolean hasInputConnection()
    {
        return false;
    }
    
    /**
     * The output calculated value for bias neurons is always 1.0
     * 
    */
    @Override
    public void calculateNeuronOutput()
    {
        setInputValue(1.0);
    }
    
}
