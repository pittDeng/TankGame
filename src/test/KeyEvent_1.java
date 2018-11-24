package test;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class KeyEvent_1 extends JFrame{
    Panel1 p1;
    public static void main(String[] args) {
        KeyEvent_1 a=new KeyEvent_1();

    }
    KeyEvent_1()
    {
        p1=new Panel1();
        this.add(p1);
        this.addKeyListener(p1);	//界面是事件源，让面板来监听。因为面板可以对事件进行处理，
        this.setTitle("我的小程序");
        this.setSize(400, 300);
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
class Oval1		//创建一个原点类
{
    int x,y,x1,y1;
    Oval1(int x,int y,int x1,int y1)	//构造方法
    {
        this.x=x;
        this.y=y;
        this.x1=x1;
        this.y1=y1;
    }
    public void moveUp()	//设置圆点的上移方法
    {
        this.y--;
    }
    public void moveRight()		//设置圆点的右移方法
    {
        this.x++;
    }
    public void moveDown()		//设置圆点的下移方法
    {
        this.y++;
    }
    public void moveLeft()		//设置圆点的左移方法
    {
        this.x--;
    }
}
class Panel1 extends JPanel implements KeyListener		//定义自己的面板，实现按键监听接口
{
    Oval1 oval;	//定义一个圆点
    Panel1()	//构造方法
    {
        oval=new Oval1(10,10,5,5);	//初始化圆点
    }
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.fillRect(0, 0, 400, 300);		//设置背景黑色
        g.setColor(Color.red);		//设置画笔颜色
        g.fillOval(oval.x, oval.y,oval.x1,oval.y1);		//画圆点
    }
    @Override
    //按下按键时做相应处理
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W)	//如果按键等于W，其中KeyEvent.VK_W的意思就是按键事件里存储的W，可以当公式
        {
            System.out.println("up");
            oval.moveUp();
        }
        else if(e.getKeyCode()==KeyEvent.VK_D)
        {
            oval.moveRight();
        }
        else if(e.getKeyCode()==KeyEvent.VK_S)
        {
            oval.moveDown();
        }
        else if(e.getKeyCode()==KeyEvent.VK_A)
        {
            oval.moveLeft();
        }
        repaint();
    }
    @Override
    //松开按键时做相应处理
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    //按键打出时做下相应处理
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}

