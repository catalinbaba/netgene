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

/**
 * Printer used to print on standard console
 * 
 * @author cbaba
 */
public class ConsolePrinter implements Printer
{
    /**
     * Create a new instance of ConsolePrinters
     */
    public ConsolePrinter(){}
    
    /**
     * Prints a string.
     * 
     * @param message the message to be printed
    */
    @Override
    public void print(String message) 
    {
        System.out.print(message);
    }
    
    /**
     * Prints a String and then terminate the line.
     * 
     * @param message the message to be printed
    */
    @Override
    public void println(String message) 
    {
        System.out.println(message);
    }
    
    /**
     * Terminates the current line by writing the line separator string.
     *
    */
    @Override
    public void println() 
    {
        System.out.println();
    }
}
