/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netgene.ga.selection.parent;

import java.io.Serializable;
import org.netgene.ga.Individual;
import org.netgene.ga.Population;
import org.netgene.ga.exception.SelectionException;
import org.netgene.utils.RandomUtils;

/**
 *
 * @author cbaba
 */
public class CompetitionSelector extends ParentSelector implements Serializable
{

    @Override
    protected Individual select(Population population) throws SelectionException {
        
        Population selectFrom = (Population)population.clone();
        Individual ind1 =selectFrom.getRandomIndividual();
        selectFrom.removeIndividual(ind1);
        Individual ind2 = selectFrom.getRandomIndividual();
        Individual fittest, weakest;
        
        
        if(ind1.getFitnessScore() > ind2.getFitnessScore())
        {
            fittest = ind1;
            weakest = ind2;
        }
        else
        {
            fittest = ind2;
            weakest = ind1;
        }
        
        double selectionRate = 0.85;
         double rand = RandomUtils.nextDouble();
            if(rand < selectionRate) //is selected to be mutated
                return fittest;
            else
                return weakest;
        
    }
    
}
