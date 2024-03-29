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

package org.netgene.ga;

import java.io.Serializable;
import java.time.Clock;
import org.netgene.ga.crossover.CrossoverOperator;
import org.netgene.ga.crossover.OnePointCrossover;
import org.netgene.ga.exception.GaException;
import org.netgene.ga.mutator.GaussianMutator;
import org.netgene.ga.mutator.MutatorOperator;
import org.netgene.ga.selection.parent.RouletteSelector;
import org.netgene.ga.selection.parent.ParentSelector;
import org.netgene.utils.ConsolePrinter;
import org.netgene.utils.Printer;

/**
 * This class can be used to incrementally construct a GeneticAlgorithm.
 * 
 * @author Catalin Baba
 */
public final class GeneticConfiguration implements Serializable
{   
    private static final long serialVersionUID = 1L;
    
    /*
    default crossover rate
    */
    private double crossoverRate = 0.8;
    
    /*
    default mutation rate
    */
    private double mutationRate = 0.05;
    
    /*
    default value for elitism is true
    */
    private boolean elitism = true;
    
    /*
    default elitism size is 1
    */
    private int elitismSize = 1;
    
    /*
    default value for the population size is 1
    */
    private int populationSize = 1;
    
    /*
    maximum generation to run
    */
    private int maxGeneration = Integer.MAX_VALUE;  
    
    /*
        Parent selection algorithm - default value is Roulette Selector
    */
    private ParentSelector parentSelector = new RouletteSelector();
    
    /*
        Default value is null - if not instantiated it will not be used
    */
    //private Selector offspringSelector;

    /*
        CrossoverOperator algorithm - default value is OnePointCrossover
    */
    private CrossoverOperator crossoverOperator = new OnePointCrossover();

    /*
        MutatorOperator algorithm - default value is gaussian mutatorOperator
    */
    private MutatorOperator mutatorOperator = new GaussianMutator();
    
    /*
        Creates a thread pool that reuses a fixed number of threads 
    */
    private int nThreads = Math.max(1, Runtime.getRuntime().availableProcessors() > 1 ? Runtime.getRuntime().availableProcessors() - 1 : 1);
    
    /*
        Clock used to count how long a task executes 
    */
    private Clock clock =  Clock.systemUTC();
    
    /*
        Console printer used in debug mode 
    */
    private Printer printer = new ConsolePrinter();
    
    /*
        Target Fitness to be reached 
    */
    private double targetFitness = Double.MAX_VALUE;
    
    /*
        Is limited by maximum generation
    */
    private boolean generationLimited = false;
    
    /*
        Is limited by target fitness
    */
    private boolean targetFitnessLimited = false;
    
    /*
        Skip crossover step
    */
    private boolean skipCrossover = false;
    
    /*
        Skip mutation step
    */
    private boolean skipMutation = false;
           
   
    /**
     * Genetic Configuration is the builder class for GeneticAlgorithm
     * 
     */
    public GeneticConfiguration() {}
    
    
    /**
     * Set the parent select algorithm for the genetic algorithm
     * 
     * @param parentSelector parent select to be configured
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setParentSelector(final ParentSelector parentSelector)
    {
        this.parentSelector = verifyIsNotNull(parentSelector);
        return this;
    }
    
    /*
     * Set the offspring select algorithm for the genetic algorithm
     * 
     * @param offspringSelector parent select to be configured
     * 
     * @return GeneticConfiguration builder class
     *
    public GeneticConfiguration setOffspringSelector(final Selector offspringSelector)
    {
        this.offspringSelector = offspringSelector;
        return this;
    }
    */
    
    /**
     * Set the crossover operator for the genetic algorithm
     * 
     * @param crossoverOperator crossover operator to be configured
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setCrossoverOperator(final CrossoverOperator crossoverOperator)
    {
        this.crossoverOperator = verifyIsNotNull(crossoverOperator);
        return this;
    }
    
    /**
     * Set the mutation operator for the genetic algorithm
     * 
     * @param mutator mutation operator to be configured
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setMutatorOperator(final MutatorOperator mutator)
    {
        this.mutatorOperator = verifyIsNotNull(mutator);
        return this;
    }
    
     /**
     * Set the crossover rate for the genetic algorithm
     * 
     * @param crossoverRate crossover rate to be configured
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setCrossoverRate(final double crossoverRate)
    {
        if(crossoverRate <= 0)
          throw new GaException("Crossover rate value cannot be negative");
        this.crossoverRate = crossoverRate;
        return this;
    }
    
     /**
     * Set the mutation rate for the genetic algorithm
     * 
     * @param mutationRate mutation rate to be configured
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setMutationRate(final double mutationRate)
    {
        if(mutationRate < 0)
          throw new GaException("Mutation rate value cannot be negative");
        this.mutationRate = mutationRate;
        return this;
    }
    
    /**
     * Enable elitism for the genetic algorithm
     * 
     * @param elitism enable elitism 
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setElitism(final boolean elitism)
    {
        this.elitism = elitism;
        return this;
    }
    
    /**
     * Set elitism size for the genetic algorithm
     * 
     * @param elitismSize elitism size
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setElitismSize(final int elitismSize)
    {
        if(elitismSize < 0)
          throw new GaException("Mutation rate value cannot be negative");
        this.elitismSize = elitismSize;
        return this;
    }
    
    /**
     * Set maximum generation for the genetic algorithm
     * 
     * @param maxGeneration maximum generation
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setMaxGeneration(final int maxGeneration)
    {
        if(maxGeneration <= 0)
          throw new GaException("Maximum generation cannot be lower than 1");
        this.maxGeneration = maxGeneration;
        this.generationLimited = true;
        return this;
    }
    
    /**
     * Set the maximum number of threads to be used in the evolution
     * 
     * @param nThreads set maximum number of threads
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setThreadPoolSize(final int nThreads)
    {
        if(nThreads < 1)
          throw new GaException("Thread Pool Size cannot be lower than 1");
        this.nThreads = nThreads;
        return this;
    }
    
    /**
     * Set the clock used to calculate the evolution duration
     * 
     * @param clock set clock
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setClock(final Clock clock)
    {
        this.clock = verifyIsNotNull(clock);
        return this;
    }
    
     /**
     * Set printer to be used to print results during evolution
     * 
     * @param printer printer
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setPrinter(final Printer printer)
    {
        this.printer = verifyIsNotNull(printer);
        return this;
    }
    
    /**
     * Set target fitness
     * 
     * @param targetFitness target fitness
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration setTargetFitness(final double targetFitness)
    {
        this.targetFitness = targetFitness;
        this.targetFitnessLimited = true;
        return this;
    }
    
     /**
     * Skip crossover
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration skipCrossover()
    {
        this.skipCrossover = true;
        return this;
    }
    
     /**
     * Skip mutation
     * 
     * @return GeneticConfiguration builder class
     */
    public GeneticConfiguration skipMutation()
    {
        this.skipMutation = true;
        return this;
    }
    
    /**
     * Get the GeneticAlgorithm 
     * 
     * @return GeneticAlgorithm
     */
    public GeneticAlgorithm getAlgorithm()
    {
       GeneticAlgorithm ga = new GeneticAlgorithm(parentSelector,
                                   //offspringSelector,
                                   crossoverOperator,
                                   mutatorOperator,
                                   crossoverRate,
                                   mutationRate,
                                   elitism,
                                   elitismSize,
                                   maxGeneration,
                                   clock,
                                   nThreads,
                                   printer,
                                   targetFitness,
                                   skipCrossover,
                                   skipMutation
       );
       ga.setGenerationLimited(generationLimited);
       ga.setFitnessLimited(targetFitnessLimited);
       return ga;
    }
    
    private static <T> T verifyIsNotNull(T obj)
    {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }
    
    
}
