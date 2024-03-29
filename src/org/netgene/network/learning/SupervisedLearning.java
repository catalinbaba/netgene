/*
 * Java Genetic Algorithm and Neural Network Library.
 * Copyright (c) @2020 @ Catalin Baba
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Catalin Baba (catalin.viorelbaba@gmail.com)
*/

package org.netgene.network.learning;

import java.io.Serializable;
import java.util.stream.Stream;
import org.netgene.network.NeuralNetwork;
import org.netgene.network.error.function.ErrorFunction;
import org.netgene.network.error.function.ErrorType;
import org.netgene.network.error.function.HingeError;
import org.netgene.network.error.function.MeanAbsoluteError;
import org.netgene.network.error.function.MeanAbsolutePercentage;
import org.netgene.network.error.function.MeanSquaredError;
import org.netgene.network.error.function.MeanSquaredLogarithmicError;
import org.netgene.network.exception.NNException;
import org.netgene.network.training.data.DataSet;
import org.netgene.network.training.data.TrainingData;
import org.netgene.network.stop.StopCondition;
import org.netgene.utils.ConsolePrinter;
import org.netgene.utils.Printer;

/**
 *
 * @author CBaba
 */
abstract public class SupervisedLearning extends IterativeLearning implements Serializable
{
   private static final long serialVersionUID = 1L;
   
   /**
    * Neural network to train
    */
   protected NeuralNetwork neuralNetwork;
   
   /**
    * Training data
    */
   private TrainingData trainingData;
   
    /**
    * Error function
    */
   private ErrorFunction errorFunction;
   
    /**
    * Max error stop condition
    */
   private StopCondition maxErrorStop;
   
    
    /**
    * Epoch ended event
    */
   private transient LearningEvent epochEndedEvent;
   
   private transient LearningEvent batchEvent;
   
   protected boolean batchMode = false;
   
   protected int batchSize = 1; // default value -> online learning
   
   protected Printer printer;
   
   protected IterationResult iterationResult;
   
      
    /**
     * Create a nee instance of Supervised Learning
     */
    protected SupervisedLearning()
   {
       errorFunction = new MeanSquaredError();
       //setErrorLimited(true);
       printer = new ConsolePrinter();
   }
   
   /**
    * Set Error function type and the maximum error
    *
    * @param errorType
    *   Set the error type
    * 
    * @param maxError
    *   Set the maximum error
    * 
   */
   
   public void setErrorFunction(final int errorType, final double maxError)
   {
        if(errorType == ErrorType.MeanAbsolutePercentage)
           errorFunction = new MeanAbsolutePercentage();
        else if(errorType == ErrorType.MeanAbsoluteError)
           errorFunction = new MeanAbsoluteError();
        else if(errorType == ErrorType.MeanSquaredLogarithmicError)
           errorFunction = new MeanSquaredLogarithmicError();
        else if(errorType == ErrorType.HingeError)
           errorFunction = new HingeError();
        else
           errorFunction = new MeanSquaredError();
         
        errorFunction.setMaxError(maxError);
   }
   
   /**
    * Set Error function type and the maximum error
    *
    * @param errorType
    *   Set the error type
    * 
   */
   
   public void setErrorFunction(final int errorType)
   {
        if(errorType == ErrorType.MeanAbsolutePercentage)
           errorFunction = new MeanAbsolutePercentage();
        else if(errorType == ErrorType.MeanAbsoluteError)
           errorFunction = new MeanAbsoluteError();
        else if(errorType == ErrorType.MeanSquaredLogarithmicError)
           errorFunction = new MeanSquaredLogarithmicError();
        else if(errorType == ErrorType.HingeError)
           errorFunction = new HingeError();
        else
           errorFunction = new MeanSquaredError();
   }
   
   /**
    * Set Error function type and the maximum error
    *
    * @param maxError
    *   Set the maximum error
    * 
   */
   
    public void setErrorFunction(final double maxError)
    {
        errorFunction.setMaxError(maxError);
    }
   
   /**
    * Set the condition to stop the alorithm when the max error is reached
    * 
   */
   
   public void setErrorStopCondition()
   {
       setErrorLimited(true);
   }
   
      
   /**
    * Change the default console printer
    * 
    * @param printer printer to be used
    */
   public void setPrinter(Printer printer)
   {
       this.printer = printer;
   }
   
      
   protected void setTrainingData(TrainingData trainingData)
   {
       this.trainingData = trainingData;
   }
   
   public double getOutputError()
   {
       return this.iterationResult.getOutputError();
   }
      
   protected void setNeuralNetwork(NeuralNetwork neuralNetwork)
   {
       this.neuralNetwork = neuralNetwork;
   }
    
    protected final boolean hasReachedStopCondition(IterationResult iterationResult) 
    {
        if (stopConditions.stream().anyMatch((stopCondition) -> (stopCondition.isReached(iterationResult)))) 
           return true;
        return false;
    }
        
    protected void setErrorLimited(boolean errorLimited)
    {
        maxErrorStop = (result) ->
        {
            return result.getOutputError() <= errorFunction.getMaxError();
        };
        this.stopConditions.add(maxErrorStop);
    }
     
   @Override
    protected void learn()
    {
        currentIteration = 0;
        Stream.generate(() -> {return executeIteration();})
              .takeWhile(result ->
              {
                  return !hasReachedStopCondition(result);
              }) 
              //.limit(1000)
              .forEach(result -> {});
        //threadPool.shutdown();
    }
    
    public abstract void parallelLearn(NeuralNetwork neuralNetwork, TrainingData trainingData) throws NNException;
    
    private IterationResult executeIteration()
    {
        DataSet outputDataSet = new DataSet();
        for(int i=0; i<trainingData.size(); i++)
        {
            //feed forward
            outputDataSet.addDataRow(neuralNetwork.getNetworkOutput(trainingData.getInputData(i)));
            //calculate neuron gradients
            calculateNeuronGradients(trainingData.getTargetData(i)); 
            if((i+1)%batchSize == 0 || i == trainingData.size()-1)  //for online mode, batch size is always 1
            {
                updateNetworkWeights();  
            }
        } 
        currentIteration++;

        iterationResult = new IterationResult(outputDataSet, 
                                                              errorFunction.calculateError(outputDataSet, trainingData.getTagetDataSet()), 
                                                              currentIteration);
        //System.out.println("error: " + iterationResult.getOutputError());
        if(epochEndedEvent != null)
            epochEndedEvent.handleEvent(iterationResult);
        return iterationResult;
    }
    
    protected void setEpochEvent(LearningEvent epochEvent)
    {
        this.epochEndedEvent = epochEvent;
    }
   
      
    private void printResults(Double[] inputData, Double[] result)
    {
        printer.print("Inputs: ");
        for (Double inputData1 : inputData) 
           printer.print(inputData1 + " ");
        printer.println();
        printer.print("Outputs: ");
        for (Double result1 : result)
           printer.print(result1 + " ");
        printer.println();
    }
   
    /**
     *  Print the training results using the Printer
     */
    public void printResults()
    {
        printer.println();
        printer.println("-----------------------------------");
        printer.println("Results after training");
        printer.println("Output Error: " + iterationResult.getOutputError());
        printer.println("steps: " + currentIteration);
        printer.println("-----------------------------------");
        for(int i=0; i<trainingData.size(); i++)
            printResults(trainingData.getInputData(i), neuralNetwork.getNetworkOutput(trainingData.getInputData(i)));
    }
   
      
   abstract protected void updateNetworkWeights();
   
   abstract protected void calculateNeuronGradients(Double[] targetData);
   
}
