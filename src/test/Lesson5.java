/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.netgene.network.MultiLayerNetwork;
import java.text.DecimalFormat;
import org.netgene.network.error.function.ErrorType;
import org.netgene.network.learning.optimizer.*;
import org.netgene.network.training.data.TrainingData;

/**
 *
 * @author cbaba
 */
public class Lesson5
{
    private static MultiLayerNetwork multiLayerNetwork;
    
    public static void main(String [] args) throws Exception
    {
        multiLayerNetwork = new MultiLayerNetwork()
                                         .addLayer(7)
                                         .addLayer(10)
                                         .addLayer(10)
                                         .addBiasNeurons()
                                         .build();
        
        TrainingData trainingData = new TrainingData();
        //digit 0
        Double inputData0[] = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0 };
        Double outputData0[] = { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        trainingData.addDataRow(inputData0, outputData0);
        //digit 1
        Double inputData1[] = { 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
        Double outputData1[] = { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        trainingData.addDataRow(inputData1, outputData1);
        //digit 2
        Double inputData2[] = { 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0 };
        Double outputData2[] = { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        trainingData.addDataRow(inputData2, outputData2);
        //digit 3 
        Double inputData3[] = { 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0 };
        Double outputData3[] = { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        trainingData.addDataRow(inputData3, outputData3);
        //digit 4
        Double inputData4[] = { 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0 };
        Double outputData4[] = { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        trainingData.addDataRow(inputData4, outputData4);
        //digit 5
        Double inputData5[] = { 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0 };
        Double outputData5[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
        trainingData.addDataRow(inputData5, outputData5);        
        //digit 6
        Double inputData6[] = { 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
        Double outputData6[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 };
        trainingData.addDataRow(inputData6, outputData6);         
        //digit 7
        Double inputData7[] = { 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
        Double outputData7[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 };
        trainingData.addDataRow(inputData7, outputData7);          
        //digit 8
        Double inputData8[] = { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
        Double outputData8[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 };
        trainingData.addDataRow(inputData8, outputData8); 
        //digit 9
        Double inputData9[] = { 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0 };
        Double outputData9[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
        trainingData.addDataRow(inputData9, outputData9);         
               
       
        SGD sgd = new SGD();
      
        sgd.setMaxIterations(100000);
        sgd.setErrorStopCondition();
        sgd.learn(multiLayerNetwork, trainingData);
                    
        sgd.printResults();
        printResult(trainingData);
        
       multiLayerNetwork.saveNetwork("C:\\netgene\\myNet.txt");
    }
    
    public static void printResult(TrainingData trainingData)
    {
        for(int i=0; i<trainingData.size(); i++)
        {
            Double result[] = multiLayerNetwork.getNetworkOutput(trainingData.getInputData(i));
            System.out.println("------------------------------------------------------------");
            System.out.println("Digit: " + i);
            humanReadable(result);
        }
    }
    
    private static void humanReadable(Double [] result)
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