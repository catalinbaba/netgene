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

package org.netgene.network.training.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  This class represents a collection of DataRow used for training a neural network 
 * 
 * @author CBaba
 */
public class DataSet implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final ArrayList<DataRow> dataSet;
    
     /**
     * Creates an instance of DataSet
     */
    public DataSet()
    {
        dataSet = new ArrayList();
    }
    
    /**
     * Add a new data raw
     * 
     * @param data data row array
     */
    public void addDataRow(Double data[])
    {
        dataSet.add(new DataRow(data));
    }
    
     /**
     * Get a data raw array at the specified position in this DataSet.
     * 
     * @param index at the specified position in this DataSet.
     * 
     * @return data raw array
     */
    public Double[] getDataRow(int index)
    {
        return dataSet.get(index).getData();
    }
    
    /**
     * Returns the number of data rows in this data set.
     * 
     * @return number of data rows
     */
    public int size()
    {
        return dataSet.size();
    }
    
    /**
     * Removes all the data rows from this data set
     */
    public void clear()
    {
        dataSet.clear();
    }
}
