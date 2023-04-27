/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.x.demo.snake;


/**
 *
 * @author CBaba
 */
public class Node 
{
    private boolean isFood = false;
    private boolean isSnake = false;
    
    private int index = 0;
   
    public Node(int index)
    {
        this.index = index;
    }
    
    
     public void setIsFood(boolean isFood) {
        this.isFood = isFood;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public boolean isSnake() {
        return isSnake;
    }

    public void setIsSnake(boolean isSnake) {
        this.isSnake = isSnake;
    }
    
     public boolean isFood() {
        return isFood;
    }

    public int getIndex() {
        return index;
    }
    
    public String toString()
    {
        return "Node: " + getIndex();
    }
    
}
