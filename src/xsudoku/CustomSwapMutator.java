/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xsudoku;

import java.util.ArrayList;
import org.netgene.ga.core.Individual;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.ga.mutator.MutatorOperator;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class CustomSwapMutator extends MutatorOperator
{
    private ArrayList<Integer> doNotMutate = new ArrayList();
    
    protected double sigma = 0.5;
    
    public void setDoNotMutateList(ArrayList doNotMutate)
    {
        this.doNotMutate = doNotMutate;
    }
    
    private void mutateSwap(Individual individual)
    {
        IntegerChromosome chromosome = (IntegerChromosome)individual.getChromosome();
        double rand = RandomUtils.nextDouble();
        Sudoku sudoku = new Sudoku(chromosome.toArray());
        
              
        boolean success = false;
        int i = 0;
        if(rand < mutationRate) //is selected to be mutated
        {
            while(!success)
            {
//                if(i==50)
//                    break;
                int row1 = RandomUtils.nextInt(9);
                int row2 = RandomUtils.nextInt(9);
                //row2 = row1;
                //System.out.println("buclaaaaaaaa");
                int fromColumn = RandomUtils.nextInt(9);
                int toColumn = RandomUtils.nextInt(9);
                if(fromColumn == toColumn)
                {
                    if(fromColumn == 0)
                        toColumn = fromColumn + 1; 
                    else
                        fromColumn = toColumn - 1; 
                }
                int firstIndex = (row1*9) + fromColumn;
                int secondIndex = (row2*9) + toColumn;
                i++;
                if(i==20)
                    break;
                if(doNotMutate.contains(firstIndex) || doNotMutate.contains(secondIndex))
                   continue;
                
                if(sudoku.canSwap(row1, row2, fromColumn, toColumn))
                {
                    //System.out.println("AMMMMMMMMMMMMMMMMMMMM ajuns aici2");
                    
                   
                    IntegerGene firstGene = chromosome.getGene(firstIndex);
                    chromosome.setGene(firstIndex, chromosome.getGene(secondIndex));
                    chromosome.setGene(secondIndex, firstGene);
                    success = true;
                    //System.out.println("swap " + firstIndex + " " + secondIndex);
                }
                //System.out.println("dasdasdasdasdasdasdas");
                 
            }
        }      
    }
    
    public void mutate(Individual individual) throws MutatorException {
        //System.out.println("aicicicicici");
        mutateSwap(individual);
    }
}
