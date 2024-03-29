/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netgene.ga.chromosome;

import java.util.ArrayList;
import org.netgene.ga.gene.Gene;

/**
 *
 * @author Catalin Baba
 * 
 * @param <G> Gene type
 */
public abstract class Chromosome<G extends Gene> 
{
    protected ArrayList<G> chromosome;// = new ArrayList<>();
    
    public abstract G getGene(int index);
    
    public abstract void addGene(G gene);
    
    public abstract void addGene(int index, G gene);
    
    public abstract void setGene(int index, G gene);
    
    public abstract ArrayList<G> getChromosome();
    
    public abstract void setChromosome(ArrayList<G> chromosome);
    
    public abstract int length();
    
    public abstract Chromosome<G> copy();
    
    public abstract boolean contains(G gene);
         
}
