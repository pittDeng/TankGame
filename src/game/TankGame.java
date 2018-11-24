package game;

import tank.Enemy;
import tank.Hero;
import tank.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TankGame extends JFrame {
    public static int width=300;
    public static int height=400;
    public static void main(String [] args){
        TankGame tg=new TankGame();
    }
    /*
    * 设置窗口参数*/
    public TankGame(){
        MyPanel mp=new MyPanel(this);
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

class MyPanel extends JPanel implements KeyListener {
    Tank hero;
    private int width;
    private int height;
    private TankGame tg;
    public MyPanel(TankGame tg){
        this.width=tg.getWidth();
        this.height=tg.getHeight();
        this.tg=tg;
        hero=new Enemy(10,10);
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,tg.getWidth(),tg.getHeight());
        hero.paint(g);
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
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
