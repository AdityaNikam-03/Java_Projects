//This is file for creating JPanel
//Where we can draw our game
package Tools;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game_Panel extends JPanel implements ActionListener,KeyListener{
    
    public class Tile{
        int x;
        int y;
        
        Tile(int x,int y)
        {
            this.x=x;
            this.y=y;
        }
    }
    
    int boardwidth;
    int boardheight;
    int tileSize=25;
    
    //Snake
    Tile snakehead;
    ArrayList<Tile> snbody;
    
    //Food
    Tile food;
    
    //Random class object to place food randomly on screen
    Random random;
    
    //Game Logic
    Timer gmloop;
    
    int velX;
    int velY;
    
    boolean gmover=false;
    
    public Game_Panel(int boardwidth,int boardheight)
    {
        this.boardwidth=boardwidth;
        this.boardheight=boardheight;
        setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        
        snakehead=new Tile(5,5);
        snbody=new ArrayList<Tile>();
        
        food=new Tile(10,10);
        
        random=new Random();
        placefood();
        
        velX=0;
        velY=1;
        
        gmloop=new Timer(100,this);
        gmloop.start();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        
        for(int i=0;i<boardwidth/tileSize;i++){
            g.drawLine(i*tileSize,0,i*tileSize,boardheight);
            g.drawLine(0,i*tileSize,boardwidth,i*tileSize);
        }
        
        //food
        g.setColor(Color.RED);
        g.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);
        
        //snake
        g.setColor(Color.GREEN);
        g.fillRect(snakehead.x*tileSize,snakehead.y*tileSize,tileSize,tileSize);
    
        //Snake Body
        for(int i=0;i<snbody.size();i++){
            Tile snpart=snbody.get(i);
            g.fillRect(snpart.x*tileSize,snpart.y*tileSize,tileSize,tileSize);
        }
        
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gmover){
            g.setColor(Color.red);
            g.drawString("Game Over : "+String.valueOf(snbody.size()),tileSize-16,tileSize);
        }else{
            g.drawString("Score : "+String.valueOf(snbody.size()),tileSize-16,tileSize);
        }
    }
    
    public void placefood(){
        food.x=random.nextInt(boardwidth/tileSize);
        food.y=random.nextInt(boardheight/tileSize);
    }
    
    //Function to detect collision b/w snakehead and tile
    public boolean collision(Tile t1,Tile t2){
        return t1.x==t2.x && t1.y==t2.y;
    }
    
    public void move(){
        //eat food
        if(collision(snakehead,food)){
            snbody.add(new Tile(food.x,food.y));
            placefood();
        }
        
        //Snake Body
        for(int i=snbody.size()-1;i>=0;i--){
            Tile snpart=snbody.get(i);     
            if(i==0){
                snpart.x=snakehead.x;
                snpart.y=snakehead.y;
            }else{
                Tile presnpart=snbody.get(i-1);
                snpart.x=presnpart.x;
                snpart.y=presnpart.y;
            }
        }
        
        //snake head
        snakehead.x+=velX;
        snakehead.y+=velY;
        
        //Game over condition for snake game
        for(int i=0;i<snbody.size();i++){
            Tile snpart=snbody.get(i);
            if(collision(snakehead,snpart)){
                gmover=true;
            }
        }
        
        if(snakehead.x*tileSize < 0 || snakehead.x*tileSize>boardwidth || snakehead.y*tileSize<0 || snakehead.y*tileSize > boardheight){
            gmover=true;
        }
    }
    
    //@override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        
        if(gmover){
            gmloop.stop();
        }
    }
    //@override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_UP && velY!=1){
            velX=0;
            velY=-1;
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN && velY!=-1){
            velX=0;
            velY=1;
        }else if(e.getKeyCode()==KeyEvent.VK_LEFT && velX!=1){
            velX=-1;
            velY=0;
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velX!=-1){
            velX=1;
            velY=0;
        }
    }
    
    
    //do not needed
    //@override
    public void keyTyped(KeyEvent e){}    
    //@override
    public void keyReleased(KeyEvent e){}    
}
