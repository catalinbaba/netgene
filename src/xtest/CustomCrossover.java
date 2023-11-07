/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import org.netgene.ga.Individual;
import org.netgene.ga.Offspring;
import org.netgene.ga.chromosome.*;
import org.netgene.ga.crossover.*;
import org.netgene.ga.exception.CrossoverException;
import org.netgene.ga.gene.IntegerGene;

/**
 *
 * @author cbaba
 */
public class CustomCrossover extends CrossoverOperator
{

    @Override
    public Offspring recombine(Individual x, Individual y) throws CrossoverException {
       
        Order1Crossover oc = new Order1Crossover();
        IntegerChromosome cx = (IntegerChromosome)x.getChromosome().copy();
        IntegerChromosome cy = (IntegerChromosome)y.getChromosome().copy();
        
        
        IntegerChromosome offspring1Chromosome = new IntegerChromosome();
        IntegerChromosome offspring2Chromosome = new IntegerChromosome();
        
        for(int i=0; i<9; i++)
        {
            PermutationChromosome px = new PermutationChromosome();
            PermutationChromosome py = new PermutationChromosome();
            for(int j=0; j<9; j++)
            {
                px.addGene(cx.getGene(j));
                py.addGene(cy.getGene(j));
            }
            Individual indx = new Individual(px);
            Individual indy = new Individual(py);
            Offspring offspring = oc.recombine(indx, indy);
            Individual off1 = offspring.getOffspring(0);
            Individual off2 = offspring.getOffspring(1);
            for(int j=0; j<9; j++)
            {
                offspring1Chromosome.addGene((IntegerGene)off1.getChromosome().getGene(j));
                offspring2Chromosome.addGene((IntegerGene)off2.getChromosome().getGene(j));
            }
            
        }
        Offspring offspringRez= new Offspring();

        offspringRez.addOffspring(new Individual(offspring1Chromosome));
        offspringRez.addOffspring(new Individual(offspring2Chromosome));
            
        return offspringRez;
    }
    
    
    
}
