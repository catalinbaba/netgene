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
import org.netgene.network.learning.data.RMSpropConnectionData;
import org.netgene.network.training.data.TrainingData;

/**
 * Root mean square prop or RMSprop (Hinton et al., 2012) is another adaptive learning rate that tries to improve AdaGrad. 
 * Instead of taking cumulative sum of squared gradients like in AdaGrad, we take the exponential moving average (again!) of these gradients. 
 * Similar to momentum, we will slowly see that this update becomes the standard update for the learning rate component for most optimizers.
 * 
 * @author CBaba
 */
public class RMSprop extends SGD implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private double decayRate = 0.9;
    
    private double epsilon = 1e-6;
    
    private double gradientClip = 5;
    
    /**
     * Set decay rate
     * 
     * @param decayRate decay rate 
     */
    public void setDecayRate(double decayRate) {
        this.decayRate = decayRate;
    }

     /**
     * Set epsilon
     * 
     * @param epsilon epsilon 
     */
    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

     /**
     * Set gradient clip
     * 
     * @param gradientClip gradient clip 
     */
    public void setGradientClip(double gradientClip) {
        this.gradientClip = gradientClip;
    }

    /**
     * Get decay rate
     * 
     * @return decay rate 
     */
    public double getDecayRate() {
        return decayRate;
    }

     /**
     * get epsilon
     * 
     * @return epsilon 
     */
    public double getEpsilon() {
        return epsilon;
    }
   
     /**
     * get gradient clip
     * 
     * @return gradient clip 
     */
    public double getGradientClip() {
        return gradientClip;
    }
    
    /**
     * Create a new RMSprop optimizer
     */
    public RMSprop() 
    {
        learningRate = 0.01;
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
                    connection.setTrainingData(new RMSpropConnectionData());        
                });
    }
    
    @Override
    protected double calculateWeightChanges(Connection connection)
    {
        double gradient = 0.0;
        double exponentialAverage = decayRate * ((RMSpropConnectionData)connection.getTrainingData()).getExponentialAverage() +
                             (1-decayRate) * connection.getWeightGradient() * connection.getWeightGradient();
        ((RMSpropConnectionData)connection.getTrainingData()).setExponentialAverage(exponentialAverage);
        
        // gradient clip
        if (connection.getWeightGradient() > gradientClip) {
            gradient = gradientClip;
        }
        if (connection.getWeightGradient()< -gradientClip) {
            gradient = -gradientClip;
        }
        
        double weightChange = (-1.0) * (learningRate / Math.sqrt(exponentialAverage + epsilon)) * connection.getWeightGradient();
                 
        //((ConnectionData)(connection.getTrainingData())).setWeightChange(weightChange);
        return weightChange;
    }
}
