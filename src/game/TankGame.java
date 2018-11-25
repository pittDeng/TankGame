package game;

import tank.Enemy;
import tank.Hero;
import tank.Parameter;
import tank.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class TankGame extends JFrame {
    public static int width= Parameter.windowWidth;
    public static int height=Parameter.windowHeight;
    public static void main(String [] args){
        TankGame tg=new TankGame();
    }
    /*
    * 设置窗口参数*/
    public TankGame(){
        MyPanel mp=new MyPanel(this);
        Thread th=new Thread(mp);
        th.start();
        this.add(mp);
        this.addKeyListener(mp);
        this.setTitle("The War of Tanks");
        this.setSize(width,height);
        this.setLocation(100,100);
        this.setFocusable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class MyPanel extends JPanel implements KeyListener,Runnable {
    Hero hero;
    Vector<Enemy> enemies;
    private static int sleepTime=Parameter.flushTime;
    private int numEnemies=3;
    private int width;
    private int height;
    private TankGame tg;
    public MyPanel(TankGame tg){
        this.width=tg.getWidth();
        this.height=tg.getHeight();
        this.tg=tg;
        hero=new Hero(10,10);
        enemies=new Vector<Enemy>();
        for(int i=0;i<numEnemies;++i){
            enemies.add(new Enemy(50*i,50));
        }

    }
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,tg.getWidth(),tg.getHeight());
        hero.paint(g);
        for(int i=0;i<numEnemies;++i){
            enemies.get(i).paint(g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("KeyPressed");
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W: hero.setDir(0);
                                hero.moveUp();
                                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: hero.setDir(1);
                                hero.moveRight();
                                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S: hero.setDir(2);
                                hero.moveDown();
                                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: hero.setDir(3);
                                hero.moveLeft();
                                break;
        }
        if(e.getKeyCode()==KeyEvent.VK_J){
            hero.shoot();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true){
            try{
                Thread.sleep(sleepTime);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
//            System.out.println("I am flushing!");
            this.repaint();
        }
    }
}
