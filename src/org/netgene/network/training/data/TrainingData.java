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

/**
 * This class holds the input data set and a target data set used to train a neural network
 * 
 * @author cbaba
 */
public class TrainingData implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final DataSet inputDataSet;
    private final DataSet targetDataSet;
    
    /**
    * Creates an instance of TrainingData
    */
    public TrainingData()
    {
        inputDataSet = new DataSet();
        targetDataSet = new DataSet();
    }
    
    /**
     * Add a new input data raw and a new target data raw
     * 
     * @param inputData input data for the neural network
     * 
     * @param targetData target data of the neural network
     */
    public void addDataRow(Double inputData[], Double[] targetData)
    {
        inputDataSet.addDataRow(inputData);
        targetDataSet.addDataRow(targetData);
    }
    
    /**
     * Get target data set as a DataSet object
     * 
     * @return target data set
     */
    public DataSet getTagetDataSet()
    {
        return targetDataSet;
    }
    
    /**
     * Get target data set as a DataSet object
     * 
     * @return target data set as DataSet object
     */
    public DataSet getInputDataSet()
    {
        return targetDataSet;
    }
    
    /**
     * Get input data set as an array at the specified position in this DataSet
     * 
     * @param index at the specified position in this DataSet
     * 
     * @return input data set as an array
     */
    public Double[] getInputData(int index)
    {
        return inputDataSet.getDataRow(index);
    }
    
    /**
     * Get target data set as an array at the specified position in this DataSet
     * 
     * @param index at the specified position in this DataSet
     * 
     * @return target data set as an array
     */
    public Double[] getTargetData(int index)
    {
        return targetDataSet.getDataRow(index);
    }
    
    /**
     *  Not implemented yet
     */
    public void loadFromFile() 
    {
       throw new java.lang.UnsupportedOperationException("Not supported yet."); 
    }
    
    /**
     *  Not implemented yet
     */
    public void saveToFile()
    {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Returns the number of data rows in this training data.
     * 
     * @return number of data rows
     */
    public int size()
    {
        return inputDataSet.size();
    }
}
