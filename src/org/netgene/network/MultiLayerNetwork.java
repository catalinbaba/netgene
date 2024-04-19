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

package org.netgene.network;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import org.netgene.network.input.InputFunction;
import org.netgene.network.neuron.BiasNeuron;
import org.netgene.network.neuron.Neuron;
import org.netgene.network.transfer.TransferFunction;
import org.netgene.network.connection.Connection;
import org.netgene.network.layer.Layer;
import org.netgene.network.exception.NNException;
import org.netgene.network.util.LayerList;


/**
 *
 * @author Catalin Baba
 */
public class MultiLayerNetwork extends NeuralNetwork implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Layer list
    protected LayerList layers;
      
    public MultiLayerNetwork()
    {
        layers = new LayerList();
    }
        
      
    /**
     * Create a specific connection between two neurons
     * @param fromLayerNumber
     *       Specify the layer from where the connection starts
     * @param fromNeuronNumber
     *       Specify the neuron from where the connection starts
     * @param toLayerNumber
     *        Specify the layer where the connection ends
     * @param toNeuronNumber
     *        Specify the neuron where the connection ends
     * 
     * @throws NNException if Layer/Neuron is not created 
    */
    
    public void setConnection(int fromLayerNumber, int fromNeuronNumber, int toLayerNumber, int toNeuronNumber) throws NNException
    {
        if(fromLayerNumber > layers.size())
        {
            throw new NNException("Layer " + fromLayerNumber + "is not created");
        }
        if(fromLayerNumber == layers.size()-1)
        {
            throw new NNException("Cannot create output connection for output neuron");
        }
        if(toLayerNumber > layers.size())
        {
            throw new NNException("Layer " + toLayerNumber + "is not created");
        }
        if(toLayerNumber == 0)
        {
            throw new NNException("Cannot create input connection for input neuron");
        }
        Neuron fromNeuron = layers.getLayer(fromLayerNumber).getNeuron(fromNeuronNumber);
        Neuron toNeuron = layers.getLayer(toLayerNumber).getNeuron(toNeuronNumber);
         
        Connection connection = new Connection(fromNeuron, toNeuron);
                    
        fromNeuron.addOutputConnection(connection);
        toNeuron.addInputConnection(connection);
    } 
    
    /**
    * Set Transfer function for all neurons in this neural network
    *
    * @param transferFunction
    *       Transfer function to be set for all neurons in the specified layer
    * 
    */
    public void setNetworkTransferFunction(TransferFunction transferFunction)
    {
        verifyIsNotNull(transferFunction);
        layers.stream().flatMap(layer -> layer.stream()).forEach(neuron -> neuron.setTransferFunction(transferFunction));
    }
    
    /**
    *    Set Transfer function for all neurons in a specific layer
    * 
    * @param layerNum
    *       Layer number
    * @param transferFunction
    *       Transfer function to be set for all neurons in the specified layer
    * 
    * @throws NNException if the Layer is not created
    * 
    */
    public void setLayerTransferFunction(int layerNum, TransferFunction transferFunction) throws NNException
    {   
        verifyIsNotNull(transferFunction);
        if(layerNum < layers.size() && layerNum > 0)
        {   
            Layer layer = layers.getLayer(layerNum);
            for(int i=0; i<layer.size(); i++)
                layer.getNeuron(i).setTransferFunction(transferFunction);
        }
        else
            throw new NNException("Layer number " + layerNum + " does not exist");
    }
    
    /**
    *    Set Transfer function for a specific neuron
    * 
    * @param layerNum
    *       Layer number
    * @param neuronNum
    *       Neuron number in the specified layer
    * @param transferFunction
    *       Transfer function for each neuron
    * 
    * @throws NNException if the Layer/Neuron is not created
    * 
    */
    public void setNeuronTransferFunction(int layerNum, int neuronNum, TransferFunction transferFunction) throws NNException
    {
        if(layerNum < layers.size() && layerNum > 0)
        {
            Layer layer = layers.getLayer(layerNum);
            if(neuronNum < layer.size() && neuronNum >0)
                layer.getNeuron(neuronNum).setTransferFunction(transferFunction);
            else
                throw new NNException("Neuron " + neuronNum+ " in layer " + layerNum + " is not created");
        }
        else
            throw new NNException("Layer number " + layerNum + " does not exist");
    }
    
    /**
    * Set Input function for all neurons in this neural network
    *      
    * @param inputFunction
    *       Input function to be set for all neurons in the specified layer
    * 
    */
    public void setNetworkInputFunction(InputFunction inputFunction)
    {
        verifyIsNotNull(inputFunction);
        layers.stream().flatMap(layer -> layer.stream()).forEach(neuron -> neuron.setInputFunction(inputFunction));
    }
    
    /**
    *    Set Input function for all neurons in a specific layer
    * 
    * @param layerNum
    *       Layer number
    * @param inputFunction
    *       Input function to be set for all neurons in the specified layer
    * 
    * @throws NNException if the Layer is not created
    * 
    */
    public void setLayerInputFunction(int layerNum, InputFunction inputFunction) throws NNException
    {
        verifyIsNotNull(inputFunction);
        if(layerNum < layers.size() && layerNum > 0)
        {
            Layer layer = layers.getLayer(layerNum);
            for(int i=0; i<layers.getLayer(layerNum).size(); i++)
                layer.getNeuron(i).setInputFunction(inputFunction);
        }
        else
            throw new NNException("Layer number " + layerNum + " does not exist");
    }
    
    /**
    * Set Input function for a specific neuron
    *
    * @param layerNum
    *       Layer number
    * @param neuronNum
    *       Neuron number in the specified layer
    * @param inputFunction
    *       Input function to be set for all neurons in the specified layer
    * 
    * @throws NNException if the Neuron is not created
    * 
    */
    public void setNeuronInputFunction(int layerNum, int neuronNum, InputFunction inputFunction) throws NNException
    {
        verifyIsNotNull(inputFunction);
        if(layerNum < layers.size())
        {
            Layer layer = layers.getLayer(layerNum);
            if(neuronNum < layer.size() && neuronNum >0)
                layer.getNeuron(neuronNum).setInputFunction(inputFunction);
            else
                throw new NNException("Neuron " + neuronNum+ " in layer " + layerNum + " is not created");
        }
        else
            throw new NNException("Layer number " + layerNum + " does not exist");
    }
    
    /**
    * Returns true if this neural network contains no layers.
    *
    * @return true if this neural network contains no layers
    *
    */
    public boolean isEmpty() 
    {
        return layers.size()==0;
    }
    
     /**
     * Calculate the neural network output 
     * 
     * @param inputData
     *   input data array
     * 
     * @return the output of the neural network
     *   
     */  
    @Override
    public Double[] getNetworkOutput(Double[] inputData)
    {
        //1. assign (latch) the output values for the input neurons
        for(int i = 0; i < inputData.length; i++)
            if(!(getInputLayer().getNeuron(i) instanceof BiasNeuron)) //if is not BiasNeuron
                getInputLayer().getNeuron(i).setOutputValue(inputData[i]);
        
        //2. forward propragation for hidden layers an output layer
        layers.stream().skip(1).flatMap(layer -> layer.stream()).forEach(neuron -> neuron.calculateNeuronOutput());
         
        Double outputValue[] = new Double[getOutputLayer().size()];
        
        //3. get neural network output
        for(int neuronNum = 0; neuronNum < getOutputLayer().size(); neuronNum++)
            outputValue[neuronNum] = getOutputLayer().getNeuron(neuronNum).getOutputValue();
         
        return outputValue;
    }  
    
     /**
     * Returns the output Layer in this neural network.
     * 
     * @return the output Layer in this neural network.
     *   
     */    
    @Override
    public Layer getOutputLayer()
    {
        return layers.getOutputLayer();
    }
    
    /**
     * Returns the input Layer in this neural network.
     * 
     * @return the input Layer in this neural network.
     *   
     */     
    @Override
    public Layer getInputLayer()
    {
        return layers.getInputLayer();
    }
            
    /**
     * Returns the Layer at the specified position in this neural network.
     * 
     * @param index
     *  index of the Layer to return
     * 
     * @return the Layer at the specified position in this neural network
     *   
     */        
    @Override
    public Layer getLayer(int index)
    {
        return layers.getLayer(index);
    }
    
    
    /**
     * Returns the number of layers in this neural network.
     * 
     * @return the number of layers in this neural network
     *   
     */
    @Override
    public int size()
    {
        return layers.size();
    }
    
    /**
     * Get a stream of the neural network
     * 
     * @return parallel stream
     *   
     */
    @Override
    public Stream<Layer> stream()
    {
        return layers.stream();
    }
    
    /**
     * Get a parallel stream of the neural network
     * 
     * @return parallel stream
     *   
     */
    @Override
    public Stream<Layer> parallelStream()
    {
        return layers.parralelStream();
    }
    
    /**
     * Get Neural Network weights
     * 
     * @return weights array
     *
     */
    @Override
    public Double[] getNetworkWeights()
    {
        List<Double> weightsList = layers.stream().skip(1).flatMap(layer -> layer.stream()).flatMap(neuron -> neuron.getInputConnectionList().stream())
                .map(connection -> 
                {
                    return connection.getWeight();
                }).collect(Collectors.toList());
        
        Double [] weights = new Double[weightsList.size()];
        weights = weightsList.toArray(weights);
       
        return weights;
    }
    
    /**
     * Set Neural Network weights
     * 
     * @param weightsArray
     *  Weights array
     * 
     * @throws NNException if cannot set weight array
     * 
     */
    @Override
    public void setNetworkWeights(double weightsArray[]) throws NNException
    {
        List<Connection> connectionsList = layers.stream().skip(1).flatMap(layer -> layer.stream()).flatMap(neuron -> neuron.getInputConnectionList().stream()).collect(Collectors.toList());
        for(int i=0; i<connectionsList.size(); i++)
            connectionsList.get(i).setWeight(weightsArray[i]);
    }
    
    /**
     * Save Neural Network in a file
     * 
     * @param filename
     *  Filename from where the Neural Network will be saved
     * 
     * @throws FileNotFoundException if the file cannot be created
     * 
     * @throws IOException if the NeuralNetwork cannot be saved
     * 
     */
    public void saveNetwork(String filename) throws IOException
    {
        File fout = new File(filename);
	FileOutputStream fos = new FileOutputStream(fout);
 	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        Double weights[] = getNetworkWeights();
 
	for (int i = 0; i < weights.length; i++) {
		bw.write("" + weights[i]);
		bw.newLine();
	}
 
	bw.close();
    }
    
    /**
     * Load Neural Network from file
     * 
     * @param filename
     *  Filename from where the Neural Network will be loaded
     * 
     * @throws java.io.FileNotFoundException if the file is not found
     * 
     * @throws org.netgene.network.exception.NNException if the saved neural network cannot be loaded
     * 
     */
    public void loadNetwork(String filename) throws FileNotFoundException, IOException, NNException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        LinkedList<Double> weightsList = new LinkedList();
                
        String weight;
        
        while ((weight = reader.readLine()) != null) 
        {
            weightsList.add(Double.parseDouble(weight));
        }
        
        reader.close();
        
        double weights[] = new double[weightsList.size()];
        
        for(int i=0; i<weightsList.size(); i++)
           weights[i] = weightsList.get(i);
           
        setNetworkWeights(weights);
    }
    
    /**
     * Get all layers from the neural network
     * 
     * @return ArrayList of Layers
     * 
     */
    @Override
    public ArrayList<Layer> getLayers() 
    {
        return layers.getLayerList();
    }
    
    private LayerList getLayerList()
    {
        return layers;
    }
        
    private void setFullConnectivity() throws NNException
    {
        if(layers.size() <2)
        {
             throw new NNException("Network must have at least two layers to create connections");
        }
        for(int layerNum = 0; layerNum < layers.size()-1; layerNum++) //for each layer 
            for(int neuronNum = 0; neuronNum <layers.getLayer(layerNum).size(); neuronNum++) //for each neuron in the layer
                for(int toNeuronNum = 0; toNeuronNum <layers.getLayer(layerNum + 1).size(); toNeuronNum++)
                {
                    Neuron fromNeuron = layers.getLayer(layerNum).getNeuron(neuronNum);
                    Neuron toNeuron = layers.getLayer(layerNum + 1).getNeuron(toNeuronNum);
                    
                    Connection connection = new Connection(fromNeuron, toNeuron);
                    
                    fromNeuron.addOutputConnection(connection);
                    toNeuron.addInputConnection(connection);
                }
    }
    
    /**
    *  Add a new layer specifying how many neurons will have
    * 
    *  @param size
    *       Specify how many neurons the layer will have
    * 
    * @return MultiLayerNetwork Builder
    *      
    */ 
    public MultiLayerNetwork addLayer(int size)
    {
        if(size < 1)
            throw new NNException("Size cannot be less than 1");
        getLayerList().addLayer(new Layer());
        for(int i = 0; i < size; i++)
            getLayerList().getOutputLayer().addNeuron(new Neuron());
        return this;
    }
    
    /**
    *  Add a new layer specifying how many neurons will have and the transfer function
    * 
    * @param size
    *   Specify how many neurons the layer will have
    * 
    * @param transferFunction
    *   Set the transfer function for all neurons in that layer
    * 
    * @return MultiLayerNetwork Builder
    *      
    */ 
    public MultiLayerNetwork addLayer(int size, TransferFunction transferFunction)
    {
        verifyIsNotNull(transferFunction);
        if(size < 1)
            throw new NNException("Size cannot be less than 1");
        getLayerList().addLayer(new Layer());
        for(int i = 0; i < size; i++)
            getLayerList().getOutputLayer().addNeuron(new Neuron(transferFunction));
        return this;
    }
    
    /**
    *  Add a new layer specifying how many neurons will have and the input function
    * 
    *  @param size
    *       Specify how many neurons the layer will have
    * 
    *  @param inputFunction
    *       Set the input function for all neurons in this layer
    * 
    * @return MultiLayerNetwork Builder
    *      
    */ 
    public MultiLayerNetwork addLayer(int size, InputFunction inputFunction)
    {
        verifyIsNotNull(inputFunction);
        if(size < 1)
            throw new NNException("Size cannot be less than 1");
        getLayerList().addLayer(new Layer());
        for(int i = 0; i < size; i++)
            getLayerList().getOutputLayer().addNeuron(new Neuron(inputFunction));
        return this;
    }
    
    /**
    *  Add a new layer specifying how many neurons will have, the transfer function and the input function
    * 
    * @param size
    *   Specify how many neurons the layer will have
    * 
    * @param transferFunction
    *   Set the transfer function for all neurons in that layer
    * 
    * @param inputFunction
    *   Set the input function for all neurons in that layer
    * 
    * @return MultiLayerNetwork Builder
    *       
    */ 
    public MultiLayerNetwork addLayer(int size, TransferFunction transferFunction, InputFunction inputFunction)
    {   
        verifyIsNotNull(inputFunction);
        verifyIsNotNull(transferFunction);
        if(size < 1)
            throw new NNException("Size cannot be less than 1");
        getLayerList().addLayer(new Layer());
        for(int i = 0; i < size; i++)
            getLayerList().getOutputLayer().addNeuron(new Neuron(transferFunction,inputFunction));
        return this;
    }
        
    /**
    * Add a created neuron to a specific layer
    * 
    * @param layerNumber
    *   Layer number where the neuron will be added
    *   
    * @param neuron
    *   Neuron to be added
    * 
    * @return MultiLayerNetwork Builder
    * 
    * @throws NNException if the Layer is not created
    *      
    */
    
    public MultiLayerNetwork addNeuronToLayer(int layerNumber, Neuron neuron) throws NNException
    {
        verifyIsNotNull(neuron);
        if(layerNumber > getLayerList().size())
            throw new NNException("Layer " + layerNumber + "is not created");
        getLayerList().getLayer(layerNumber).addNeuron(neuron);
        return this;
    }
    
    /**
    * Create and add a bias neuron to a specific layer
    * 
    * @param layerNumber
    *   Layer number where the neuron will be added
    * 
    * @return MultiLayerNetwork Builder
    * 
    * @throws NNException if the Layer is not created
    *       
    */
    
    public MultiLayerNetwork addBiasNeuronToLayer(int layerNumber) throws NNException
    {
        if(layerNumber > getLayerList().size())
            throw new NNException("Layer " + layerNumber + "is not created");
        if(layerNumber == getLayerList().size() - 1)
            throw new NNException("Cannot add bias neuron to output layer");
        getLayerList().getLayer(layerNumber).addNeuron(new BiasNeuron());
        return this;
    }
    
    /**
    * Add bias neurons to all layers except output layer
    * 
    * @return MultiLayerNetwork Builder
    *      
    */
    public MultiLayerNetwork addBiasNeurons()
    {
        for(int layerNum = 0; layerNum <getLayerList().size()-1; layerNum++) //do not add bias neuron to output layer
            getLayerList().getLayer(layerNum).addNeuron(new BiasNeuron());
        return this;
    }
        
    /**
    * Build the Multi Layer Neural Network
    * 
    * @param connectNeurons
    *    Create a fully connected neural network. If the value is false a MultiLayerNetwork with no connection is build and the user should manually set the connections.
    * 
    * @return MultiLayerNetwork 
    * 
    * @throws NNException if the MultiLayerNetwork cannot create the connections
    * 
    */
    public MultiLayerNetwork build(boolean connectNeurons) throws NNException
    {
        if(connectNeurons)
            setFullConnectivity();
        return this;
    }
        
    /**
    * Build a fully connected  Multi Layer Neural Network
    * 
    * @return MultiLayerNetwork 
    * 
    * @throws NNException if the MultiLayerNetwork cannot create the connections
    *    
    */
    public MultiLayerNetwork build() throws NNException
    {
        return build(true);
    }
    
    private static <T> T verifyIsNotNull(T obj)
    {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }
    
    
//    public static class Builder
//    {
//        // MultiLayerNetwork to be built
//        
//        public MultiLayerNetwork neuralNetwork;
//        
//        /**
//        * Create a new MultiLayerNetwork builder
//        *
//        */
//        public Builder()
//        {
//           neuralNetwork = new MultiLayerNetwork();
//        }
//        
//        /**
//        *  Add a new layer specifying how many neurons will have
//        * 
//        *  @param size
//        *       Specify how many neurons the layer will have
//        * 
//        * @return MultiLayerNetwork Builder
//        *      
//        */ 
//    
//        public Builder addLayer(int size)
//        {
//            neuralNetwork.getLayerList().addLayer(new Layer());
//            for(int i = 0; i < size; i++)
//                neuralNetwork.getLayerList().getOutputLayer().addNeuron(new Neuron());
//            return this;
//        }
//    
//        /**
//        *  Add a new layer specifying how many neurons will have and the transfer function
//        * 
//        * @param size
//        *   Specify how many neurons the layer will have
//        * 
//        * @param transferFunction
//        *   Set the transfer function for all neurons in that layer
//        * 
//        * @return MultiLayerNetwork Builder
//        *      
//        */ 
//        public Builder addLayer(int size, TransferFunction transferFunction)
//        {
//            neuralNetwork.getLayerList().addLayer(new Layer());
//            for(int i = 0; i < size; i++)
//                neuralNetwork.getLayerList().getOutputLayer().addNeuron(new Neuron(transferFunction));
//            return this;
//        }
//    
//        /**
//        *  Add a new layer specifying how many neurons will have and the input function
//        * 
//        *  @param size
//        *       Specify how many neurons the layer will have
//        * 
//        *  @param inputFunction
//        *       Set the input function for all neurons in this layer
//        * 
//        * @return MultiLayerNetwork Builder
//        *      
//        */ 
//        public Builder addLayer(int size, InputFunction inputFunction)
//        {
//            neuralNetwork.getLayerList().addLayer(new Layer());
//            for(int i = 0; i < size; i++)
//                neuralNetwork.getLayerList().getOutputLayer().addNeuron(new Neuron(inputFunction));
//            return this;
//        }
//    
//        /**
//        *  Add a new layer specifying how many neurons will have, the transfer function and the input function
//        * 
//        * @param size
//        *   Specify how many neurons the layer will have
//        * 
//        * @param transferFunction
//        *   Set the transfer function for all neurons in that layer
//        * 
//        * @param inputFunction
//        *   Set the input function for all neurons in that layer
//        * 
//        * @return MultiLayerNetwork Builder
//        *       
//        */ 
//        public Builder addLayer(int size, TransferFunction transferFunction, InputFunction inputFunction)
//        {   
//            neuralNetwork.getLayerList().addLayer(new Layer());
//            for(int i = 0; i < size; i++)
//                neuralNetwork.getLayerList().getOutputLayer().addNeuron(new Neuron(transferFunction,inputFunction));
//            return this;
//        }
//        
//        /**
//        * Add a created neuron to a specific layer
//        * 
//        * @param layerNumber
//        *   Layer number where the neuron will be added
//        *   
//        * @param neuron
//        *   Neuron to be added
//        * 
//        * @return MultiLayerNetwork Builder
//        * 
//        * @throws NNException if the Layer is not created
//        *      
//        */
//    
//        public Builder addNeuronToLayer(int layerNumber, Neuron neuron) throws NNException
//        {
//            if(layerNumber > neuralNetwork.getLayerList().size())
//                throw new NNException("Layer " + layerNumber + "is not created");
//            neuralNetwork.getLayerList().getLayer(layerNumber).addNeuron(neuron);
//            return this;
//        }
//    
//        /**
//        * Create and add a bias neuron to a specific layer
//        * 
//        * @param layerNumber
//        *   Layer number where the neuron will be added
//        * 
//        * @return MultiLayerNetwork Builder
//        * 
//        * @throws NNException if the Layer is not created
//        *       
//        */
//    
//        public Builder addBiasNeuronToLayer(int layerNumber) throws NNException
//        {
//            if(layerNumber > neuralNetwork.getLayerList().size())
//                throw new NNException("Layer " + layerNumber + "is not created");
//            if(layerNumber == neuralNetwork.getLayerList().size() - 1)
//                throw new NNException("Cannot add bias neuron to output layer");
//           neuralNetwork.getLayerList().getLayer(layerNumber).addNeuron(new BiasNeuron());
//           return this;
//        }
//    
//        /**
//        * Add bias neurons to all layers except output layer
//        * 
//        * @return MultiLayerNetwork Builder
//        *      
//        */
//        public Builder addBiasNeurons()
//        {
//            for(int layerNum = 0; layerNum <neuralNetwork.getLayerList().size()-1; layerNum++) //do not add bias neuron to output layer
//                neuralNetwork.getLayerList().getLayer(layerNum).addNeuron(new BiasNeuron());
//            return this;
//        }
//        
//        /**
//        * Build the Multi Layer Neural Network
//        * 
//        * @param connectNeurons
//        *    Create a fully connected neural network. If the value is false a MultiLayerNetwork with no connection is build and the user should manually set the connections.
//        * 
//        * @return MultiLayerNetwork 
//        * 
//        * @throws NNException if the MultiLayerNetwork cannot create the connections
//        * 
//        */
//        public MultiLayerNetwork build(boolean connectNeurons) throws NNException
//        {
//            if(connectNeurons)
//               neuralNetwork.setFullConnectivity();
//            return neuralNetwork;
//        }
//        
//        /**
//        * Build a fully connected  Multi Layer Neural Network
//        * 
//        * @return MultiLayerNetwork 
//        * 
//        * @throws NNException if the MultiLayerNetwork cannot create the connections
//        *    
//        */
//        public MultiLayerNetwork build() throws NNException
//        {
//           return build(true);
//        }
//        
//    }
}
