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
 *
 * @author CBaba
 */
public class Max implements InputFunction, Serializable 
{
    private static final long serialVersionUID = 1L;
    
     /**
     * Create a new Min Input Function
     */
    public Max() {}
    
    @Override
    public double getFunctionOutput(List<Connection> connectionList) 
    {
        if(connectionList.size() == 0)
            return 0;
        
        double max = connectionList.get(0).getFromNeuron().getOutputValue() * connectionList.get(0).getWeight();
        for(Connection connection : connectionList)
            max = Math.max(max, connection.getFromNeuron().getOutputValue()*connection.getWeight());
        return max;
    }
}
