/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import org.netgene.ga.Individual;
import org.netgene.ga.Offspring;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.crossover.CrossoverOperator;
import org.netgene.ga.exception.CrossoverException;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class CustomPyCrossover extends CrossoverOperator
{

    @Override
    public Offspring recombine(Individual x, Individual y) throws CrossoverException {
        
        IntegerChromosome child1 = (IntegerChromosome)x.getChromosome().copy();
        IntegerChromosome child2 = (IntegerChromosome)y.getChromosome().copy();
        
        //generateCrossoverPoints
        int firstCrossoverPoint = RandomUtils.nextInt(child1.length());
        int secondBound = child1.length()- firstCrossoverPoint;
        int diff = RandomUtils.nextInt(secondBound+1);
        int secondCrossoverPoint = firstCrossoverPoint + diff;
        if(firstCrossoverPoint == secondCrossoverPoint)
            if(firstCrossoverPoint==0)
                secondCrossoverPoint++;
            else
                firstCrossoverPoint--;
        
        firstCrossoverPoint =  firstCrossoverPoint - (firstCrossoverPoint % 9);
        secondCrossoverPoint = secondCrossoverPoint - (secondCrossoverPoint % 9) -1;
        
        Offspring offspring= new Offspring();
        
        for(int i=firstCrossoverPoint; i<secondCrossoverPoint; i++)
            swap(child1, child2, i);
        
        offspring.addOffspring(new Individual(child1));
        offspring.addOffspring(new Individual(child2));
               
        return offspring;
    }
    
}
