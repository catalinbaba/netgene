/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.netgene.ga.gene.IntegerGene;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class Main 
{
    public static void main(String[] args) throws Exception
    {
        
        int number = 78;
        int result = number - (number % 9);
        System.out.println("Result: " + result);
        
        
        for(int i=0; i<10; i++)
        {
            //SudokuGARunner sgr = new SudokuGARunner();
            //sgr.run(i);
             System.out.println("aici1");
            if(1==1)
                continue;
            System.out.println("aici2");
        }
    }
    
    public static void fillUniqueDigits(int[] array) {
        Set<Integer> fixedDigits = new HashSet<>();
        for (int num : array) {
            if (num != 0) {
                fixedDigits.add(num);
            }
        }
        
        int index = 0;
        for (int i = 1; i <= 9; i++) {
            if (!fixedDigits.contains(i)) {
                while (array[index] != 0) {
                    index++;
                }
                array[index] = i;
                index++;
            }
        }
    }
}

