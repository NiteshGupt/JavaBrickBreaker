/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class JavaApplication2 extends JComponent implements ActionListener, MouseMotionListener, KeyListener {

    private int ballx = 1000;
    private int bally = 200;

    private int paddlex = 0;
    private int ballySpeed = 6;
    private int ballxSpeed = 4;

    public boolean gameOver=false, started;
    public int brokeX;
    public int brokeY;
    public int[] id=new int[brickno];
    public int brokebyX=0;
    public int brokebyY=0;
    private static int brickno=25;
    private static int gameMin=0;
    private static int gameSec=30;
    Brick[] b=new Brick[brickno];
    static Timer gg=new Timer(1000,new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent p){
           gameSec--;
           if(gameSec<=0){
               gameMin--;
               gameSec=59;
           }
           }
       });
    static JavaApplication2 ggg;
    public static void main(String[] args) {

        JFrame wind = new JFrame("Nitesh");    
        ggg = new JavaApplication2();
        
        wind.add(ggg);
        wind.pack();
        wind.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        wind.setLocationRelativeTo(null);
        wind.setVisible(true);   
        wind.addMouseMotionListener(ggg);
        wind.addKeyListener(ggg);
        wind.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
  
        Timer tt = new Timer(1,ggg);
        tt.start();
       
        
        gg.start();
        int k=0;
        
        for(int j=200;k<9;j+=52,k++)ggg.b[k]= new Brick(j,0,50);
        for(int j=200;k<16;j+=52,k++)ggg.b[k]= new Brick(j,52,50,Color.green);
        for(int j=200;k<21;j+=52,k++)ggg.b[k]= new Brick(j,104,50);
        for(int j=200;k<24;j+=52,k++)ggg.b[k]= new Brick(j,156,50,Color.green);
        for(int j=200;k<25;j+=52,k++)ggg.b[k]= new Brick(j,208,50);
      
       /* for(int i=0;i<1220&&k<brickno;i+=52){
            for(int j=200;j<=(9-2*i)*52+200&&k<brickno;j+=52,k++){
                g.b[k]= new Brick(j,i,50);               
            }
        }*/
       

    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(1280, 712);
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        
        //draw the sky
        g.setColor(Color.black);
        g.fillRect(0, 0, ggg.getWidth(), ggg.getHeight());
       
        for(int i=0;i<brickno;i++){
            if(id[i] !=1) b[i].paintBrick(g);
        }
        if(brokebyX==1||brokebyY==1){
            g.clearRect(brokeX,brokeY,50,50);
            if(brokebyX==1)brokebyX=0;
            else if(brokebyY==1)brokebyY=0;
            
        }

        //draw the paddel
        g.setColor(Color.GRAY);
        g.fillRect(paddlex, 600, 100, 20);

        //draw the ball
        g.setColor(Color.RED);
        g.fillOval(ballx, bally, 30, 30);

     
        
        // start && gameOver
        

        if (gameMin<0) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", 6, 60));
        g.drawString(String.valueOf("Time Up!"), 570, 300);
        int i=0;
        for(int j=0;j<brickno;j++){if(id[j]!=0)i++;}
        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", 6, 60));
        g.drawString(String.valueOf("Score: "+i+"/"+brickno), 550, 360);
        gg.stop();
        gameMin=0;
        gameSec=0;
        gameOver=true;

        }
        g.setColor(Color.gray);
        g.setFont(new Font("Arial",7,30));
        g.drawString(String.valueOf("Time Left:"),15,650);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 8, 50));
        if(gameSec<10)
        g.drawString(String.valueOf(gameMin+":0" + gameSec), 15, 700);
        else g.drawString(String.valueOf(gameMin+":" + gameSec), 15, 700);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver==false){
            ballx = ballx + ballxSpeed;
            bally = bally + ballySpeed;

            // Window Down 
            if (ballx >= paddlex && ballx <= paddlex + 100 && bally >= 570&&bally<=580) {

                ballySpeed = -6;

            }

            for(int i=0;i<brickno;i++){
                if(b[i]==null)continue;
                if(ballx>=b[i].getX()&&ballx<=b[i].getX()+50){
                    if(bally>=b[i].getY()-30&&bally<=b[i].getY()+50+30){
                        ballySpeed=(ballySpeed>0)?-6:6;
                        brokeX=b[i].getX();
                        brokeY=b[i].getY();
                        id[i]++;
                        brokebyX=1;
                        repaint();
                        b[i]=null;                  
                        break;
                    }

                }

                if(bally>=b[i].getY()&&bally<=b[i].getY()+50){

                    if(ballx>=b[i].getX()-30&&ballx<=b[i].getX()+50+30){

                        ballxSpeed=(ballxSpeed>0)?-4:4;
                        brokeX=b[i].getX();
                        brokeY=b[i].getY();
                        id[i]++;
                        brokebyY=1;
                        repaint();
                        b[i]=null;                   
                        break;
                    }

                }


            }
            if (bally >= ggg.getHeight() ) {
                
                gameOver = true;

            }

            // Window up
            if (bally <= 0) {

                ballySpeed = 6;

            }

            // Window right
            if (ballx >= ggg.getWidth()) {

                ballxSpeed = -4;

            }

            // Window left
            if (ballx <= 0) {

                ballxSpeed = 4;

            }      
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(e.getX()<=0) paddlex = 0;
        else if(e.getX()>=ggg.getWidth()-70) paddlex = ggg.getWidth()-70;
        else paddlex=e.getX();
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='a'||e.getKeyChar()=='A'){
            if(paddlex<=0) paddlex = 0;
            else paddlex-=20;
            repaint();
        }
        else if(e.getKeyChar()=='D'||e.getKeyChar()=='d'){
      
            if(paddlex>=1180) paddlex = 1180;
            else paddlex+=20;
            repaint();
        }
      
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}

class Brick {
            
    private final int side;
    private final int x;
    private final int y;
    private final Color co;
    public Brick(int s){
        x=0;
        y=0;
        side=s;
        co=Color.white;
    }
    public Brick(int X,int Y,int s){
        x=X;
        y=Y;
        side=s;
        co=Color.white;
    }
    public Brick(int X,int Y,int s,Color col){
        x=X;
        y=Y;
        side=s;
        co=col;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void paintBrick(Graphics g){
        g.setColor(co);
        g.fillRect(x, y, side, side);
        
    }
        

}
