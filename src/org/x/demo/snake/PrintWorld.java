/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.x.demo.snake;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author CBaba
 */
public class PrintWorld
{
    private World world;
    
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
    
    //-----------------------GUI-----------------------
    
    private final ArrayList<JButton> buttonList = new ArrayList();
    private final ArrayList<JButton> snake = new ArrayList();
    
    private JFrame frame;
    private final GridLayout grid;
    
    private int worldSize;
    //private int squareLength = 30;  //this should be the default value
    private int squareLength = 40;
    
    public Color defaultColor;
    
    private Border defaultBorder;
    
    private boolean humanController = false;
    
    private int oldTail = 0;
    private int oldSnakeHead = 0;
    
    private int snakeSize;
    
    public boolean isEating = false;
    
    public boolean isWinner = false;
    
    public JLabel statusLabel;
    
    private Border lineBorder;
    
    private boolean stop = false;
    
    private int locationX = 100;
    
    private int locationY = 100;
    
    public PrintWorld(int worldSize) 
    {
        this.worldSize = worldSize;
        grid = new GridLayout(this.worldSize, this.worldSize);
        
        //statusLabel.setSize(squareLength, squareLength);
        //statusLabel.setT
    }
     
    public void create() throws Exception
    {
        world = new World(worldSize);
        snakeSize = world.getSnakeSize();
        //this.worldSize = worldSize;
 
        frame = new JFrame("Snake GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(this.worldSize*squareLength, this.worldSize*squareLength);

        //grid = new GridLayout(this.worldSize, this.worldSize);
        
        lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        statusLabel = new JLabel();
        statusLabel.setText("     Snake Size: " + world.getSnakeSize() + "     ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setVerticalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(lineBorder);
        statusLabel.setFont(new Font(statusLabel.getFont().getFontName(), Font.PLAIN, 16));
        
        JPanel content = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        //content.setSize(this.worldSize*squareLength, this.worldSize*squareLength);
        content.setLayout(borderLayout);
        borderLayout.setHgap(10);
        borderLayout.setVgap(10);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setSize(this.worldSize*squareLength, this.worldSize*squareLength);
        //grid.set
        buttonsPanel.setLayout(grid);
        buttonsPanel.setBorder(lineBorder);
        //JPanel gridPanel = new JPanel();
        content.add(buttonsPanel, BorderLayout.CENTER);
        content.add(statusLabel, BorderLayout.SOUTH);
                
        JButton defaultButton = new JButton();
        defaultBorder = defaultButton.getBorder();
        
        for(int i=0; i< this.worldSize*this.worldSize; i++)
        {
            JButton button = new JButton();
            button.setBorder(null);
            //button.setSize(worldSize*squareLength, worldSize*squareLength);
            buttonList.add(button);
            button.setEnabled(false);
            buttonsPanel.add(button);
        }
        
        defaultColor = buttonList.get(0).getBackground();
        
        paintSnake();
        frame.setSize(this.worldSize*squareLength, this.worldSize*squareLength + statusLabel.getSize().width);
        frame.setContentPane(content);
        frame.setVisible(true);
        frame.setLocation(locationX, locationY);
        frame.setLocation(1030, 200);
    }
    
    private void paintSnake()
    {
        //repaintWorld();
        //buttonList.get(world.snake.get(0).getIndex()).setBackground(Color.GREEN);
        //buttonList.get(world.snake.get(0).getIndex()).setBorder(defaultBorder);
        snake.add(buttonList.get(world.snake.get(0).getIndex()));  //add head
        snake.get(0).setBackground(Color.GREEN);  //colour Head
        snake.get(0).setBorder(defaultBorder);   
        for(int i=1; i<world.snake.size(); i++)
        {
            buttonList.get(world.snake.get(i).getIndex()).setBackground(Color.red);
            buttonList.get(world.snake.get(i).getIndex()).setBorder(defaultBorder);
            snake.add(buttonList.get(world.snake.get(i).getIndex()));
        }

        buttonList.get(world.getFoodNodeNr()).setBackground(Color.blue);
             
    }
    
    
    public void moveSnake()
    {
        //old head becomes body
        snake.get(0).setBackground(Color.RED);
        snake.get(0).setBorder(defaultBorder);
        
        //add new head
        snake.add(0, buttonList.get(world.getSnakeHead()));
        snake.get(0).setBackground(Color.green);
        snake.get(0).setBorder(defaultBorder);
               
            
        if(!world.isEating)
        {
            JButton removeTail = snake.remove(snake.size()-1);
            removeTail.setBackground(defaultColor);
            removeTail.setBorder(null);
            buttonList.get(world.getFoodNodeNr()).setBackground(Color.blue);
        }
         
    }
    
    public void move(int direction)
    {
        if(!stop)
        {
            oldTail = world.getSnakeTail();
            oldSnakeHead = world.getSnakeHead();
            world.move(direction);
            world.getVision();
            moveSnake();
            statusLabel.setText("     Snake Size: " + world.getSnakeSize() + "     ");
            if(world.isWinner())
            {
                statusLabel.setText("!WINNER! Snake Size: " + world.getSnakeSize());
                stop = true;
                return;
            }
            if(world.isDead())
            {
                statusLabel.setText("!SNAKE is DEAD! Snake Size: " + world.getSnakeSize());
                stop = true;
                return;
            }
        }
        
    }
    
    
    public Double[] getVision()
    {
        return world.getVision();
    }
    
    public int getSnakeSize()
    {
        return world.getSnakeSize();
    }
    
    public void addHumanController()
    {
        humanController = true;
        frame.addKeyListener(new java.awt.event.KeyListener() {
            public void keyTyped(KeyEvent e)
            {
                //do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //if(!keyPressed)
                //{
                    boolean keyPressed = true;
                    if(e.getKeyCode() == KeyEvent.VK_UP)
                    {
                        move(MOVE_UP);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    {
                        move(MOVE_DOWN);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    {
                        move(MOVE_LEFT);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    {
                        move(MOVE_RIGHT);
                    }
             }

            @Override
            public void keyReleased(KeyEvent e) {
                //do nothing
            }
        }
        
        );
    }
    
    public boolean isWinner()
    {
        return world.isWinner();
    }
    
    public boolean isDead()
    {
        return world.isDead();
    }
    
    public void distroy()
    {
        frame.setVisible(false);
        frame.dispose();
        frame = null;
    }
        
    public void setSleep(int sleep)
    {
        
    }
    
    private void sleep(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(PrintWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

