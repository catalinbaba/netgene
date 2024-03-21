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

import java.util.Random;


/**
 * Utility library that supplements the standard Random class.
 * 
 * @author CBaba
 */
public class RandomUtils 
{
    private final static Random random = new Random();
    
    /**
     * Returns the next pseudorandom, uniformly distributed int value from this random number generator's sequence.
     * 
     * @return the random integer
     */
    public static int nextInt()
    {
        return random.nextInt();
    }
    
     /**
     *  Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) 
     *  and the specified value (exclusive), drawn from this random number generator's sequence.
     * 
     * @param bound bound - the upper bound (exclusive). Must be positive.
     * 
     * @return the random integer
     */
    public static int nextInt(int bound) 
    {
        return random.nextInt(bound);
    }
    
    
     /**
     *  Returns a pseudorandom, uniformly distributed int value between an specified value (inclusive) 
     *  and another specified value (exclusive), drawn from this random number generator's sequence.
     * 
     * @param rangeMin inclusive value
     * 
     * @param rangeMax exclusive value
     * 
     * @return the random integer
     * 
     * @throws IllegalArgumentException for illegal range
     */
    public static int nextInt(final int rangeMin, final int rangeMax) throws IllegalArgumentException
    {
        if(rangeMin > rangeMax)
        {
            throw new IllegalArgumentException("rangeMin value cannot be greather than rangeMax value");
        }
        return rangeMin + random.nextInt(rangeMax - rangeMin);
    }
    
    /**
     * Returns the next pseudorandom, uniformly distributed double value between 0.0 and 1.0 from this random number generator's sequence.
     * 
     * @return the random double
     */
    public static double nextDouble()
    {
        return random.nextDouble();
    }
    
     /**
     *  Returns a pseudorandom, uniformly distributed int value between 0.0 (inclusive) 
     *  and the specified value (exclusive), drawn from this random number generator's sequence.
     * 
     * @param bound bound - the upper bound (exclusive). Must be positive.
     * 
     * @return the random double
     * 
     * @throws IllegalArgumentException if rangeMax value is negative
     */
    public static double nextDouble(final double bound) throws IllegalArgumentException
    {
        if(bound < 0)
        {
            throw new IllegalArgumentException("rangeMax vlaue cannot be negative");
        }
        return bound * random.nextDouble();
    }
    
     /**
     *  Returns a pseudorandom, uniformly distributed double value between an specified value (inclusive) 
     *  and another specified value (exclusive), drawn from this random number generator's sequence.
     * 
     * @param rangeMin inclusive value
     * 
     * @param rangeMax exclusive value
     * 
     * @return the random double
     * 
     * @throws IllegalArgumentException if rangeMin is greater that rangeMax or when rangeMin is negative
     */
    public static double nextDouble(final double rangeMin, final double rangeMax) throws IllegalArgumentException
    {
        if(rangeMin > rangeMax)
        {
            throw new IllegalArgumentException("rangeMin value cannot be greather than rangeMax value");
        }
        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();
    }
    
    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed double value with mean 0.0 
     * and standard deviation 1.0 from this random number generator's sequence.
     *
     * @return the next pseudorandom, Gaussian ("normally") distributed double value with mean 0.0 and standard deviation 1.0 from this random number generator's sequence
     */
    public static double nextGaussian()
    {
        return random.nextGaussian();
    }
    
    
    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed double value with mean 0.0 
     * and provided standard deviation from this random number generator's sequence.
     *
     * @param sigma standard deviation
     * 
     * @return the next pseudorandom, Gaussian ("normally") distributed double value with mean 0.0 and standard deviation 1.0 from this random number generator's sequence
     */
    public static double nextGaussian(double sigma)
    {
        return random.nextGaussian() * sigma;
    }
    
    /**
     * Returns the next pseudorandom, uniformly distributed boolean value from this random number generator's sequence.
     * 
     * @return the next pseudorandom, uniformly distributed boolean value from this random number generator's sequence
     */
    public static boolean nextBoolean()
    {
        return random.nextBoolean();
    }
    
}
