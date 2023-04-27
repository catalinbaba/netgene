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
import org.netgene.network.learning.data.ExpConnectionData;
import org.netgene.network.training.data.TrainingData;

/**
 * Adaptive moment estimation, or Adam (Kingma and Ba, 2014), is simply a combination of momentum and RMSprop. It acts upon:
 *  1. the gradient component by using m, the exponential moving average of gradients (like in momentum), and
 *  2. the learning rate component by dividing the learning rate α by square root of v, the exponential moving average of squared gradients (like in RMSprop).
 *  
 * @author CBaba
 */
public class Adam extends SGD implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected double decayRate1 = 0.9;
    
    protected double decayRate2 = 0.999;
    
    protected double epsilon = 1e-8;
    
    protected double learningRate = 0.05; 
    
    /**
     * Create a new Adam optimizer
     */ 
    public Adam() {}

    /**
     * Get first decay rate
     * 
     * @return first decay rate
     */
    public double getDecayRate1() {
        return decayRate1;
    }

    /**
     * Get second decay rate
     * 
     * @return second decay rate
     */
    public double getDecayRate2() {
        return decayRate2;
    }

    /**
     * Get epsilon
     * 
     * @return epsilon
     */
    public double getEpsilon() {
        return epsilon;
    }

    /**
     * Get learning rate
     * 
     * @return learning rate
     */
    @Override
    public double getLearningRate() {
        return learningRate;
    }

    /**
     * Set first decay rate
     * 
     * @param decayRate1 first decay rate
     */
    public void setDecayRate1(double decayRate1) {
        this.decayRate1 = decayRate1;
    }

    /**
     * Set second decay rate
     * 
     * @param decayRate2 second decay rate
     */
    public void setDecayRate2(double decayRate2) {
        this.decayRate2 = decayRate2;
    }

    /**
     * Set epsilon
     * 
     * @param epsilon set epsilon
     */
    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    /**
     * Set learning rate
     * 
     * @param learningRate second decay rate
     */
    @Override
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
      
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
        if(trainingData.size() < batchSize)
            throw new NNException("Training Data set has a size smaller than the batch size. Please modify the bach size");
        learn();
    }
        
     //@Override
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
                    connection.setTrainingData(new ExpConnectionData());        
                });
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
        
        exponentialV = exponentialV /(1- Math.pow(decayRate1, Math.max(2, currentIteration)));
        exponentialS = exponentialS /(1- Math.pow(decayRate2, Math.max(2, currentIteration)));
             
               
        double weightChange = (-1.0) * (learningRate * exponentialV / (Math.sqrt(exponentialS) + epsilon));

        return weightChange;
    }
    
    
}
