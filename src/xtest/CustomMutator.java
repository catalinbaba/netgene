/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import java.util.ArrayList;
import org.netgene.ga.Individual;
import org.netgene.ga.chromosome.IntegerChromosome;
import org.netgene.ga.exception.MutatorException;
import org.netgene.ga.gene.Gene;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.ga.mutator.MutatorOperator;
import org.netgene.utils.RandomUtils;
/*
 *
 * @author cbaba
 */
public class CustomMutator extends MutatorOperator
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
                sudoku.canSwap(row1, row2, fromColumn, toColumn);
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
    
    private void mutateRandom(Individual individual)
    {
        IntegerChromosome chromosome = (IntegerChromosome)individual.getChromosome();
        for(int i=0 ;i<chromosome.length(); i++)
        {
            double rand = RandomUtils.nextDouble();
            if(rand < mutationRate) //is selected to be mutated
            {
                int delta = RandomUtils.nextInt(1, 10);
                IntegerGene gene = new IntegerGene(delta);
                    
                chromosome.setGene(i, gene);
            }
        }
    }
    
    private void mutateRandomCheck(Individual individual)
    {
        IntegerChromosome chromosome = (IntegerChromosome)individual.getChromosome();
        Sudoku sudoku = new Sudoku(chromosome.toArray());
        
        for(int i=0 ;i<chromosome.length(); i++)
        {
            double rand = RandomUtils.nextDouble();
            if(rand < mutationRate) //is selected to be mutated
            {
                int delta = RandomUtils.nextInt(1, 10);
                //find the column, row and grid
                int row = i / 9;
                int column = i % 9;
                
                int gridRow = row / 3;
                int gridCol = column / 3;
                int grid = gridRow * 3 + gridCol;
                /*
                In this code, we divide the row index by 3 to determine the grid row, and divide the column index by 3 to determine the grid column. We then calculate the grid number by multiplying the grid row by 3 and adding the grid column. This will give you a grid number ranging from 0 to 8, indicating the specific 3x3 grid to which the element belongs.
                */
                //get the duplicates before mutation
                int rowDuplicate = sudoku.countRowDuplicates(row);
                int columnDuplicate = sudoku.countColumnsDuplicates(column);
                int gridDuplicate = sudoku.countGridDuplicates(grid);
                //
                sudoku.replaceValue(i, delta);
                
                int newRowDuplicate = sudoku.countRowDuplicates(row);
                int newColumnDuplicate = sudoku.countColumnsDuplicates(column);
                int newGridDuplicate = sudoku.countGridDuplicates(grid);
                
                if(newRowDuplicate > rowDuplicate)
                {
                    //System.out.println("NU mutez0!!!!!!!!");
                    return; //do not mutate
                }
                if(newColumnDuplicate > columnDuplicate)
                {
                    //System.out.println("NU mutez1!!!!!!!!");
                    return; //do not mutate
                }
                if(newGridDuplicate > gridDuplicate)
                {
                    //System.out.println("NU mutez2!!!!!!!!");
                    return; //do not mutate
                }
                
                
                
                //System.out.println("am ajuns sa mutez!!!!!!!!");
                
                IntegerGene gene = new IntegerGene((int)delta);
                    
                chromosome.setGene(i, gene);
            }
        }
    }
    
    @Override
    public void mutate(Individual individual) throws MutatorException {
        //System.out.println("aicicicicici");
        mutateSwap(individual);
        //mutateRandomCheck(individual);
        //mutateRandom(individual);
        /*
        Chromosome chromosome = (IntegerChromosome)individual.getChromosome();
        //System.out.println("zzzzzzzzzzzzzzzz");
        int firstIndex = RandomUtils.nextInt(chromosome.length());
        int secondIndex = RandomUtils.nextInt(chromosome.length());
        double rand = RandomUtils.nextDouble();
        if(firstIndex == secondIndex)  //if both positions are the same
        {
            if(firstIndex == 0)
                secondIndex = firstIndex + 1; 
            else
                secondIndex = firstIndex - 1; 
        }
        
        if(rand < mutationRate) //is selected to be mutated
        {
            if(doNotMutate.contains(firstIndex) || doNotMutate.contains(secondIndex))
                 return;
            Gene firstGene = chromosome.getGene(firstIndex);
                
            chromosome.setGene(firstIndex, chromosome.getGene(secondIndex));
            chromosome.setGene(secondIndex, firstGene);
        }
        */
    }
    
}
