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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.netgene.network.connection.Connection;
import org.netgene.network.layer.Layer;
import org.netgene.network.learning.BackPropagation;
import org.netgene.network.neuron.Neuron;


/**
 * Gradient descent is an optimization method for finding the minimum of a function. 
 * It is commonly used in deep learning models to update the weights of a neural network through backpropagation.
 * 
 * @author CBaba
 */
public class SGD extends BackPropagation implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected double learningRate = 0.15; 
     
    /**
     * Create a new Stochastic gradient descent
     */
    public SGD() {}
    
    /**
     * Set learning rate
     * 
     * @param learningRate second decay rate
     */
    public void setLearningRate(double learningRate)
    {
        this.learningRate = learningRate;
    }
    
    /**
     * Get learning rate
     * 
     * @return learning rate
     */
    public double getLearningRate()
    {
        return this.learningRate;
    }
        
    private void calculateWeightChanges(Neuron neuron)
    {
        //if next neuron does not have connection there is no need to calculate further
        if(neuron.hasInputConnection()) // do not update weights for bias neuron or for neuron that has no connections
        {    
            
            for(Connection connection : neuron.getInputConnectionList())
                if(!batchMode)
                    connection.setWeightChange(calculateWeightChanges(connection));
                else
                    connection.setWeightChange(connection.getWeightChange()+calculateWeightChanges(connection)); //acumulate weights changes (previous + actuall)
            
            /*
            neuron.getInputConnectionList().stream()
                        .forEach(connection -> 
                        {
                            if(!batchMode)
                                connection.setWeightChange(calculateWeightChanges(connection));
                            else
                                connection.setWeightChange(connection.getWeightChange()+calculateWeightChanges(connection)); //acumulate weights changes (previous + actuall)
                        });
            */
        }

    }
    
    protected double calculateWeightChanges(Connection connection)
    {
        double weightChange = 0.0;
                   
        weightChange = (-1.0) *  //negative 
                       learningRate *     // learning rate parameter
                       connection.getWeightGradient();
        return weightChange;
    }
    
    protected void setConnectionWeight(Neuron neuron)
    {
        
        for(Connection connection : neuron.getInputConnectionList())
            if(!batchMode)
                connection.setWeight(connection.getWeight() + connection.getWeightChange());
            else
            {
                connection.setWeight(connection.getWeight() + connection.getWeightChange()/batchSize); 
                connection.setWeightChange(0.0); 
        }
       
        /*
        neuron.getInputConnectionList()
               .stream()
               .forEach(connection -> 
                {
                    if(!batchMode)
                    {
                        connection.setWeight(connection.getWeight() + connection.getWeightChange());
                    }
                    else
                    {
                        connection.setWeight(connection.getWeight() + connection.getWeightChange()/batchSize); 
                        connection.setWeightChange(0.0); 
                    }
                });
        */
    }
 
    @Override
    protected void updateNetworkWeights()
    {
        
        if(parallelLearning)
        {
            List<CompletableFuture<Void>> neuronFutures = neuralNetwork.stream()
                                      .skip(1)
                                      .flatMap(layer -> layer.stream())
                                      .map(neuron-> 
                                      {
                                           return CompletableFuture.runAsync(() -> 
                                           {
                                                calculateWeightChanges(neuron);
                                                setConnectionWeight(neuron);

                                           }, threadPool);
                                      })
                                      .collect(Collectors.toList());
  
            CompletableFuture<Void> allNeurons = CompletableFuture.allOf(
                                    neuronFutures.toArray(new CompletableFuture[neuronFutures.size()]));
          
            allNeurons.join();
        }
        else
        {
            for(Layer layer : neuralNetwork.getLayers())
                for(Neuron neuron : layer.getNeurons())
                {
                    calculateWeightChanges(neuron);
                    setConnectionWeight(neuron);
                }
        }
    }

    
    public void setBatchMode(int batchSize)
    {
       this.batchMode = true;
       this.batchSize = batchSize;
    }
   
   public void setOnlineMode()
   {
       this.batchMode = false;
       this.batchSize = 1;
   }
   
   public boolean getBatchMode()
   {
       return batchMode;
   }
   
   public int getBatchSize()
   {
       return batchSize;
   }
}
