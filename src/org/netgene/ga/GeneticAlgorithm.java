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
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Offspring;
import org.netgene.ga.core.Parents;
import org.netgene.ga.core.Population;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.crossover.CrossoverOperator;
import org.netgene.ga.exception.CrossoverException;
import org.netgene.ga.exception.GaException;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.exception.SelectionException;
import org.netgene.ga.fitness.FitnessFunction;
import org.netgene.ga.mutator.MutatorOperator;
import org.netgene.ga.selection.parent.ParentSelector;
import org.netgene.ga.stop.StopCondition;
import org.netgene.utils.Printer;
import org.netgene.utils.RandomUtils;
import org.netgene.utils.Task;
import org.netgene.utils.TaskExecutor;

/**
 *
 * @author Catalin Baba
 */
public class GeneticAlgorithm implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private double crossoverRate;
    
    private final boolean elitism;
    
    private final int elitismSize;
      
    private ParentSelector parentSelector;
    
    //private final Selector offspringSelector;
        
    private CrossoverOperator crossoverOperator;

    public void setCrossoverOperator(CrossoverOperator crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
    }
    
    private MutatorOperator mutatorOperator;

    public void setMutatorOperator(MutatorOperator mutatorOperator) {
        this.mutatorOperator = mutatorOperator;
    }
    
    private final int maxGeneration;
    
    private final Printer printer;
            
    private final Clock clock;
    
    private final int nThreads;
    
    private Population population;
    
    private FitnessFunction fitnessFunction;
   
    private final ForkJoinPool threadPool;
       
    private GenerationTracker generationTracker;
    
    private final TaskExecutor taskExecutor;
    
    private final double targetFitness;
       
    private final ArrayList<StopCondition> stopConditions;
    
    private StopCondition maxGenerationStop;
           
    private StopCondition targetFitnessStop;
    
    private boolean skipCrossover = true;
    
    private boolean skipMutation = false;
        
    protected GeneticAlgorithm(ParentSelector parentSelector,
                            ///sSelector offspringSelector,
                            CrossoverOperator crossoverOperator,
                            MutatorOperator mutatorOperator,
                            double crossoverRate,
                            double mutationRate,
                            boolean elitism,
                            int elitismSize,
                            int maxGeneration,
                            Clock clock,
                            int nThreads,
                            Printer printer,
                            double targetFitness,
                            boolean skipCrossover,
                            boolean skipMutation
                            )
    {
        this.parentSelector = parentSelector;
        //this.offspringSelector = offspringSelector;
        this.crossoverOperator = crossoverOperator;
        this.mutatorOperator = mutatorOperator;
        this.mutatorOperator.setMutationRate(mutationRate);
        this.crossoverRate = crossoverRate;
        this.elitism = elitism;
        this.elitismSize = elitismSize;
        this.maxGeneration = maxGeneration;
        this.clock = clock;
        this.nThreads = nThreads;
        this.printer = printer;
        this.targetFitness = targetFitness;
        this.skipCrossover = skipCrossover;
        this.skipMutation = skipMutation;
        taskExecutor = new TaskExecutor(clock);
        threadPool = new ForkJoinPool(nThreads);
        stopConditions = new ArrayList();
    }
    
         
    /**
     * Start evolution of a generation using the specified fitness function
     * 
     * @param population population to evolve 
     * 
     * @param fitnessFunction specified fitness function
     */
    public void evolve(Population population, FitnessFunction fitnessFunction)
    {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        Stream.generate(() -> {return evolveGeneration();})
              .map(result ->
              {
                  if(generationTracker != null)
                      track(result);
                  return result;
              })
              .takeWhile(result ->
              {
                  return !hasReachedStopCondition();
              }
              )
              //.limit(this.maxGeneration)
              .forEach(result ->
              {
                  //if(generationTracker != null)
                  //    track(result);
              });

        threadPool.shutdown();
        
        /*
        generationResult = Stream.generate(() -> {return evolveGeneration();}
        ).map(result -> 
        {
            //if(track)
                //System.out.println(elem.getGeneration());
            return result;
        }).limit(maxGeneration).skip(maxGeneration-1).findFirst().get();
        */
    }
    
    
    protected GenerationResult evolveGeneration()
    {
        final Population newPopulation = new Population();
        
        long generationNr = population.getGeneration();
                       
        int limit = elitism ? population.size()-elitismSize : population.size();

        //pass elites to the new population
        population.stream().limit(population.size()-limit).forEach(elem -> newPopulation.addIndividual(elem));
       
        Task evolutionParallelTask = getEvolutionParallelTask(limit, newPopulation);
    
        Duration evolutionDuration = taskExecutor.runTask(evolutionParallelTask, threadPool);
        
        Task evaluationParallelTask = () ->
        {
            calculatePopulationFitness();
        };
        Duration evaluationDuration = taskExecutor.runTask(evaluationParallelTask, threadPool);
                
        orderByFitness();  //first best fitness individuals are keeped in the next generation - elitism
          
        population.setGeneration(generationNr+1L);
        
        return new GenerationResult(evolutionDuration, evaluationDuration, population.getBestIndividual(), population.getGeneration());
    }
    
    private Task getEvolutionParallelTask(int limit, Population newPopulation)
    {
        return () ->
        {
            Stream<Individual> individualStream = null;
            
            if(!skipCrossover)
                individualStream = Stream.generate(parentsSupplier()).parallel()  //select parents
                //individualStream = Stream.generate(parentsSupplier())  //select parents
                    .map(couple ->
                    {
                        return crossover(couple);   //crossover
                    }
                    ).flatMap(offspring ->
                    {
                        return offspring.getIndividuals().stream();
                    }
                    ).limit(limit);
            else
                individualStream = population.stream().limit(limit); //parallelStream here will add hudge overhead and can make the algorithm 10 times slower
            
            //System.out.println("size: " + individualStream.);
            
            if(!skipMutation)
                individualStream = individualStream.map(individual ->
                {
                    mutate(individual);    //mutate individuals
                    return individual;
                }
                );
            
//            System.out.println("first individual: "+ population.getIndividual(0));
//            System.out.println("size: "+  population.size());
            
            individualStream.forEach(individual -> newPopulation.addIndividual(individual));
            
//            System.out.println("first individual 2: "+ population.getIndividual(0));
//            System.out.println("size2: "+  population.size());
//            
            population = newPopulation;
        };
    }
    
    private void calculatePopulationFitness()
    {
        population.parallelStream().forEach(individual -> fitnessFunction.calculateFitness(individual));
        //population.stream().forEach(individual -> fitnessFunction.calculateFitness(individual));
        
        /*
        List<CompletableFuture<Void>> individualFutures = population.stream()
               .map(ind -> calculateFitness(ind))
               .collect(Collectors.toList());
       
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                 individualFutures.toArray(new CompletableFuture[individualFutures.size()])
        );
        
        allFutures.join();
        */
    }
    
   
    /*
    private Population selectOffspring(Population population)
    {
        final Population newPopulation = new Population();
        Stream.generate(() ->
            {
                return offspringSelector.selectIndividual(population);
            }
            )
            .parallel()
            .limit(population.size())
            .forEach(individual -> newPopulation.addIndividual(individual));
        return newPopulation;
    }
    */
    
    private Supplier<Parents> parentsSupplier()
    {
        return () ->
        {
            Parents couple = selectParents();
            return couple;
        };
    }
      
    private Parents selectParents()
    {
        Parents parents = null;
        try
        {
            parents =  parentSelector.selectParents(population);
        }
        catch(SelectionException e)
        {
            Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, e);
        }
        return parents;
    }
    
    private Offspring crossover(Parents couple)
    {
        double random =  RandomUtils.nextDouble();
        Offspring offspring = new Offspring();
        if(crossoverRate > random) 
        {
            try
            {
                offspring = crossoverOperator.recombine(couple.getFirstParent(), couple.getSecondParent());
            }
            catch(CrossoverException e)
            {
                Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        else
        {
            //create the child as a copy of the parent, not the parent itself 
            //we can pass the elite wich can be mutated
            Chromosome firstOffspring = couple.getFirstParent().getChromosome().copy();
            offspring.addOffspring(new Individual(firstOffspring));
            if(!crossoverOperator.hasSingleOffspring())
            {
                Chromosome secondOffspring = couple.getSecondParent().getChromosome().copy();
                offspring.addOffspring(new Individual(secondOffspring));
            }
        }
        return offspring;
    }
        
    private void mutate(Individual individual)
    {
        try
        {
            mutatorOperator.mutate(individual);
        }
        catch(MutatorException e)
        {
            Logger.getLogger(GeneticAlgorithm.class.getName()).log(Level.SEVERE, null, e);
        }
    }
       
    private void orderByFitness()
    {
        population.sort();
    }
       
    private boolean hasReachedStopCondition() 
    {
        if (stopConditions.stream().anyMatch(stopCondition -> (stopCondition.isReached(population)))) {
            return true;
        }
        return false;
    }
    
    protected void setGenerationLimited(boolean generationLimited)
    {
        maxGenerationStop = (population) -> population.getGeneration() == maxGeneration; 
        this.stopConditions.add(maxGenerationStop);
    }
    
    protected void setFitnessLimited(boolean targetFitnessLimited)
    {
        targetFitnessStop = (population) -> targetFitness <= population.getBestIndividual().getFitnessScore();
        this.stopConditions.add(targetFitnessStop);
    }
    
    public void setCustomStopCondition(StopCondition customStopCondition)
    {
        this.stopConditions.add(customStopCondition);
    }
    
    private void track(GenerationResult result)
    {
        generationTracker.track(this, result);
    }
    
     /**
     * Set population 
     * 
      */
    public void setPopulation(Population population) {
        this.population = population;
    }
    
    public void changeMutatorOperator(MutatorOperator mutator)
    {
        this.mutatorOperator = verifyIsNotNull(mutator);
    }
    
    public void changeSelectorOperator(ParentSelector selector)
    {
        this.parentSelector = verifyIsNotNull(selector);
    }
    
    public void changeCrossoverOperator(CrossoverOperator crossover)
    {
        this.crossoverOperator = verifyIsNotNull(crossover);
    }
    
    //-----------------------GETTER METHODS---------------------------------------
       
    /**
     * Get population 
     * 
     * @return population
     */
    public Population getPopulation() {
        return population;
    }
    
    /**
     * Returns an long representing the generation number
     * 
     * @return generation
     */
    public long getGeneration()
    {
        return population.getGeneration();
    }
       
    /**
     * Returns elitism size
     * 
     * @return elitism size
     */
    public int getElitismSize() {
        return elitismSize;
    }

    /**
     * Returns true if elitism is set otherwise returns false
     * 
     * @return elitism 
     */
    public boolean isElitism() {
        return elitism;
    }
    
    /**
     * Set the mutation rate
     * 
     * @param mutationRate - mutation rate
     */
    public void setMutationRate(double mutationRate)
    {
        if(mutationRate < 0)
          throw new GaException("Mutation rate value cannot be negative");
        this.mutatorOperator.setMutationRate(mutationRate);
    }
      
    /**
     * Returns an double representing the mutation rate
     * 
     * @return mutation rate
     */
    public double getMutationRate()
    {
        return this.mutatorOperator.getMutationRate();
    }
    
    /**
     * Returns an double representing crossover rate
     * 
     * @return crossover rate
     */
    public double getCrossoverRate()
    {
        return this.crossoverRate;
    }
    
     /**
     * Set the crossover rate
     * 
     * @param crossoverRate - crossover rate
     */
    public void setCrossoverRate(double crossoverRate)
    {
        if(crossoverRate <= 0)
          throw new GaException("Crossover rate value cannot be negative");
        this.crossoverRate = crossoverRate;
    }
     
    /**
     * Set tracking mode  
     * 
     * @param generationTracker 
     * 
     */
    public void setGenerationTracker(GenerationTracker generationTracker)
    {
        this.generationTracker = generationTracker;
    }
    
    private static <T> T verifyIsNotNull(T obj)
    {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }
}
