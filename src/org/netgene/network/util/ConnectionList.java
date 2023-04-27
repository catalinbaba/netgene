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

package org.netgene.network.util;

import java.util.ArrayList;
import java.util.stream.Stream;
import org.netgene.network.connection.Connection;

/**
 *
 * @author CBaba
 */
public class ConnectionList 
{
    public ArrayList<Connection> connectionList;
    
    public ConnectionList()
    {
        connectionList = new ArrayList();
    }
    
    public void addConnection(Connection connection)
    {
        connectionList.add(connection);
    }
    
    public void setConnection(int index, Connection connection)
    {
        connectionList.set(index, connection);
    }
    
    public Connection getConnection(int index)
    {
        return connectionList.get(index);
    }
    
       
    public int size()
    {
        return connectionList.size();
    }
    
    public Stream<Connection> stream()
    {
        return connectionList.stream();
    }
    
    public Stream<Connection> parallelStream()
    {
        return connectionList.parallelStream();
    }
}
