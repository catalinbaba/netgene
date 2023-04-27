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

package org.netgene.network.connection;

import java.io.Serializable;
import org.netgene.network.neuron.Neuron;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author CBaba
 */
public class Connection<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    *  Destination neuron
    */
    private Neuron toNeuron;
    
    /**
      Source neuron
    */
    private Neuron fromNeuron;
    
    /**
     *  Weight for this connection
    */
    private double weight;

   
    private transient double weightGradient;
    
    private transient double weightChange;
            
    private transient T trainingData;
    
    
    /**
     *  Create a connection between two neurons
     * 
     * @param fromNeuron source neuron
     *  
     * @param toNeuron destination neuron
    */
    public Connection(Neuron fromNeuron, Neuron toNeuron)
    {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        randomWeight();
    }
    
    /**
     *  Create a connection between two neurons with specified weight value
     * 
     * @param fromNeuron source neuron
     *  
     * @param toNeuron destination neuron
     * 
     * @param weight weight value for this connection
    */
    public Connection(Neuron fromNeuron, Neuron toNeuron, double weight)   
    {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.weight = weight;
    }
  
    /**
     *  Set weight value for this connection
     * 
     * @param weight weight value for this connection
    */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }
    
    /**
     *  Get weight value for this connection
     * 
     * @return weight for this connection
    */
    public double getWeight()
    {
        return this.weight;
    }
     
    /**
     *  Get source neuron for this connection
     * 
     * @return source neuron
    */
    public Neuron getFromNeuron()
    {
        return this.fromNeuron;
    }
    
    /**
     *  Get destination neuron for this connection
     * 
     * @return destination neuron
    */
    public Neuron getToNeuron()
    {
        return this.toNeuron;
    }
    
    /**
     *  Set training data for this connection
     * 
     * @param trainingData training data for this connection
    */    
    public void setTrainingData(T trainingData)
    {
        this.trainingData = trainingData;
    }
    
    /**
     *  Get training data for this connection
     * 
     * @return training data for this connection
    */
    public T getTrainingData()
    {
        return this.trainingData;
    }
    
    /**
     *  Set weight gradient for this connection
     * 
     * @param weightGradient weight gradient for this connection
    */
    public void setWeightGradient(double weightGradient) 
    {
        this.weightGradient = weightGradient;
    }

    /**
     *  Set weight change for this connection
     * 
     * @param weightChange weight change for this connection
    */
    public void setWeightChange(double weightChange) 
    {
        this.weightChange = weightChange;
    }
    
    /**
     *  Get weight gradient for this connection
     * 
     * @return weight gradient for this connection
    */
    public double getWeightGradient() 
    {
        return weightGradient;
    }

     /**
     *  Get weight change for this connection
     * 
     * @return weight change for this connection
    */
    public double getWeightChange() 
    {
        return weightChange;
    }
    
    private void randomWeight()
    {
        //double upper = 0.65;
        //double lower = -0.65;
        //double random =  RandomUtils.nextDouble(lower, upper);
        //double random = RandomUtils.nextGaussian();
        //weight = random;
        //weight = 0.1;  //only for debug
        weight = RandomUtils.nextDouble() - 0.5;
    }
}
