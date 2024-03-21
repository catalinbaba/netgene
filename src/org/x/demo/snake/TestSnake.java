/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.x.demo.snake;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.netgene.network.MultiLayerNetwork;
import org.netgene.network.exception.NNException;

/**
 *
 * @author CBaba
 */
public class TestSnake 
{
    public static void main(String [] args) throws Exception
    {
       MultiLayerNetwork multiLayerNetwork = new MultiLayerNetwork()
                                         .addLayer(24)
                                         .addLayer(15)
                                         .addLayer(4)
                                         .addBiasNeurons()
                                         .build();
        
        multiLayerNetwork.loadNetwork("legend.txt");
        
        PrintWorld world = new PrintWorld(20);
        //world.setSquaresSize(10);
        world.create();
        
        Double inputs[];
        int snakeSize = 0;
        Double outputs[];
        
            
        while(!world.isDead() && !world.isWinner())
        {
            inputs = world.getVision();
            //System.out.println("inputs" + System.currentTimeMillis());
            snakeSize = world.getSnakeSize();
            outputs = multiLayerNetwork.getNetworkOutput(inputs);
                
            int direction = 0;
            double max = 0;
            for(int j=0; j<outputs.length; j++)
            {
                if(outputs[j] > max)
                {
                    max = outputs[j];
                    direction = j;
                }
            }
            
            //System.out.println("outputs");
            
            //try
            //{
                world.move(direction);
                sleep(10);
            //    if(world.isWinner())
            //        break;
            //}
            //catch(Exception e)
            //{
                //System.out.println("snake is dead!");
            //}
                
            //System.out.println("end");
            //sleep(1000);
        }
        
        System.out.println("Snake size: " + world.getSnakeSize());
           
        //System.out.println("exiiiiiittttttt");    
            //world.distroy();
        
        //sleep(1000);
    }
    
    private static void sleep(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(PrintWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
