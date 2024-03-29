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
import java.time.Clock;
import java.util.concurrent.ForkJoinPool;
import org.netgene.utils.TaskExecutor;

/**
 *
 * @author CBaba
 */
public class LearningAlgorithm implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected final TaskExecutor taskExecutor;
    
    protected final Clock clock;
    
    protected int nThreads = Runtime.getRuntime().availableProcessors();
        
    protected ForkJoinPool threadPool;
    //protected Executor threadPool;
       
    protected LearningAlgorithm()
    {
        threadPool = new ForkJoinPool(nThreads);
        clock = Clock.systemUTC();
        taskExecutor = new TaskExecutor(clock);
    }
}
