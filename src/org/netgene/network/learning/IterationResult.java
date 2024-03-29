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

package org.netgene.network.learning;

import org.netgene.network.training.data.DataSet;

/**
 *
 * @author CBaba
 */
public class IterationResult 
{
    private final DataSet outputDataSet;
    private final double outputError;
    private final long currentIteration;
    
    protected IterationResult(DataSet outputDataSet, double outputError, long currentIteration)
    {
        this.outputDataSet = outputDataSet;
        this.outputError = outputError;
        this.currentIteration = currentIteration;
    }
    
    public DataSet getDataSet()
    {
        return outputDataSet;
    }
    
    public double getOutputError()
    {
        return outputError;
    }
    
    public long getCurrentIteration()
    {
        return currentIteration;
    }
}
