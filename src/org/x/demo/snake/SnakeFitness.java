/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.x.demo.snake;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.netgene.ga.Individual;
import org.netgene.ga.Population;
import org.netgene.ga.chromosome.DoubleChromosome;
import org.netgene.ga.fitness.FitnessFunction;
import org.netgene.network.MultiLayerNetwork;
import org.netgene.network.exception.NNException;

/**
 *
 * @author CBaba
 */
public class SnakeFitness implements FitnessFunction
{
    private final int worldSize = 10;
    
    @Override
    public void calculateFitness(Individual individual)  
    {
        DoubleChromosome chromosome = (DoubleChromosome)individual.getChromosome();
        double weights[] = chromosome.toArray();
        MultiLayerNetwork multiLayerNetwork = null;
        try {
            multiLayerNetwork = BrainGenerator.generateBrain();
            multiLayerNetwork.setNetworkWeights(weights);
        } catch (NNException ex) {
            System.out.println(ex.toString());;
        }
        Double inputs[] = new Double[24];
        int lifetime = 0;
        int snakeSize = 0;
        //int leftToLive = 20;
        //int step = 30;
        // world size=10 -> leftToLive = 20 and step = 30
        // world size=20 -> leftToLive = 80 and step = 120
        // world size=30 -> leftToLive = 180 and step = 300
        int leftToLive = 20;
        int step = 30;
        
        //---------------dsdasd
        //step = 20;
        
        World world = null;
        try {
            world = new World(worldSize);
        } catch (Exception ex) {
            Logger.getLogger(SnakeFitness.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(!world.isDead() && !world.isWinner())
        {
            snakeSize = world.getSnakeSize();
            inputs = world.getVision();
            Double outputs[] = multiLayerNetwork.getNetworkOutput(inputs);
            
            int direction = 0;
            double max = 0;
            for(int i=0; i<outputs.length; i++)
            {
                if(outputs[i] > max)
                {
                    max = outputs[i];
                    direction = i;
                }
            }
            //try
            //{
               world.move(direction);
            //}
            //catch(Exception e)
            //{
                
            //}
            lifetime++;
            
            if(snakeSize == world.getSnakeSize())
            {
                leftToLive--;
            }
            else
            {
                //if(world.getSnakeSize()<100)
                  leftToLive= leftToLive + step;
                //else
                //  leftToLive= leftToLive + 200;
            }
            
            if(leftToLive == 0)
            {
                break;
            }
        }
        
        snakeSize = world.getSnakeSize(); 
        
        double fitness = 0;
         
        if(world.getSnakeSize() <= 9)
        {
            fitness = lifetime * Math.pow(2,world.getSnakeSize());
        }
        else 
        {
            fitness = lifetime * Math.pow(2, 10) * world.getSnakeSize();
        }
        
        individual.setCustomData(world.getSnakeSize());
        individual.setFitnessScore(fitness);
        //world = null;
    }
    
  
}