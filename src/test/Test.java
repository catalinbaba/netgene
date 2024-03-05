/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author cbaba
 */
public class Test {
    
    private static int nThreads = Math.max(1, Runtime.getRuntime().availableProcessors() > 1 ? Runtime.getRuntime().availableProcessors() - 1 : 1);
    public static void main(String[] args) {
        
        System.out.println("threads: " + nThreads);
        
    }
    
}
