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

package org.netgene.network.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.netgene.network.layer.Layer;

/**
 *
 * @author CBaba
 */
public class LayerList 
{
    ArrayList<Layer> layerList;
   
    public LayerList()
    {
        layerList = new ArrayList();
    }
    
    public void addLayer(Layer layer)
    {
        layerList.add(layer);
    }
    
    public Layer getLayer(int index)
    {
        return layerList.get(index);
    }
    
    public void setLayer(int index, Layer layer)
    {
        layerList.set(index, layer);
    }
    
    public void setLayers(List<Layer> layers)
    {
        this.layerList = (ArrayList)layers;
    }
    
    public Layer getOutputLayer()
    {
        return layerList.get(layerList.size() - 1);
    }
    
    public Layer getInputLayer()
    {
        return layerList.get(0);
    }
    
    public int size()
    {
        return layerList.size();
    }
    
    public Stream<Layer> stream()
    {
        return layerList.stream();
    }
    
    public Stream<Layer> parralelStream()
    {
        return layerList.parallelStream();
    }
    
    public ArrayList<Layer> getLayerList() {
        return layerList;
    }
}
