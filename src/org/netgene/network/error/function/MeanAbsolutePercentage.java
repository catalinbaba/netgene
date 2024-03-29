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

package org.netgene.network.error.function;

import org.netgene.network.training.data.DataSet;

/**
 *
 * @author CBaba
 */
public class MeanAbsolutePercentage implements ErrorFunction
{
    private double maxError = 0.6;
    
    @Override
    public double calculateError(DataSet outputSet, DataSet targetSet)
    {
        //System.out.println("aici");
        double totalError = 0.0;
        Double output[] = {}; 
        Double target[] = {};
        for(int i=0; i<targetSet.size(); i++)
        {
            output = outputSet.getDataRow(i);
            target = targetSet.getDataRow(i);
            for(int j=0; j<target.length; j++)
            {
                totalError =  totalError + Math.abs((output[j] - target[j])/output[j]);
            }
        }
        return totalError/(targetSet.size());
    }
    
    @Override
    public double getMaxError()
    {
        return this.maxError;
    }
    
    @Override
    public void setMaxError(double maxError)
    {
        this.maxError = maxError;
    }
}
