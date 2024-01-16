/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.x.demo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Population;
import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.chromosome.PermutationChromosome;
import org.netgene.ga.GeneticAlgorithm;
import org.netgene.ga.GeneticConfiguration;
import org.netgene.ga.crossover.Order1Crossover;
import org.netgene.ga.fitness.FitnessFunction;
import org.netgene.ga.selection.parent.TournamentSelector;
import org.netgene.ga.mutator.InversionMutator;
import org.netgene.ga.GenerationTracker;
import org.netgene.ga.stop.StopCondition;

/**
 *
 * @author cbaba
 */
public class Lesson2 
{
    /*
    private static int distances[][] = {
                                        {0,3,5,1},
                                        {3,0,4,2},
                                        {5,4,0,7},
                                        {1,2,7,0}
                                       };
    */
    
    private static int distances[][] = {
                                        {0,3,7,1,3,5},
                                        {3,0,8,5,1,2},
                                        {7,8,0,4,3,8},
                                        {1,5,4,0,6,7},
                                        {3,1,3,6,0,1},
                                        {5,2,8,7,1,0}
                                       };
    
    
    public static void main(String[] args) 
    {
        TournamentSelector tournamentSelector = new TournamentSelector(3);
        Order1Crossover o1 = new Order1Crossover();
        InversionMutator inv = new InversionMutator();
        GeneticAlgorithm ga = new GeneticConfiguration()
                                                  .setParentSelector(tournamentSelector)
                                                  .setCrossoverOperator(o1)
                                                  .setMutatorOperator(inv)
                                                  .setElitismSize(1)
                                                  .setMaxGeneration(10)
                                                  .setThreadPoolSize(2)
                                                  .getAlgorithm();
        
        Population population = new Population();
         
        int populationSize = 5;
        int chromosomeSize = 5;
        
        for(int i=0; i<populationSize; i++)
        {
            PermutationChromosome chromosome = new PermutationChromosome(chromosomeSize, 1);
            Individual individual = new Individual(chromosome);
            population.addIndividual(individual);
        }
        
        FitnessFunction exFitness = (individual) ->
        {
            PermutationChromosome chromosome = (PermutationChromosome)individual.getChromosome();
            
            int gene1Allel = chromosome.getGene(0).getAllele();
            int gene2Allel = chromosome.getGene(1).getAllele();
            int gene3Allel = chromosome.getGene(2).getAllele();
                       
            int d1 = distances[0][gene1Allel];
            int d2 = distances[gene1Allel][gene2Allel];
            int d3 = distances[gene2Allel][gene3Allel];
            int d4 = distances[gene3Allel][0];
            
            int totalDistance = d1 + d2 + d3 + d4;
            
            double fitness = (double)1/totalDistance * 1000;
 
            individual.setFitnessScore(fitness);
        };
        
        FitnessFunction exFitness2 = (individual) ->
        {
            PermutationChromosome chromosome = (PermutationChromosome)individual.getChromosome();

            int totalDistance = 0;
            int dStart = distances[0][chromosome.getGene(0).getAllele()];
            
            for(int i=0; i<chromosome.length()-1; i++)
            {
                totalDistance = totalDistance + distances[chromosome.getGene(i).getAllele()][chromosome.getGene(i+1).getAllele()];
            }
           
            
            int dEnd = distances[chromosome.getGene(chromosome.length()-1).getAllele()][0];
            
            totalDistance = dStart + dEnd + totalDistance;
            
            double fitness = (double)1/totalDistance * 1000;
 
            individual.setFitnessScore(fitness);
        };
        
        StopCondition stopCondition = (pop) ->
        {
            PermutationChromosome chromosome = (PermutationChromosome)pop.getBestIndividual().getChromosome();
            int totalDistance = calculateDistance(chromosome);
            if(totalDistance <23)
                return true;
            else
                return false;
        };
        
        
        GenerationTracker myTraker = (g, r) ->
        {
            System.out.println("----------------------------------------------------");
            System.out.println("Step: " + g.getGeneration());
            System.out.println("Evolution execution: " + r.getEvolutionDuration().toMillis() + "ms");
            System.out.println("Evaluation execution: " + r.getEvaluationDuration().toMillis() + "ms");
            System.out.println("Generation execution: " + r.getGenerationDuration().toMillis()+ "ms");
            System.out.println("Best fitness: " +g.getPopulation().getBestIndividual().getFitnessScore());
            System.out.println("lowest fitness: " + g.getPopulation().getIndividual(0).getFitnessScore());
            //System.out.println("custom data: " + g.getPopulation().getBestIndividual().getCustomData());
        };
        
        //ga.setCustomStopCondition(stopCondition);
        ga.setGenerationTracker(myTraker);
        ga.evolve(population, exFitness2);
        
        double fitnessValue = ga.getPopulation().getBestIndividual().getFitnessScore();
        
        System.out.println("");
        System.out.println("===========================================");
        System.out.println("fitness value: " + fitnessValue);
        System.out.println("generation: " + ga.getGeneration());
        Chromosome bestChromosome = ga.getPopulation().getBestIndividual().getChromosome();
        String bestRoute = "0->";
        for(int i=0; i<bestChromosome.length(); i++)
        {
            bestRoute = bestRoute + bestChromosome.getGene(i).getAllele() + "->";
        }
        bestRoute = bestRoute + "0";
        System.out.println("best route: " + bestRoute);
        System.out.println("total distance: " + calculateDistance((PermutationChromosome)bestChromosome));
    }
    
    public static int calculateDistance(PermutationChromosome chromosome)
    {
        int totalDistance = 0;
        int dStart = distances[0][chromosome.getGene(0).getAllele()];
               
        for(int i=0; i<chromosome.length()-1; i++)
        {
            totalDistance = totalDistance + distances[chromosome.getGene(i).getAllele()][chromosome.getGene(i+1).getAllele()];
        }
           
        int dEnd = distances[chromosome.getGene(chromosome.length()-1).getAllele()][0];
            
        totalDistance = dStart + dEnd + totalDistance;
        
        return totalDistance;
    }
}

