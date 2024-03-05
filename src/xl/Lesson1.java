/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xl;

import org.netgene.ga.GeneticConfiguration;
import org.netgene.ga.*;
import org.netgene.ga.core.*;
import org.netgene.ga.crossover.*;
import org.netgene.ga.mutator.*;
import org.netgene.ga.selection.parent.*;
import org.netgene.ga.stop.*;
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
public class Lesson1 {
    public static void main(String[] args) {
        
        
        BitFlipMutator bf = new BitFlipMutator();
        TwoPointCrossover hpc = new TwoPointCrossover();
        GeneticAlgorithm ga = new GeneticConfiguration().setElitismSize(1)
                                  .setMaxGeneration(100)
                                  .setMutatorOperator(bf)
                                  .setCrossoverOperator(hpc)
                                  .setElitismSize(2)
                                  .getAlgorithm();
        
        Population population = new Population();
        int populationSize = 10;
        int chromosomeSize = 5;
        BitChromosome chromosome = new BitChromosome(chromosomeSize);
        
        for(int i=0; i<populationSize; i++)
        {
            Individual individual = new Individual(chromosome);
            population.addIndividual(individual);
        }
               
        FitnessFunction exFitness = (individual) ->
        {
            //BitChromosome chromosome = individual.getChromosome();
            BitChromosome ch = (BitChromosome)individual.getChromosome();
            int fitness = 0;
            for(int i=0; i<ch.length(); i++)
            {
                BitGene gene = ch.getGene(i);
                if(gene.getAllele() == true)
                    fitness++;
            }
            individual.setFitnessScore(fitness);
        };
        
        ga.evolve(population, exFitness);
        
        Individual bestIndividual = ga.getPopulation().getBestIndividual();
        
        System.out.println("individual: " + bestIndividual);
        System.out.println("Fitness score: " + bestIndividual.getFitnessScore());
        
        
    }
}
