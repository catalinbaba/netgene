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

import java.util.ArrayList;
import java.util.stream.Stream;
import org.netgene.network.exception.NNException;
import org.netgene.network.layer.Layer;

/**
 *
 * @author Catalin Baba
 */
public abstract class NeuralNetwork 
{
    public abstract Layer getOutputLayer();
    
    public abstract Layer getInputLayer();
    
    public abstract Layer getLayer(int index);
    
    public abstract int size();
    
    public abstract Double[] getNetworkOutput(Double[] inputData);
    
    public abstract Double[] getNetworkWeights();
    
    public abstract void setNetworkWeights(double weightsArray[]) throws NNException;
    
    public abstract Stream<Layer> stream();
    
    public abstract Stream<Layer> parallelStream();
    
    public abstract ArrayList<Layer> getLayers();
    
}
