package game;

import tank.*;

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
    private Vector<Bullet> bullets=new Vector<Bullet>();
    private Vector<Boom> booms=new Vector<Boom>();
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
        for(int i=0;i<bullets.size();++i){
            bullets.get(i).drawBullet(g);
        }
        for(int i=0;i<enemies.size();++i){
            enemies.get(i).paint(g);
        }
        for(int i=0;i<booms.size();++i){
            booms.get(i).paint(g);
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
            heroShoot();
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
            //每刷新5次再检测子弹是否打中Tank
//            if(numFlush==2){
//                numFlush=0;
//                isHit();
//            }
            isHit();
            removeDeadBullet();
            removeDeadTank();
            removeDeadBoom();
//            System.out.println("I am flushing!");
            this.repaint();
        }
    }
    private void removeDeadBullet(){
        for(int i=0;i<bullets.size();++i)
        {
            if(!bullets.get(i).getIsLived())
            {
                bullets.remove(i);
                --i;
            }
            else
                continue;
        }
    }
    private void heroShoot(){
        Bullet item=hero.shoot();
        if(item!=null)
        {
            bullets.add(item);
        }
        //检查子弹是否正常销毁
        //System.out.println("bullets ' num"+bullets.size());
    }
    private void isHitEnemy(Enemy enemy,Bullet bullet){
        switch (enemy.getDir()){
            case 0:
            case 2:
                if(bullet.getX()>enemy.getX()&&bullet.getX()<enemy.getX()+ Tank.tankWidth&&
                        bullet.getY()>enemy.getY()&&bullet.getY()<enemy.getY()+Tank.tankHeight){
                    bullet.setLived(false);
                    enemy.setLived(false);
                }
                break;
            case 1:
            case 3:
                if(bullet.getX()>enemy.getX()&&bullet.getX()<enemy.getX()+ Tank.tankHeight&&
                        bullet.getY()>enemy.getY()&&bullet.getY()<enemy.getY()+Tank.tankWidth){
                    bullet.setLived(false);
                    enemy.setLived(false);
                }
                break;
        }
    }
    private void isHit(){
        for(int i=0;i<enemies.size();++i)
            for(int j=0;j<bullets.size();++j){
                Enemy enemy=enemies.get(i);
                Bullet bullet=bullets.get(j);
                if(enemy.isLived()&&bullet.getIsLived()) isHitEnemy(enemies.get(i),bullets.get(j));
            }
    }
    private void removeDeadTank(){
        for(int i=0;i<enemies.size();++i){
            Enemy item=enemies.get(i);
            if(!item.isLived()){
                Boom boom=new Boom(item.getX(),item.getY(),this);
                booms.add(boom);
                enemies.remove(i);
                --i;
            }
        }
    }
    public void removeDeadBoom(){
        System.out.println("现有boom："+booms.size()+"个");
        for(int i=0;i<booms.size();++i){
            if(!(booms.get(i).isLive())){
                booms.remove(i);
                --i;
            }
        }
    }
}
