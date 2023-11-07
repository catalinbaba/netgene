/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.netgene.network.MultiLayerNetwork;
import java.text.DecimalFormat;
import org.netgene.network.error.function.ErrorType;
import org.netgene.network.exception.NNException;
import org.netgene.network.learning.optimizer.*;
import org.netgene.network.training.data.TrainingData;

/**
 *
 * @author cbaba
 */
public class DigitRecognition 
{
    private static MultiLayerNetwork multiLayerNetwork;
    
    public static void main(String [] args) throws NNException 
    {
       multiLayerNetwork = new MultiLayerNetwork.Builder()
                                         .addLayer(7)
                                         .addLayer(5)
                                         .addLayer(10)
                                         .addBiasNeurons()
                                         .build();
        
        TrainingData trainingData = new TrainingData();
        
        Double inputData0[] = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0 };
        Double outputData0[] = { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
                
        trainingData.addDataRow(inputData0, outputData0);
        
        Double inputData1[] = { 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
        Double outputData1[] = { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
                
        trainingData.addDataRow(inputData1, outputData1);
        
        Double inputData2[] = { 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0 };
        Double outputData2[] = { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
                
        trainingData.addDataRow(inputData2, outputData2);
        
        Double inputData3[] = { 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0 };
        Double outputData3[] = { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
                
        trainingData.addDataRow(inputData3, outputData3);
        
        Double inputData4[] = { 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0 };
        Double outputData4[] = { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
                
        trainingData.addDataRow(inputData4, outputData4);
        
        Double inputData5[] = { 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0 };
        Double outputData5[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
                
        trainingData.addDataRow(inputData5, outputData5);        
        
        Double inputData6[] = { 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
        Double outputData6[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 };
                
        trainingData.addDataRow(inputData6, outputData6);         
        
        Double inputData7[] = { 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
        Double outputData7[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 };
                
        trainingData.addDataRow(inputData7, outputData7);          
        
        Double inputData8[] = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
        Double outputData8[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 };
        
        trainingData.addDataRow(inputData8, outputData8); 
        
        Double inputData9[] = { 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0 };
        Double outputData9[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
                
        trainingData.addDataRow(inputData9, outputData9);         
               
        
        MSGD sgd = new MSGD();
        //Adadelta sgd = new Adadelta();
        sgd.setLearningRate(0.01);
        //sgd.setBatchMode(2);
        
        long startTime = System.currentTimeMillis();
        
        sgd.setMaxIterations(100000);
        sgd.setErrorFunction(ErrorType.MeanSquaredError, 0.01);
        sgd.learn(multiLayerNetwork, trainingData);
        //sgd.parallelLearn(multiLayerNetwork, trainingData);
        
        //backPropagation.setDecreaseFactor(startTime);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        sgd.printResults();
       
        getResult(trainingData);
        
        System.out.println("");
        System.out.println("");
        System.out.println("=============================================");
	System.out.println("Execution time in nanoseconds: " + elapsedTime);
	System.out.println("Execution time in milliseconds: " + elapsedTime / 1000000);
        System.out.println("Execution time in seconds: " + (double)(elapsedTime / 1000000)/1000);
        System.out.println("=============================================");
        
        //System.out.println("Execution time in nanoseconds: " + timeElapsed);
	//System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
        //System.out.println("Execution time in seconds: " + (double)(timeElapsed / 1000000)/1000);
        
        //multiLayerNetwork.printWeights();
        
    }
    
    public static void getResult(TrainingData trainingData)
    {
        for(int i=0; i<trainingData.size(); i++)
        {
            Double result[] = multiLayerNetwork.getNetworkOutput(trainingData.getInputData(i));
            System.out.println("------------------------------------------------------------");
            System.out.println("Digit: " + i);
            printHunableForm(result);
        }
    }
    
    public static void printHunableForm(Double [] result)
    {
        Double max = findMax(result);
        for(int i=0; i< result.length; i++)
        {
            Double value = Double.parseDouble(new DecimalFormat("##.##").format(result[i]));
            System.out.print("Digit " + i + ": " + (value * 100) + "%");
            if(max == result[i])
                System.out.println(" ***");
            else
                System.out.println("");
        }
    }
    
    public static Double findMax(Double [] result)
    {
        Double max = result[0];
        for(int i=1; i< result.length; i++)
        {
            if(max < result[i])
               max = result[i];
        }
        return max;
    }
    
    
    
}