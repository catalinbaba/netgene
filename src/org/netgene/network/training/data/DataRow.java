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
 * Stores a row of data - a single example
 * 
 * @author CBaba
 */
public class DataRow implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Double[] dataRow;
    
    /**
     * Creates an instance of DataRow with the specified data row array.
     * 
     * @param dataRow data row
    */
    public DataRow(Double[] dataRow)
    {
        this.dataRow = dataRow;
    }
    
    /**
     * Add a new data raw
     * 
     * @param dataRow data row
     */
    public void addData(Double[] dataRow)
    {
        this.dataRow = dataRow;
    }
      
    /**
     * Get data raw
     * 
     * @return data row array
     */
    public Double[] getData()
    {
        return this.dataRow;
    }
}
