/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class Sudoku 
{
    private int[][] table = {{1,1,1,1,1,1,1,1,1,1},
        {0,1,2,3,4,5,6,7,8,9},
        {0,1,2,3,4,5,6,7,8,9},
        {0,1,2,3,4,5,6,7,8,9},
        {0,1,2,3,4,5,6,7,8,9},
        {0,1,2,3,4,5,6,7,8,9},
        {0,1,2,3,4,5,6,7,8,9},
        {0,1,2,3,4,5,6,7,8,9},
        {0,1,2,3,4,5,6,7,8,9},
    };
    
    
    //private int[][] table;

    private int columns = 9;
    private int rows = 9;
    
    
      
    public Sudoku(int table[][])
    {
        this.table = table;
    }
    
    public Sudoku(int table[])
    {
        for (int i = 0; i < table.length; i++) {
            int row = i / columns;
            int col = i % rows;
            this.table[row][col] = table[i];
        }

    }
   
    public void replaceValue(int index, int value)
    {
        int row = index / columns;
        int col = index % rows;
        this.table[row][col] = value;
    }
    
    public int[] get1DArray()
    {
        int grid1DArray[] = new int[rows * columns];
        int index = 0;
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < columns; j++) 
               grid1DArray[index++] =  table[i][j];
        
        return grid1DArray;
    }
    
    public Integer[] get1DArrayInteger()
    {
        Integer grid1DArray[] = new Integer[rows * columns];
        int index = 0;
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < columns; j++) 
               grid1DArray[index++] =  table[i][j];
        
        return grid1DArray;
    }
    
    public int[][] getTable()
    {
        return table;
    }
    
    public boolean canSwap(int row1, int row2, int fromColumn, int toColumn)
    {
        if(!isColumnDuplicate(toColumn, table[row1][fromColumn]) || !isColumnDuplicate(fromColumn, table[row2][toColumn]) 
                    || !isGridDuplicate(row2, toColumn, table[row1][fromColumn]) 
                    || !isGridDuplicate(row1, fromColumn, table[row2][toColumn]))
        {
            return true;
        }
        return false;
    }
    
    public void swap()
    {
        int array[] = get1DArray();
        
        int row1 = RandomUtils.nextInt(9);
        int row2 = RandomUtils.nextInt(9);
        row2 = row1;
        
        boolean success = false;
        while(!success)
        {
            int fromColumn = RandomUtils.nextInt(9);
            int toColumn = RandomUtils.nextInt(9);
            if(fromColumn == toColumn)
            {
                if(fromColumn == 0)
                    toColumn = fromColumn + 1; 
                else
                    fromColumn = toColumn - 1; 
            }
            
            if(!isColumnDuplicate(toColumn, table[row1][fromColumn]) || !isColumnDuplicate(fromColumn, table[row2][toColumn]) 
                    || !isGridDuplicate(row2, toColumn, table[row1][fromColumn]) 
                    || !isGridDuplicate(row1, fromColumn, table[row2][toColumn]))
            {
                int temp = table[row2][toColumn];
                table[row2][toColumn] = table[row1][fromColumn];
                table[row1][fromColumn] = temp;
                success = true;
            }
        }
    }
    
    private boolean isRowDuplicate(int row, int value)
    {
        for(int i=0; i<columns; i++)
        {
            if(table[row][i] == value)
                return true;
        }
        return false;
    }
    
    private boolean isColumnDuplicate(int column, int value)
    {
        for(int i=0; i<rows; i++)
        {
            if(table[i][column] == value)
                return true;
        }
        return false;
    }
     
     private boolean isGridDuplicate(int row, int column, int value)
     {
        int gridRow = 3 * (row / 3);
        int gridColumn = 3 * (column / 3);

         
         for (int i = gridRow; i < gridRow + 3; i++) {
            for (int j = gridColumn; j < gridColumn + 3; j++) {
                if (table[i][j] == value) {
                    return true;
                }
            }
        }
        return false;
     }
    
  
    
    public int countRowDuplicates(int row)
    {
        int duplicates = 0;
        for(int i=0; i<columns; i++)
            for(int j=i+1; j<columns; j++ )
               if(table[row][i] == table[row][j])
                {
                   duplicates++;
                   break;
                }
        return duplicates;
    }
    
    public int countColumnsDuplicates(int column)
    {
        int duplicates = 0;
        for(int i=0; i<rows; i++)
            for(int j=i+1; j<rows; j++ )
               if(table[i][column] == table[j][column])
                {
                   duplicates++;
                   break;
                }
        return duplicates;
    }
    
    public int calculateFitnessScore() {
        int fitnessScore = 0;

        // Check rows
        for (int i = 0; i < 9; i++) {
            boolean[] found = new boolean[9];
            for (int j = 0; j < 9; j++) {
                int num = table[i][j];
                if (found[num - 1]) {
                    fitnessScore--;
                } else {
                    found[num - 1] = true;
                }
            }
            fitnessScore += 9;
        }

        // Check columns
        for (int j = 0; j < 9; j++) {
            boolean[] found = new boolean[9];
            for (int i = 0; i < 9; i++) {
                int num = table[i][j];
                if (found[num - 1]) {
                    fitnessScore--;
                } else {
                    found[num - 1] = true;
                }
            }
            fitnessScore += 9;
        }

        // Check subgrids
        for (int k = 0; k < 9; k++) {
            boolean[] found = new boolean[9];
            for (int i = k / 3 * 3; i < k / 3 * 3 + 3; i++) {
                for (int j = k % 3 * 3; j < k % 3 * 3 + 3; j++) {
                    int num = table[i][j];
                    if (found[num - 1]) {
                        fitnessScore--;
                    } else {
                        found[num - 1] = true;
                    }
                }
            }
            fitnessScore += 9;
        }

        return fitnessScore;
    }
    
    public int countGridDuplicates(int grid)
    {
        int startRow=0, startCol=0;
        if(grid %3 ==0)
            startRow = 0;
        if(grid %3 ==1)
            startRow = 3;
        if(grid %3 ==2)
            startRow = 6;
        
        if(grid/3 ==0)
            startCol = 0;
        if(grid/3 ==1)
            startCol = 3;
        if(grid/3 ==2)
            startCol = 6;
        
        int grid1DArray[] = new int[rows];
        int index = 0;
        
        for (int i = startRow; i < startRow + 3; i++) 
            for (int j = startCol; j < startCol + 3; j++) 
               grid1DArray[index++] =  table[i][j];

        int duplicates = 0;
        for(int i=0; i<index; i++)
            for(int j= i+1; j<index; j++)
                if(grid1DArray[i] == grid1DArray[j])
                {
                    duplicates++;
                    break;
                }
        return duplicates;
    }
    
    public int countDuplicates()
    {
        int duplicate = 0;
        
        for(int i=0; i<rows; i++)
           duplicate = duplicate + countRowDuplicates(i);
        
        for(int i=0; i<columns; i++)
           duplicate = duplicate + countColumnsDuplicates(i);
        
        for(int i=0; i<rows; i++)
           duplicate = duplicate + countGridDuplicates(i);
        
        return duplicate;
    }
    
    public static void printArray(int [] array)
    {
        for(Integer i : array)
            System.out.print(i + " ");
    }
    
    
    public static void print2DArray(int array[][])
    {
         for (int i = 0; i < 9; i++) 
         {
            System.out.println();
            for (int j = 0; j < 9; j++) 
            {
                System.out.print(array[i][j] + " ");
            }
         }
    }
    
    public void saveGame(String fileName) throws Exception
    {
        File fout = new File(fileName);
	FileOutputStream fos = new FileOutputStream(fout);
 	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < columns; j++) 
            {
               bw.write(""+table[i][j]);
               bw.newLine();
            }
        bw.close();
 
    }
    
    public void loadGame(String fileName) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        
        LinkedList<Integer> tableList = new LinkedList();
                
        String value;
        
        while ((value = reader.readLine()) != null) 
            tableList.add(Integer.parseInt(value));
        
         reader.close();
        
        int values[] = new int[tableList.size()];
        
        for (int i = 0; i < tableList.size(); i++) {
            int row = i / columns;
            int col = i % rows;
            this.table[row][col] = tableList.get(i);
        }
    }
    
}

