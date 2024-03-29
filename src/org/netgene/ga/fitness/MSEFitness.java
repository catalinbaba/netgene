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

package org.netgene.ga.fitness;

import java.io.Serializable;
import org.netgene.network.error.function.MeanSquaredError;
import org.netgene.network.training.data.DataSet;

/**
 *
 * @author Catalin Baba
 */
public class MSEFitness implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final MeanSquaredError mse;
    private final DataSet targetData;
    
    private double maxFitness = 100;
  
    public MSEFitness(DataSet targetData)
    {
        this.targetData = targetData;
        mse = new MeanSquaredError();
    }
    
    public double getFitness(DataSet output)
    {
        double error = mse.calculateError(output, targetData);
        if (error <= 0.000000001)
            error = 0.0;
        double fitness = (1/(1+error))* maxFitness;  
        return fitness;
    }
    
        
    public double getMaxFitness() {
        return maxFitness;
    }

    public void setMaxFitness(double maxFitness) {
        this.maxFitness = maxFitness;
    }
        
    public void setMaxError(double maxError)
    {
        mse.setMaxError(maxError);
    }
    
    public double getMaxError()
    {
        return mse.getMaxError();
    }
}
