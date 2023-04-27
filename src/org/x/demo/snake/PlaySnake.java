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
public class PlaySnake 
{
    public static void main(String[] args) throws Exception
    {
        PrintWorld snakeGui = new PrintWorld(20);
        snakeGui.create();
         
        //CheckAction checkAction = new CheckAction();
        
        snakeGui.addHumanController();
        
        //snakeGui.move(0);
        
        
    }
}
