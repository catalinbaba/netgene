
package org.x.demo.snake;

import org.netgene.network.MultiLayerNetwork;

/**
 *
 * @author Catalin Baba
 */
public class BrainGenerator 
{
    public static MultiLayerNetwork generateBrain() 
    {
        
        MultiLayerNetwork multiLayerNetwork = new MultiLayerNetwork()
                                         .addLayer(24)
                                         .addLayer(15)
                                         .addLayer(4)
                                         .addBiasNeurons()
                                         .build();
        
        return multiLayerNetwork; 
    }
}
