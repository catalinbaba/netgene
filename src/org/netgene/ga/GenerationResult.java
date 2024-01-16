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
import java.time.Duration;
import org.netgene.ga.core.Individual;

/**
 * Generation Result in each generation
 * 
 * @author Catalin Baba
 */
public final class GenerationResult implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private final Duration evolutionDuration;
    
    private final Duration evaluationDuration;
    
    private final Duration generationDuration;
    
    private final double bestFitness;
    
    private final Individual individual;
    
    private final long generationNumber;

    
    protected GenerationResult(Duration evolutionDuration, Duration evaluationDuration, Individual individual, long generationNumber)
    {
        this.evolutionDuration = evolutionDuration;
        this.evaluationDuration = evaluationDuration;
        this.individual = individual;
        this.bestFitness = individual.getFitnessScore();
        this.generationNumber = generationNumber;
        generationDuration = evolutionDuration.plus(evaluationDuration);
    }
 
    /**
     * Get the evolution duration for an generation
     * 
     * @return Duration of evolution
     */
    public Duration getEvolutionDuration() {
        return evolutionDuration;
    }

    /**
     * Get the fitness evaluation duration for an generation
     * 
     * @return Duration of fitness evolution
     */
    public Duration getEvaluationDuration() {
        return evaluationDuration;
    }
    
    /**
     * Get the best individual 
     * 
     * @return best Individual
     */
    public Individual getBestIndividual() {
        return individual;
    }

    /**
     * Get the evolution duration for a generation - how long it takes to evolve a generation
     * 
     * @return the evolution duration
     */
    public Duration getGenerationDuration() {
        return generationDuration;
    }
    
    /**
     * Get the best fitness
     * 
     * @return best fitness
     */
    public double getBestFitness() {
        return bestFitness;
    }
    
    /**
     * Get generation number
     * 
     * @return generation number
     */
    public long getGenerationNumber() {
        return this.generationNumber;
    }
    
}
