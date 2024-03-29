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

package org.netgene.network.layer;

import java.io.Serializable;
import org.netgene.network.neuron.Neuron;
import java.util.ArrayList;
import java.util.stream.Stream;

/*
* All of neurons will be aranged in layers 
* Layer object contanis Neuron ojbects
*/


public class Layer implements Serializable
{
    private final ArrayList<Neuron> layer;

    /**
    *  Layer holds a list of Neurons
    */  
    public Layer()
    {
        this.layer = new ArrayList();
    }
    
    /**
     * Add a neuron to this layer
     * 
     * @param neuron to be added
     */
    public void addNeuron(Neuron neuron)
    {
        layer.add(neuron);
    }
    
    /**
     * Returns the neuron at the specified position in this layer.
     * 
     * @param index of the neuron to return
     * 
     * @return the neuron at the specified position in this layer
     */
    public Neuron getNeuron(int index) 
    {
       return layer.get(index);
    }
     
    /**
     * Returns the number of neurons in this layer
     * 
     * @return the number of neurons in this layer
     */
    public int size()
    {
        //return the number of neuron
        return layer.size();
    }
    
    /**
     * Returns a sequential Stream with this Layer as its source.
     * 
     * @return a sequential Stream over the Neurons in this Layer
     */
    public Stream<Neuron> stream()
    {
        return layer.stream();
    }
    
    /**
     * Returns a parallel Stream with this Layer as its source.
     * 
     * @return a parallel Stream over the Neurons in this Layer
     */
    public Stream<Neuron> parallelStream()
    {
        return layer.parallelStream();
    }
    
    /**
     * Returns a list of neurons from this Layer
     * 
     * @return list of neurons from this Layer
     */
    public ArrayList<Neuron> getNeurons() {
        return layer;
    }
}