/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.x.demo.snake;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author CBaba
 */

public class World 
{
    private ArrayList<Node> nodes = new ArrayList();
    public ArrayList<Node> snake = new ArrayList();
    
    public int snakeHead;
    
    private int worldSize;
    
    private int snakeSize = 3;
    
    public int movingDirection = 0;
    
    private final int MOVE_UP = 0;
    private final int MOVE_LEFT = 1;
    private final int MOVE_RIGHT = 2;
    private final int MOVE_DOWN = 3;
    
    private final int LOOK_UP = 0;
    private final int LOOK_LEFT = 1;
    private final int LOOK_RIGHT = 2;
    private final int LOOK_DOWN = 3;
    private final int LOOK_UP_LEFT = 4;
    private final int LOOK_UP_RIGHT = 5;
    private final int LOOK_DOWN_LEFT = 6;
    private final int LOOK_DOWN_RIGHT = 7;
    
    public boolean isEating = false;
    
    private boolean isDead = false;
    
    private boolean hitTheWall = false;
    
    private final int initialSize = 3;
    
    private int foodNodeNr;
    
    private int move = 0;

      
    public World(int worldSize) throws Exception
    {
        if(worldSize < 7)
            throw new Exception("World size cannot be smallar than 7");
        this.worldSize = worldSize;
        for(int i=0; i< worldSize*worldSize; i++)
        {
            Node node = new Node(i);
            nodes.add(node);
        }
        
        createSnake();
        createFood();
     }
   
    private void createSnake()
    {
        snake.clear();  // remove if one snake exists before
        snakeHead = worldSize * (2 * worldSize/3) + (2 * worldSize/3);
          
        for(int i = 0; i < initialSize; i++)
        {
            snake.add(nodes.get(snakeHead + i * worldSize));
        }
        movingDirection = MOVE_UP;
        isDead = false;
    }
    
    /*
     1 - UP
     2 - LEFT
     3 - RIGHT
     4 - DOWN
    */
    
    public void move(int direction) 
    {
        move++;
        //check if move is valid
        if(direction == MOVE_UP && movingDirection == MOVE_DOWN)
        {
            isDead = true;
            return; //do nothing, cannot change the moving direction
        }
        if(direction == MOVE_DOWN && movingDirection == MOVE_UP)
        {
            isDead = true;
            return; //do nothing, cannot change the moving direction
        }
        if(direction == MOVE_LEFT && movingDirection == MOVE_RIGHT)
        {
            isDead = true;
            return; //do nothing, cannot change the moving direction
        }
        if(direction == MOVE_RIGHT && movingDirection == MOVE_LEFT)
        {
            isDead = true;
            return; //do nothing, cannot change the moving direction
        }
                
        if(checkCollision(direction))
        {
            isDead = true;
            return;
        }

        switch(direction) 
        {
            case MOVE_UP:   //up
               snakeHead = snakeHead - worldSize;
               break;
            case MOVE_LEFT:  //left
               snakeHead = snakeHead - 1;
               break;
            case MOVE_RIGHT:  //right
               snakeHead = snakeHead + 1;
               break;
            case MOVE_DOWN:
               snakeHead = snakeHead + worldSize;
               break;
        }
        
        if(snake.contains(nodes.get(snakeHead)))
        {
            isDead = true;
            return;
        }
        
                                             
        snake.add(0, nodes.get(snakeHead));
                  
        //if(foodNodeNr == snakeHead)
        if(nodes.get(snakeHead).isFood())
        {
            nodes.get(snakeHead).setIsFood(false);
            isEating = true;
            if(isWinner())
              return;
            createFood();
        }
        else
        {
            isEating = false;
        }
                      
        if(!isEating)
        {
            snake.remove(snake.size()-1);  //remove the tail
        }
        
              
        movingDirection = direction; 
  
    }
    
    private boolean checkCollision(int direction) 
    {
        //check if snake hits the wall
        if(direction == MOVE_UP && snakeHead < worldSize)
            return true;
        if(direction == MOVE_LEFT && snakeHead % worldSize == 0)
            return true;
        if(direction == MOVE_RIGHT  && (snakeHead % worldSize == worldSize -1))
            return true;
        if(direction == MOVE_DOWN && (snakeHead + worldSize > worldSize * worldSize - 1))
            return true;
    
        return false;
    } 
    
    public void createFood()
    {
        ArrayList<Node> cloneList =  (ArrayList)nodes.clone();
        
        for(int i = 0; i<snake.size(); i++)
        {
            cloneList.remove(snake.get(i));
        }
        
        Random random = new Random();
        
        int nextInt = random.nextInt(cloneList.size());
        
        Node food = cloneList.get(nextInt);
        
        foodNodeNr = food.getIndex();
 
        food.setIsFood(true);

    }
    
    public Double[] getVision()
    {
        Double vision[] = new Double[24];
        
        boolean foodIsFound = false;
        
        double directionVision[] = new double[3];

        vision[0] = distanceToWall(this.LOOK_UP);
        //System.out.println("distance wall up: " + vision[0]);
        vision[1] = distanceToBody(this.LOOK_UP);
        vision[2] = distanceToFood(this.LOOK_UP);
        //System.out.println("food wall up: " + vision[2]);
        
        vision[3] = distanceToWall(this.LOOK_LEFT);
        //System.out.println("distance wall left: " + vision[3]);
        vision[4] = distanceToBody(this.LOOK_LEFT);
        vision[5] = distanceToFood(this.LOOK_LEFT);
        //System.out.println("food wall left: " + vision[5]);
          
        vision[6] = distanceToWall(this.LOOK_RIGHT);
        //System.out.println("distance wall right: " + vision[6]);
        vision[7] = distanceToBody(this.LOOK_RIGHT);
        vision[8] = distanceToFood(this.LOOK_RIGHT);
        //System.out.println("food wall right: " + vision[8]);
        
        vision[9] = distanceToWall(this.LOOK_DOWN);
        //System.out.println("distance wall down: " + vision[9]);
        vision[10] = distanceToBody(this.LOOK_DOWN);
        vision[11] = distanceToFood(this.LOOK_DOWN);
        //System.out.println("food wall down: " + vision[11]);
        
        vision[12] = distanceToWall(this.LOOK_UP_LEFT);
        //System.out.println("distance wall up left: " + vision[12]);
        vision[13] = distanceToBody(this.LOOK_UP_LEFT);
        vision[14] = distanceToFood(this.LOOK_UP_LEFT);
        //System.out.println("food wall up left: " + vision[14]);
         
        vision[15] = distanceToWall(this.LOOK_UP_RIGHT);
        //System.out.println("distance wall up right: " + vision[15]);
        vision[16] = distanceToBody(this.LOOK_UP_RIGHT);
        vision[17] = distanceToFood(this.LOOK_UP_RIGHT);
        //System.out.println("food wall up right: " + vision[17]);
        
        vision[18] = distanceToWall(this.LOOK_DOWN_LEFT);
        //System.out.println("distance wall down left: " + vision[18]);
        vision[19] = distanceToBody(this.LOOK_DOWN_LEFT);
        vision[20] = distanceToFood(this.LOOK_DOWN_LEFT);
        //System.out.println("food wall down left: " + vision[20]);
        
        vision[21] = distanceToWall(this.LOOK_DOWN_RIGHT);
        //System.out.println("distance wall down right: " + vision[21]);
        vision[22] = distanceToBody(this.LOOK_DOWN_RIGHT);
        vision[23] = distanceToFood(this.LOOK_DOWN_RIGHT);
        //System.out.println("food wall down right: " + vision[23]);

        return vision; 
    }
    
    public double distanceToWall(int direction)
    {
        double distance = 0;
        switch(direction)
        {
            case LOOK_UP:
                distance = snakeHead / worldSize;
                break;
            case LOOK_DOWN:
                distance = worldSize - snakeHead / worldSize - 1;
                break;
            case LOOK_LEFT:
                distance = snakeHead % worldSize;
                break;
            case LOOK_RIGHT:
                distance = worldSize - snakeHead % worldSize - 1;
                break;
            case LOOK_UP_LEFT:
                distance = Math.min(snakeHead / worldSize, snakeHead % worldSize);
                break;
            case LOOK_UP_RIGHT:
                distance = Math.min(snakeHead / worldSize, worldSize - snakeHead % worldSize - 1);
                break;
            case LOOK_DOWN_LEFT:
                distance = Math.min(worldSize - snakeHead / worldSize - 1, snakeHead % worldSize);
                break;
            case LOOK_DOWN_RIGHT:
                distance = Math.min(worldSize - snakeHead / worldSize - 1, worldSize - snakeHead % worldSize - 1);
                break;
        };
        
        distance = distance + 1; //since we normalize
        
        distance = 1/distance;
           
        return distance;
    }
    
    public double distanceToBody(int direction)
    {
        double distance = 0;
        boolean maxCount = true;
        int i = snakeHead;
        while(true)
        {
            switch(direction)
            {
                case LOOK_UP:
                    i = i - worldSize;
                    maxCount = (i >= 0);
                break;
                case LOOK_LEFT:
                    i = i - 1;
                    maxCount = (i % worldSize != worldSize - 1) && (i >= 0);  // - 1
                break;
                case LOOK_RIGHT:
                    i = i + 1;
                    maxCount = (i % worldSize != 0) && (i < worldSize * worldSize -1);
                break;
                case LOOK_DOWN:
                    i = i + worldSize;
                    maxCount = i < worldSize * worldSize - 1;
                break;
                case LOOK_UP_LEFT:
                    i = i - worldSize - 1;
                    maxCount = (i >= 0) && (i % worldSize != worldSize - 1);
                break;
                case LOOK_UP_RIGHT:
                    i = i - worldSize + 1;
                    maxCount = (i >= 0) && (i % worldSize != 0) && (i < worldSize * worldSize -1);
                break;
                case LOOK_DOWN_LEFT:
                    i = i + worldSize - 1;
                    maxCount = (i < worldSize * worldSize - 1) && (i % worldSize != worldSize - 1) && (i >= 0);
                break;
                case LOOK_DOWN_RIGHT:
                    i = i + worldSize + 1;
                    maxCount = (i < worldSize * worldSize - 1) && (i % worldSize != 0);
                break;
            }
            
            if(!maxCount)
                break;
            
            distance++;
            
            if(snake.contains(nodes.get(i)))
            {
                return 1/distance;
            }
        }
        
        return 0;
    }
    
    
    public double distanceToFood(int direction)
    {   
        int distance = 1;
        int snakeLineX = snakeHead % worldSize;
        int foodLineX = foodNodeNr % worldSize;
        
        int snakeLineY = snakeHead / worldSize ;
        int foodLineY = foodNodeNr / worldSize;
        
        double dx = Math.abs(snakeLineX - foodLineX);
        double dy = Math.abs(snakeLineY - foodLineY);
                
        if(direction == this.LOOK_UP)
        {
            if(dx == 0 && foodLineY < snakeLineY )
                 return distance;
        }
        if(direction == this.LOOK_LEFT)
        {
            if(dy == 0 && foodLineX < snakeLineX)
                return distance ;
        }
        if(direction == this.LOOK_RIGHT)
        {
            if(dy == 0 && foodLineX > snakeLineX)
                return distance ;
        }
        if(direction == this.LOOK_DOWN)
        {
            if(dx ==0 && foodLineY > snakeLineY)
                return distance ;
        }
        
        if(direction == LOOK_UP_LEFT && dx == dy && foodLineX < 0)  
        {
            return distance ;
        }
        if(direction == LOOK_UP_RIGHT && dx == dy && foodLineX > 0)
        {
            return distance ;
        }
        if(direction == LOOK_DOWN_LEFT && dx == dy && foodLineX < 0)
        {
            return distance ;
        }
        if(direction == LOOK_DOWN_RIGHT && dx == dy && foodLineX > 0)
        {
            return distance ;
        }
 
        return 0;
    }
        
    public int getSnakeHead()
    {
        return this.snakeHead;
    }
    
    public int getSnakeSize()
    {
        return snake.size();
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public int getSnakeTail()
    {
        return snake.get(snake.size()-1).getIndex();
    }
  
    public int getMovingDirection()
    {
        return this.movingDirection;
    }
    
    public int getFoodNodeNr()
    {
        return this.foodNodeNr;
    }
    
    public boolean isWinner()
    {
        if(snake.size() == worldSize * worldSize)
            return true;
        return false;
      }
 
}

