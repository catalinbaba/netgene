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
import org.netgene.network.learning.data.AdadeltaConnectionData;
import org.netgene.network.neuron.Neuron;
import org.netgene.network.training.data.TrainingData;

/**
 * Like RMSprop, Adadelta (Zeiler, 2012) is also another improvement from AdaGrad, focusing on the learning rate component.
 * Adadelta is probably short for ‘adaptive delta’, where delta here refers to the difference between the current weight and the newly updated weight.
 * The difference between Adadelta and RMSprop is that Adadelta removes the use of the learning rate parameter completely by replacing it with D, 
 * the exponential moving average of squared deltas.
 * 
 * @author CBaba
 */
public class Adadelta extends SGD implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected double epsilon = 1e-6;
    
    /**
     * Create a new Adadelta optimizer
     */
    public Adadelta() 
    {
        learningRate = 0.95;
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
                    connection.setTrainingData(new AdadeltaConnectionData());        
                });
    }
    
    @Override
    protected void setConnectionWeight(Neuron neuron)
    {
         neuron.getInputConnectionList()
               .stream()
               .forEach(connection -> 
               {
                    ((AdadeltaConnectionData)connection.getTrainingData()).setPreviousWeight(connection.getWeight());
                    if(!batchMode)
                        connection.setWeight(connection.getWeight() + connection.getWeightChange());
                    else
                    {
                        connection.setWeight(connection.getWeight() + connection.getWeightChange()/batchSize); 
                        connection.setWeightChange(0.0); 
                    }
                });
    }
    
    @Override
    protected double calculateWeightChanges(Connection connection)
    {
        double deltaWeight = connection.getWeight() - ((AdadeltaConnectionData)connection.getTrainingData()).getPreviousWeight();
        
        double exponentialD = learningRate * ((AdadeltaConnectionData)connection.getTrainingData()).getExponentialD() +
                             (1-learningRate) * deltaWeight * deltaWeight;
        double exponentialS = learningRate * ((AdadeltaConnectionData)connection.getTrainingData()).getExponentialS() +
                             (1-learningRate) * connection.getWeightGradient() * connection.getWeightGradient();
        
        
        double weightChange = (-1.0) * Math.sqrt(((AdadeltaConnectionData)connection.getTrainingData()).getExponentialD() + epsilon)/
                              Math.sqrt(exponentialS + epsilon) * connection.getWeightGradient();
               
        ((AdadeltaConnectionData)connection.getTrainingData()).setExponentialD(exponentialD );
        ((AdadeltaConnectionData)connection.getTrainingData()).setExponentialS(exponentialS);
  
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
