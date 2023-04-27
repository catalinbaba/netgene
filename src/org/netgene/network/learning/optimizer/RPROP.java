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
import org.netgene.network.NeuralNetwork;
import org.netgene.network.connection.Connection;
import org.netgene.network.exception.NNException;
import org.netgene.network.learning.ResilientPropagation;
import org.netgene.network.learning.data.ResilientConnectionData;
import org.netgene.network.neuron.Neuron;
import org.netgene.network.training.data.TrainingData;
import org.netgene.utils.Task;

/**
 * Rprop stands for 'Resilient backpropagation' and is a local adaptive learning scheme, performing supervised batch learning in multi-layer perceptrons.
 * 
 * Resilient back propagation (Rprop), is an algorithm that can be used to train a neural network, is similar to the more common (regular) back-propagation. 
 * But it has two main advantages over back propagation: First, training with Rprop is often faster than training with back propagation. 
 * Second, Rprop doesn't require you to specify any free parameter values, as opposed to back propagation which needs values for 
 * the learning rate (and usually an optional momentum term). 
 * The main disadvantage of Rprop is that it's a more complex algorithm to implement than back propagation.
 * 
 * @author CBaba
 */
public abstract class RPROP extends ResilientPropagation implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final double initialUpdateValue = 0.1;
       
    private double decreaseFactor = 0.5;
    private double increaseFactor = 1.2;
    
    private double maxUpdateValue = 1.0;
    private double minUpdateValue = 1e-6;
    
    /**
     *  Train neural network with training data 
     * 
     * @param neuralNetwork   
     *  Neural network to train
     * 
     * @param trainingData  
     *  Training data to learn
     * 
     * @throws NNException if training data size is smaller than the batch size in bach mode learning
    */
    @Override
    public void learn(NeuralNetwork neuralNetwork, TrainingData trainingData) throws NNException
    {
        setNeuralNetwork(neuralNetwork);
        setTrainingData(trainingData);
        initializeTrainingData();
        batchMode = true; //resilient progpagation only in batch mode
        batchSize = trainingData.size();
        learn();
    }
    
    protected void initializeTrainingData()
    {
         neuralNetwork.stream().flatMap((layer) -> 
                {
                    return layer.stream();
                }).flatMap((neuron) ->
                {
                    return neuron.getInputConnectionList().stream();
                }).forEach(connection ->
                {
                    ResilientConnectionData connectionTrainingData = new ResilientConnectionData();
                    connectionTrainingData.setUpdateValue(initialUpdateValue);
                    connection.setTrainingData(connectionTrainingData);        
                });
    }
    
    protected void calculateWeightChanges(Neuron neuron)
    {
        if(neuron.hasInputConnection()) // do not update weights for bias neuron or for neuron that has no weights
        {        
             neuron.getInputConnectionList().stream()
                        .forEach(connection -> 
                        {
                            double weightChange = calculateWeightChanges(connection);
                            ((ResilientConnectionData)(connection.getTrainingData())).setWeightChange(weightChange);
                        });
        }
    }
    
    protected void setConnectionWeight(Neuron neuron)
    {
         neuron.getInputConnectionList()
               .stream()
               .forEach(connection -> 
               {
                    double weightChange = ((ResilientConnectionData)(connection.getTrainingData())).getWeightChange();  
                    connection.setWeight(connection.getWeight() + weightChange);
                });
    }
    
     @Override
    protected void updateNetworkWeights()
    {
        Task updateNetworkWeightsTask = () ->
        {
            neuralNetwork.parallelStream()
                    .flatMap(layer -> layer.parallelStream())
                    .forEach(neuron ->
                    {
                        calculateWeightChanges(neuron);
                        setConnectionWeight(neuron);
                    });
        };
    
                
        taskExecutor.runTask(updateNetworkWeightsTask, threadPool);
    }
    
    abstract protected double calculateWeightChanges(Connection connection);
    
    /**
     * Set increase factor
     * 
     * @param increaseFactor increase factor
     */
    public void setIncreaseFactor(double increaseFactor)
    {
        this.increaseFactor = increaseFactor;
    }
    
    /**
     * get increase factor
     * 
     * @return increase factor
     */
    public double getIncreaseFactor()
    {
        return this.increaseFactor;
    }
    
    /**
     * Set decrease factor
     * 
     * @param decreaseFactor decrease factor 
     */
    public void setDecreaseFactor(double decreaseFactor)
    {
        this.decreaseFactor = decreaseFactor;
    }
    
    /**
     * Get decrease factor
     * 
     * @return decrease factor 
     */
    public double getDecreaseFactor()
    {
        return this.decreaseFactor;
    }
    
    /**
     * Get maximum update value
     * 
     * @return maximum update value
     */
    public double getMaxUpdateValue() {
        return maxUpdateValue;
    }
 
    /**
     * Get minimum update value
     * 
     * @return minimum update value
     */
    public double getMinUpdateValue() {
        return minUpdateValue;
    }

    /**
     * Set maximum update value
     * 
     * @param maxUpdateValue maximum update value
     */
    public void setMaxUpdateValue(double maxUpdateValue) {
        this.maxUpdateValue = maxUpdateValue;
    }

     /**
     * Set minimum update value
     * 
     * @param minUpdateValue minimum update value
     */
    public void setMinUpdateValue(double minUpdateValue) {
        this.minUpdateValue = minUpdateValue;
    }
}
