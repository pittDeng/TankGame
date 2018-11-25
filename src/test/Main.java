package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

public class Main  implements Runnable{
    public static void  main(String [] args){
        Main temp=new Main();
        Thread th=new Thread(temp);
        th.start();





    }


    @Override
    public void run() {
        for(int i=0;i<20;++i){
            Date date=new Date();
            try{
                Thread.sleep(1000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(date.getTime());
        }
    }
}

class MyPanel extends Panel implements KeyListener {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fill3DRect(0,0,100,100,false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Pressed!");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
