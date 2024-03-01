/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.x.demo;


import org.netgene.ga.core.Population;
import org.netgene.ga.core.Individual;
import org.netgene.ga.*;
import org.netgene.ga.crossover.*;
import org.netgene.ga.mutator.*;
import org.netgene.ga.chromosome.*;
import org.netgene.ga.fitness.*;
import org.netgene.ga.gene.*;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cbaba
 */
public class Lesson1 
{
    public static void main(String[] args) 
    {
        
        //TournamentSelector tournamentSelector = new TournamentSelector(3);
        BitFlipMutator bfMutator = new BitFlipMutator();
        TwoPointCrossover hpc = new TwoPointCrossover();
        GeneticAlgorithm ga = new GeneticConfiguration()
                                                  //.setParentSelector(tournamentSelector)
                                                  //.setOffspringSelector(tournamentSelector)
                                                  //.setElitismSize(2)
                                                  .setMutatorOperator(bfMutator)
                                                  //.setCrossoverOperator(hpc)
                                                  //.setMutationRate(0)
                                                  .setElitismSize(1)
                                                  .setMaxGeneration(100)
                                                  .setThreadPoolSize(2)
                                                  .setTargetFitness(5)
                                                  .getAlgorithm();
               
        Population population = new Population();
         
        int populationSize = 3;
        int chromosomeSize = 5;
        
        for(int i=0; i<populationSize; i++)
        {
            BitChromosome chromosome = new BitChromosome(chromosomeSize);
            Individual individual = new Individual(chromosome);
            population.addIndividual(individual);
        }
        
        FitnessFunction exFitness = (individual) ->
        {
            //BitChromosome chromosome = individual.getChromosome();
            BitChromosome chromosome = (BitChromosome)individual.getChromosome();
            int fitness = 0;
            for(int i=0; i<chromosome.length(); i++)
            {
                BitGene gene = chromosome.getGene(i);
                if(gene.getAllele() == true)
                    fitness++;
            }
            individual.setFitnessScore(fitness);
        };
        
               
        GenerationTracker myTracker = (g, r) ->
        {
            System.out.println("Step: " + r.getGenerationNumber());
            System.out.println("best fitness: " + r.getBestFitness());
            System.out.println("best individual: " + r.getBestIndividual());
            System.out.println("Evaluation execution: " + r.getEvolutionDuration().toMillis() + "ms");
            //System.out.println("----------------------------------------------------");
            //System.out.println("Step: " + g.getGeneration());
            //System.out.println("Evolution execution: " + r.getEvolutionDuration().toMillis() + "ms");
            //System.out.println("Evaluation execution: " + r.getEvaluationDuration().toMillis() + "ms");
            //System.out.println("Generation execution: " + r.getGenerationDuration().toMillis()+ "ms");
            //System.out.println("Best fitness: " +g.getPopulation().getBestIndividual().getFitnessScore());
            //System.out.println("lowest fitness: " + g.getPopulation().getIndividual(0).getFitnessScore());
            //System.out.println("custom data: " + g.getPopulation().getBestIndividual().getCustomData());
            Population population1 = g.getPopulation();
            if(r.getBestFitness() > 3)
            {
                g.setMutationRate(0.01);
            }
            System.out.println("----------------------------------------------------");
        };
        
        ga.setGenerationTracker(myTracker);
        //ga.setCustomStopCondition(customStopCondition);
        ga.evolve(population, exFitness);
        
        Individual bestIndividual = ga.getPopulation().getBestIndividual();
        
        double fitnessValue = ga.getPopulation().getBestIndividual().getFitnessScore();
        
        System.out.println("individual: " + ga.getPopulation().getBestIndividual());
        System.out.println("fitness value: " + fitnessValue);
        //System.out.println("generation: " + ga.getGeneration());
        
    }

}
