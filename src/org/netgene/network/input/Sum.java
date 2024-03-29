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

package org.netgene.network.input;

import java.io.Serializable;
import java.util.List;
import org.netgene.network.connection.Connection;

/**
 * Sumation input function 
 * 
 * @author CBaba
 */
public class Sum implements InputFunction, Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a new Sum Input Function
     */
    public Sum() {}

    @Override
    public double getFunctionOutput(List<Connection> connectionList) 
    {
        double sum = 0.0;
        for(Connection connection : connectionList)
            sum += connection.getWeight() * connection.getFromNeuron().getOutputValue();
        return sum;
    }
    
}
