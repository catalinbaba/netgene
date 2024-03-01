/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xsudoku;

import org.netgene.ga.chromosome.Chromosome;
import org.netgene.ga.chromosome.PermutationChromosome;
import org.netgene.ga.core.Individual;
import org.netgene.ga.core.Offspring;
import org.netgene.ga.crossover.CrossoverOperator;
import org.netgene.ga.exception.CrossoverException;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class CustomCrossover extends CrossoverOperator
{

    @Override
    public Offspring recombine(Individual x, Individual y) throws CrossoverException {
        Chromosome firstOffspring = x.getChromosome().copy();
        Chromosome secondOffspring = y.getChromosome().copy();
        
        if(firstOffspring instanceof PermutationChromosome || secondOffspring instanceof PermutationChromosome)
            throw new CrossoverException("Cannot use One Point Crossover for Permutation Chromosome. Only Permutation Crossover Operators are allowed");
        if(firstOffspring.length()!= secondOffspring.length())
            throw new CrossoverException("Cannot recombine chromosomes with different lengths");
        
        Offspring offspring= new Offspring();
   
        int crossoverPoint = RandomUtils.nextInt(firstOffspring.length());
         
        for(int i=crossoverPoint; i<firstOffspring.length(); i++)
            swap(firstOffspring, secondOffspring, i);
          
        if(!singleOffspring)
        {
            offspring.addOffspring(new Individual(firstOffspring));
            offspring.addOffspring(new Individual(secondOffspring));
        }
        else
        {
            offspring.addOffspring(new Individual(firstOffspring));
        }
        for(int i=0; i<offspring.getSize(); i++)
        {
            Individual indv = offspring.getOffspring(i);
            indv.getChromosome().setGene(1, new IntegerGene(2));
        }
        return offspring;
    }
    
}
