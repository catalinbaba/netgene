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

import java.io.Serializable;
import java.util.ArrayList;
import org.netgene.network.stop.StopCondition;

/**
 *
 * @author CBaba
 */
abstract public class IterativeLearning extends LearningAlgorithm implements Serializable
{
    private static final long serialVersionUID = 1L;
     
    protected long maxInterations = Long.MAX_VALUE;
    
    protected transient long currentIteration = 0;
    
    protected StopCondition maxIterationStop;
    
    protected ArrayList<StopCondition> stopConditions;
    
    protected IterativeLearning()
    {
       stopConditions = new ArrayList();
    }
    
    /**
     * Set maximum iteration for the training process
     * 
     * @param maxIterations maximum number of iteration 
     */
    public void setMaxIterations(int maxIterations)
    {
       this.maxInterations = maxIterations;
       setIterationLimited(true);
    }
   
    /**
     * Get maximum iteration for the training process
     * 
     * @return  maximum number of iteration 
     */
    public long getMaxIterations()
    {
       return this.maxInterations;
    }
    
    protected void setIterationLimited(boolean generationLimited)
    {
        maxIterationStop = (result) ->
        {
            if(currentIteration == getMaxIterations())
                return true;
            else
               return false;
        };
        this.stopConditions.add(maxIterationStop);
    }
    
    abstract protected void learn();
}
