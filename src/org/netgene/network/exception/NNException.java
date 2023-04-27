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

package org.netgene.network.exception;

/**
 * Neural Network Exception
 * 
 * @author CBaba
 */
public class NNException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message the detail message
     */
    public NNException(final String message)
    {
        super(message);
    }
}
