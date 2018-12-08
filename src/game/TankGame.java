package game;

import tank.*;

import javax.swing.*;


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

