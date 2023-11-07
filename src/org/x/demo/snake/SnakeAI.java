/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.x.demo.snake;

import java.util.concurrent.ExecutorService;
import org.netgene.ga.Individual;
import org.netgene.ga.Population;
import org.netgene.ga.chromosome.DoubleChromosome;
import org.netgene.ga.core.GeneticAlgorithm;
import org.netgene.ga.gene.DoubleGene;
import org.netgene.ga.selection.parent.TournamentSelector;
import org.netgene.network.MultiLayerNetwork;
import org.netgene.network.exception.NNException;
import org.netgene.ga.core.GeneticConfiguration;
import org.netgene.ga.stop.StopCondition;
import org.netgene.ga.core.GenerationTracker;

/**
 *
 * @author CBaba
 */
public class SnakeAI 
{
    private static MultiLayerNetwork[] multiLayerNetworks;
    
    private static int populationSize = 1000;
    
    private static ExecutorService executor;
    
    public static void main(String [] args) throws Exception
    {
        multiLayerNetworks = new MultiLayerNetwork[populationSize];
        
        generateNetworks();
        
        Population population = new Population();
         
        for(int i=0; i<populationSize; i++)
        {
            DoubleChromosome chromosome = new DoubleChromosome();
            Double weights[] = multiLayerNetworks[i].getNetworkWeights();
            for(int j=0; j<weights.length; j++)
            {
                DoubleGene gene = new DoubleGene(weights[j]);
                chromosome.addGene(gene);
            }
            Individual individual = new Individual(chromosome);
            population.addIndividual(individual);
        }
        
          
        System.out.println("Population is created!");
        
        SnakeFitness snakeFitness = new SnakeFitness();
           
        TournamentSelector tournamentSelector = new TournamentSelector(10);
        
        GeneticAlgorithm ga = new GeneticConfiguration()
                                                  .setParentSelector(tournamentSelector)
                                                  //.setOffspringSelector(tournamentSelector)
                                                  //.setElitismSize(2)
                                                  .setElitismSize(10)
                                                  .setMaxGeneration(1000)
                                                  .setThreadPoolSize(2)
                                                  .getAlgorithm();
        
        
        
        GenerationTracker debugStep = (g, r) ->
        {
            System.out.println("----------------------------------------------------");
            System.out.println("Generation: " + g.getGeneration());
            System.out.println("Evolution execution: " + r.getEvolutionDuration().toMillis() + "ms");
            System.out.println("Evaluation execution: " + r.getEvaluationDuration().toMillis() + "ms");
            System.out.println("Generation execution: " + r.getGenerationDuration().toMillis()+ "ms");
            System.out.println("Best fitness score: " +g.getPopulation().getBestIndividual().getFitnessScore());
            System.out.println("Longest snake size: " + g.getPopulation().getBestIndividual().getCustomData());
        };
        /*
        StopCondition customStopCondition = (pop) ->
        {
            if((int)pop.getBestIndividual().getCustomData() >= 20)
                return true;
            else
                return false;
        };
        */
        ga.setGenerationTracker(debugStep);
        //ga.setCustomStopCondition(customStopCondition);
        long startTime = System.nanoTime();
        ga.evolve(population, snakeFitness);
        long endTime = System.nanoTime();
        
        System.out.println("--------------------------------------------------");
        
        Individual individual = ga.getPopulation().getBestIndividual();
        
        System.out.println("Best Individual fitness: " + individual.getFitnessScore());
        System.out.println("Best size: " + (Integer)individual.getCustomData());
 
        DoubleChromosome chromosome = (DoubleChromosome)individual.getChromosome();
        
        double weights[] = chromosome.toArray();
        
        MultiLayerNetwork bestNetwork = BrainGenerator.generateBrain();
        
        try {
            bestNetwork.setNetworkWeights(weights);
        } catch (NNException ex) {
            System.out.println(ex.toString());;
        }
                
        bestNetwork.saveNetwork("run_2.txt");
        
        long timeElapsed = endTime - startTime;
	//System.out.println("Execution time in nanoseconds: " + timeElapsed);
	//System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
        System.out.println("Execution time in seconds: " + (double)(timeElapsed / 1000000)/1000);
        
        System.out.println("--------------------------------------------------");
//        for(int i=0; i<ga.getPopulation().size(); i++)
//        {
//            System.out.println("Fitness value: " + ga.getPopulation().getIndividual(i).getFitnessScore());
//        }
    }
    
    public static void generateNetworks() throws Exception
    {
        MultiLayerNetwork multiLayerNetwork;
        
        for(int i=0; i<populationSize; i++)
        {
            multiLayerNetwork = BrainGenerator.generateBrain();
            
            // uncomment if you don't want to load 
            
            //multiLayerNetwork.loadNetwork("legend.txt");
            multiLayerNetworks[i] = multiLayerNetwork;           
        }
        
    }
   
}