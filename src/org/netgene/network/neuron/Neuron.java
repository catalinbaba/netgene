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
import java.util.ArrayList;
import java.util.Collections;
import org.netgene.network.input.InputFunction;
import org.netgene.network.input.Sum;
import org.netgene.network.transfer.Tanh;
import org.netgene.network.transfer.TransferFunction;
import java.util.List;
import org.netgene.network.connection.Connection;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author CBaba
 */
public class Neuron implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
	 * Transfer function for this neuron
    */
    private TransferFunction transferFunction;
    
    /**
	 * Input function for this neuron
    */
    private InputFunction inputFunction;
   
    /**
	 * Neuron output value
    */
    private double outputValue;
    
    /**
        * Neuron input value
    */
    private double inputValue;
    
    /**
	 * Collection list of neuron's input connections 
    */
    //private ConnectionList inputConnections;
    
    private List<Connection> inputConnections;
    
    /**
	 * Collection list of neuron's output connections 
    */
    //private ConnectionList outputConnections;
    
    private List<Connection> outputConnections;
    
      
    /**
         * Training data buffer which is used by learning algorithm during training process
    */
    private transient Object trainingData;
    
    /**
         * Neuron delta
    */
    
    private transient double nodeDelta;
     
    /**
     *  Creates an instance of Neuron with default transfer and input functions.
     */
    public Neuron()
    {
        transferFunction = new Tanh();
        inputFunction = new Sum();
        inputConnections = Collections.synchronizedList(new ArrayList()); 
        outputConnections = Collections.synchronizedList(new ArrayList()); 
    }
  
    /**
     * Creates an instance of Neuron with the specified transfer and input functions.
     * @param transferFunction
     *          transfer function for this neuron
     * @param inputFunctions 
     *          input function for this neuron
     */
    public Neuron(TransferFunction transferFunction, InputFunction inputFunctions)
    {
        
        this.transferFunction = transferFunction;
        this.inputFunction = inputFunctions;
        inputConnections = Collections.synchronizedList(new ArrayList<Connection>()); 
        outputConnections = Collections.synchronizedList(new ArrayList<Connection>()); 
    }
    
    /**
     * Creates an instance of Neuron with the specified transfer and input functions.
     * @param transferFunction
     *          transfer function for this neuron
     */
    public Neuron(TransferFunction transferFunction)
    {
        this.transferFunction = transferFunction;
        inputFunction = new Sum();
        inputConnections = Collections.synchronizedList(new ArrayList<Connection>()); 
        outputConnections = Collections.synchronizedList(new ArrayList<Connection>()); 
    }
    
     /**
     * Creates an instance of Neuron with the specified input function.
     * @param inputFunctions 
     *          input function for this neuron
     */
    public Neuron(InputFunction inputFunctions)
    {
        this.transferFunction = new Tanh();;
        this.inputFunction = inputFunctions;
        inputConnections = Collections.synchronizedList(new ArrayList<Connection>()); 
        outputConnections = Collections.synchronizedList(new ArrayList<Connection>()); 
    }
    
   
    /**
        * Set neuron output value. This method is useful to set bias neuron output.
        *
        * @param outputValue
        *            output value to set
        *
    */
    public void setOutputValue(double outputValue) 
    {
        this.outputValue = outputValue;
    }
    
    /**
        * Get neuron output value.
        * 
        * @return neuron output value
        * 
    */
    public double getOutputValue()
    {
        return this.outputValue;
    }
    
    /**
        * Set neuron input value
        * 
        * @param inputValue
        *       input value to set
    */
    
    public void setInputValue(double inputValue)
    {
        this.inputValue = inputValue;
    }
    
    /**
        * Get neuron input value
        * 
        * @return inputValue
        *           
    */
    public double getInputValue()
    {
        return this.inputValue;
    }
    
    /**
        * Add new input connection to neuron
        * 
        * @param connection
        *       connection to set
    */
    
    public void addInputConnection(Connection connection)
    {
        inputConnections.add(connection);
    }
    
    /**
        * Get input connection from a specified neuron
        * 
        * @param index
        *           neuron index
        * @return Connection
    */
    
    public Connection getInputConnection(int index)
    {
        return inputConnections.get(index);
    }
      
    /**
     * Returns input connections for this neuron
     * @return ConnectionList
     *  
     */
    public List<Connection> getInputConnectionList()
    {
        return inputConnections;
    }
    
    
    /**
        * Add new output connection to neuron
        * 
        * @param connection
        *       connection to set
    */
    
    public void addOutputConnection(Connection connection)
    {
        outputConnections.add(connection);
    }
    
     /**
        * Get output connection from a specified neuron
        * 
        * @param index
        *           neuron index
        * @return Connection
    */
    
    public Connection getOutputConnection(int index)
    {
        return outputConnections.get(index);
    }
    
     /**
     * Returns input connections for this neuron
     * @return ConnectionList
     *  
     */
    public List<Connection> getOutputConnectionList()
    {
        return outputConnections;
    }
    
    /**
     * Returns true if the neuron has no connection, otherwise false
     * 
     * @return true if the neuron has no connection, otherwise false
     */
    public boolean hasInputConnection()
    {
        return !inputConnections.isEmpty();
    }
      
    /**
        * Set transfer function for this neuron
        * 
        * @param transferFunction
        *           transfer function for this neuron
    */
    public void setTransferFunction(TransferFunction transferFunction)
    {
        this.transferFunction = transferFunction;
    }
    
    /**
        * Get transfer function for this neuron
        * 
        * @return TransferFuntion
    */
    public TransferFunction getTransferFunction()
    {
        return this.transferFunction;
    }
    
    /**
        * Set input function for this neuron
        * 
        * @param inputFunction
        *           input function for this neuron
    */
    public void setInputFunction(InputFunction inputFunction)
    {
        this.inputFunction = inputFunction;
    }
    
    /**
        * Get input function for this neuron
        * 
        * @return InputFunction
    */
    public InputFunction getInputFunction()
    {
        return this.inputFunction;
    }
    
    /**
    * Calculate neuron output
    */
    
    public void calculateNeuronOutput()
    {
        if(this.inputConnections.size() > 0)
            this.inputValue = this.inputFunction.getFunctionOutput(inputConnections);
         
        this.outputValue = this.transferFunction.getFunctionOutput(inputValue);
    }
    
    
    /**
    *   Set training data buffer
    * @param trainingData training data buffer
    */
    public void setTrainingData(Object trainingData)
    {
        this.trainingData = trainingData;
    }
    
    /**
    * Get training data buffer
    * 
    * @return training data buffer
    */
    public Object getTrainingData()
    {
        return this.trainingData;
    }
    
    public void setNodeDelta(double value)
    {
        this.nodeDelta = value;
    }
    
    /**
    * Get node delta (neuron gradient)
    * 
    * @return node delta (neuron gradient)
    */
    public double getNodeDelta()
    {
        return this.nodeDelta;
    }
       
    /**
     * Randomize neuron's weights
     */
    public void randomizeWeights()
    {
        for(Connection connection : inputConnections)
            connection.setWeight(RandomUtils.nextGaussian());
    }
    
}
