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

package org.netgene.network.learning;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.netgene.network.NeuralNetwork;
import org.netgene.network.neuron.Neuron;
import org.netgene.network.connection.Connection;
import org.netgene.network.exception.NNException;
import org.netgene.network.layer.Layer;
import org.netgene.network.training.data.TrainingData;

/**
 *
 * @author cbaba
 */
public abstract class BackPropagation extends SupervisedLearning implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected boolean parallelLearning = false;
    
    public BackPropagation() {}    
    
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
    public void learn(NeuralNetwork neuralNetwork, TrainingData trainingData) throws NNException
    {
        setNeuralNetwork(neuralNetwork);
        setTrainingData(trainingData);
        if(trainingData.size() < batchSize)
            throw new NNException("Training Data set has a size smaller than the batch size. Please modify the bach size");
        learn();
    }
    
    /**
     *  Train neural network in parallel with training data 
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
    public void parallelLearn(NeuralNetwork neuralNetwork, TrainingData trainingData) throws NNException
    {
        parallelLearning = true;
        learn(neuralNetwork, trainingData);
    }
          
    
    /**
     *  Set pool size for parallel learning
     * 
     * @param nThreads number of threads in pool 
     * 
    */
    public void setThreadPoolSize(final int nThreads)
    {
        if(nThreads < 1)
            try {
                throw new Exception("Thread Pool Size cannot be lower than 1");
        } catch (Exception ex) {
            Logger.getLogger(BackPropagation.class.getName()).log(Level.SEVERE, null, ex);
        }
       this.nThreads= nThreads;
       this.threadPool = new ForkJoinPool(nThreads);
    }
           
  
    protected void calculateHiddenNeuronGradients(Neuron neuron)
    {
        if(neuron.hasInputConnection()) 
        {
            double nodeDelta = sumDoW(neuron) * neuron.getTransferFunction().getFunctionOutputDerivate(neuron.getInputValue());
 
            for(Connection connection : neuron.getInputConnectionList())
                    connection.setWeightGradient(nodeDelta * connection.getFromNeuron().getOutputValue());
        }
        
    }
    
    
     /**
     * Calculate hidden neuron's node delta for each neuron in the network
     *
     * */
    
    protected void calculateHiddenNeuronGradients()
    {     
       
        
        if(this.parallelLearning)
        {
            List<CompletableFuture<Void>> neuronFutures = neuralNetwork.stream()
                                      .skip(1) //skip input layer
                                      .limit(neuralNetwork.size()-2) //skip output layer
                                      .flatMap(layer -> layer.stream())
                                      .map(neuron-> 
                                      {
                                          return CompletableFuture.runAsync(() -> 
                                                {
                                                    calculateHiddenNeuronGradients(neuron);
                                                }, threadPool);
                                      })
                                      .collect(Collectors.toList());
  
            CompletableFuture<Void> allNeurons = CompletableFuture.allOf(
                                    neuronFutures.toArray(new CompletableFuture[neuronFutures.size()]));
        
            allNeurons.join();
        }
        else
        {
            for(int layerNum = neuralNetwork.size() - 2; layerNum > 0; layerNum--)
                for(int neuronNum = 0; neuronNum < neuralNetwork.getLayer(layerNum).size(); neuronNum++)
                    calculateHiddenNeuronGradients(neuralNetwork.getLayer(layerNum).getNeuron(neuronNum));
        }
    }
        
    /**
     * Calculate gradient (node delta) for an output neuron
     * @param neuron
     *          neuron to calculate its node delta or neuron gradient
     * @param targetData
     *          target data 
     * */
    protected void calculateOutputNeuronGradients(Neuron neuron, double targetData)
    {
        final double outputError = neuron.getOutputValue() - targetData;
      
        //calculate neuron gradient or node delta
        double nodeDelta = outputError * neuron.getTransferFunction().getFunctionOutputDerivate(neuron.getInputValue());
        
        //add node delta value to neuron training data
        neuron.setNodeDelta(nodeDelta);
            
        for(Connection connection : neuron.getInputConnectionList())
             connection.setWeightGradient(nodeDelta * connection.getFromNeuron().getOutputValue());
    }
    
    /**
     * Calculate output neuron's node delta for each neuron in the output layer
     * @param targetData
     *          target data 
     * */
    protected void calculateOutputNeuronGradients(Double[] targetData)
    {
        Layer outputLayer = neuralNetwork.getOutputLayer();
      
        for(int neuronNum = 0; neuronNum < outputLayer.size(); neuronNum++) 
            calculateOutputNeuronGradients(outputLayer.getNeuron(neuronNum), targetData[neuronNum]);
    }
    
            
    @Override
    protected void calculateNeuronGradients(Double[] targetData)
    {
        calculateOutputNeuronGradients(targetData);
        calculateHiddenNeuronGradients();
    }
    
    protected <T> Collector<T, ?, Stream<T> > reverseStream() 
    { 
        return Collectors 
            .collectingAndThen(Collectors.toList(), 
                               list -> { 
                                   Collections.reverse(list); 
                                   return list.stream(); 
                               }); 
    } 
       
    protected double sumDoW(Neuron neuron)
    {
                
        double sumDoW = 0.0;
        for(Connection connection : neuron.getOutputConnectionList())
            sumDoW += connection.getToNeuron().getNodeDelta() * connection.getWeight();
        return sumDoW;
    }
    
    
}
