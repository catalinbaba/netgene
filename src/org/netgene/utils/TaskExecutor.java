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

package org.netgene.utils;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author cbaba
 */
public class TaskExecutor 
{   
    private final Clock clock;
        
    public TaskExecutor(final Clock clock)
    {
        this.clock = clock;
    }
    
    public Duration runTask(Task task, ForkJoinPool executor) 
    {
        Instant start = Instant.now(clock);
        try {
            executor.submit(() ->
            {
                task.execute();
            }).get();
        } catch (InterruptedException | ExecutionException ex) {
            //throw new NNException("Exception task - SEVERE!");
        }
        Instant finish = Instant.now(clock);
        return Duration.between(start, finish);
    }
  
}
