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

package org.netgene.network.learning.optimizer;

import java.io.Serializable;
import org.netgene.network.connection.Connection;
import org.netgene.network.learning.data.ResilientConnectionData;

/**
 * The classic RPROP algorithm.
 * 
 * @author CBaba
 */
public class RPROPp extends RPROP implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new RPROPp optimizer
     */
    public RPROPp() {}
    
    @Override
    public double calculateWeightChanges(Connection connection)
    {
        double weightGradient = ((ResilientConnectionData)(connection.getTrainingData())).getWeightGradient();
        double updateValue = ((ResilientConnectionData)(connection.getTrainingData())).getUpdateValue();
        double prevWeightGradient = ((ResilientConnectionData)(connection.getTrainingData())).getPrevWeightGradient();
        double weightChange = ((ResilientConnectionData)(connection.getTrainingData())).getWeightChange();
        
        double signum = Math.signum(weightGradient * prevWeightGradient);
        
        if(signum > 0 )
        {
            updateValue = Math.min(updateValue * getIncreaseFactor(), getMaxUpdateValue());
            weightChange = -Math.signum(weightGradient) *updateValue;
            ((ResilientConnectionData)(connection.getTrainingData())).setUpdateValue(updateValue);
            ((ResilientConnectionData)(connection.getTrainingData())).setWeightChange(weightChange);
        }
        else if(signum <0)
        {
            updateValue = Math.max(updateValue * getDecreaseFactor(), getMinUpdateValue());
            weightChange = -((ResilientConnectionData)(connection.getTrainingData())).getWeightChange();
            weightGradient = 0.0;
            ((ResilientConnectionData)(connection.getTrainingData())).setWeightChange(weightChange);
            ((ResilientConnectionData)(connection.getTrainingData())).setUpdateValue(updateValue);
        }
        else
        {
            weightChange = -Math.signum(weightGradient) * updateValue;
            ((ResilientConnectionData)(connection.getTrainingData())).setWeightChange(weightChange);
        }
        ((ResilientConnectionData)(connection.getTrainingData())).setPrevWeightGradient(weightGradient);
        ((ResilientConnectionData)(connection.getTrainingData())).setWeightGradient(0.0);
         
        return weightChange;
    }  
}
