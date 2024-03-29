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

package org.netgene.network.learning.data;

import org.netgene.network.learning.data.ConnectionData;

/**
 *
 * @author CBaba
 */
public class ResilientConnectionData extends ConnectionData
{
    private double prevWeightGradient = 0.0; //default weight gradient
    private double updateValue = 0.0; //default update value;
      
    public void setPrevWeightGradient(double prevWeightGradient)
    {
        this.prevWeightGradient = prevWeightGradient;
    }
    
    public double getPrevWeightGradient()
    {
        return this.prevWeightGradient;
    }
    
    public void setUpdateValue(double updateValue)
    {
        this.updateValue = updateValue;
    }
    
    public double getUpdateValue()
    {
        return this.updateValue;
    }
}
