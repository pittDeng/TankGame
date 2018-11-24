package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame {
    public static void  main(String [] args){
        Main frame=new Main();


    }
    public Main(){
        MyPanel myPanel=new MyPanel();
        this.add(myPanel);
        this.setSize(400,300);
        this.setVisible(true);
        this.addKeyListener(myPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
