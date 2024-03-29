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

package org.netgene.network.learning.data;

/**
 *
 * @author CBaba
 */
public class ExpConnectionData extends ConnectionData 
{
    private double exponentialV;
    private double exponentialS;
    private double exponentialS2;

    public double getExponentialV() {
        return exponentialV;
    }

    public double getExponentialS() {
        return exponentialS;
    }

    public void setExponentialV(double exponentialV) {
        this.exponentialV = exponentialV;
    }

    public void setExponentialS(double exponentialS) {
        this.exponentialS = exponentialS;
    }
    
    public void setExponentialS2(double exponentialS2) {
        this.exponentialS2 = exponentialS2;
    }

    public double getExponentialS2() {
        return exponentialS2;
    }
    
}
