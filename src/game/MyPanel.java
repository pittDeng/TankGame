package game;

import tank.*;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable {
    public static Hero hero;
    public static Vector<Enemy> enemies;
    private Vector<Bullet> bullets=new Vector<Bullet>();
    private Vector<Boom> booms=new Vector<Boom>();
    private static int sleepTime= Parameter.flushTime;
    private int numEnemies=10;
    private int enemiesOnScreen=3;
    private int lifeNum=3;
    private int width;
    private int height;
    private TankGame tg;
    public MyPanel(TankGame tg){
        this.width=tg.getWidth();
        this.height=tg.getHeight();
        this.tg=tg;
        hero=new Hero();
        enemies=new Vector<Enemy>();
        for(int i=0;i<enemiesOnScreen;++i){
            Enemy enemy=new Enemy();
            Thread th=new Thread(enemy);
            th.start();
            enemies.add(enemy);
        }
        numEnemies-=enemiesOnScreen;
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,tg.getWidth(),tg.getHeight());

        if(hero.isLived())hero.paint(g);
        for(int i=0;i<bullets.size();++i){
            bullets.get(i).drawBullet(g);
        }
        for(int i=0;i<enemies.size();++i){
            enemies.get(i).paint(g);
        }
        for(int i=0;i<Enemy.eBullets.size();++i){
            Enemy.eBullets.get(i).drawBullet(g);
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
        while (lifeNum>0&&numEnemies>0){
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
            if(!hero.isLived())hero.relive();
            isHit();
            removeDeadBullet();
            removeDeadTank();
            removeDeadBoom();
//            System.out.println("I am flushing!");
            this.repaint();
        }
    }
    private void removeDeadBullet(){
        //移除死亡的敌机子弹
        for(int i=0;i<Enemy.eBullets.size();++i){
            if(!Enemy.eBullets.get(i).getIsLived())
            {
                Enemy.eBullets.remove(i);
                --i;
            }
            else
                continue;
        }
        //移除死亡的英雄子弹
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
    //这个函数初始版本是判断是否击中敌人的
    //而后应为要判断英雄是否被击中，将enemy变量的类型从
    //Enemy改为Tank就好了
    private boolean isHitEnemy(Tank enemy,Bullet bullet){
        switch (enemy.getDir()){
            case 0:
            case 2:
                if(bullet.getX()>enemy.getX()&&bullet.getX()<enemy.getX()+ Tank.tankWidth&&
                        bullet.getY()>enemy.getY()&&bullet.getY()<enemy.getY()+Tank.tankHeight){
                    bullet.setLived(false);
                    enemy.setLived(false);
                    return true;
                }
                break;
            case 1:
            case 3:
                if(bullet.getX()>enemy.getX()&&bullet.getX()<enemy.getX()+ Tank.tankHeight&&
                        bullet.getY()>enemy.getY()&&bullet.getY()<enemy.getY()+Tank.tankWidth){
                    bullet.setLived(false);
                    enemy.setLived(false);
                    return true;
                }
                break;
        }
        return false;
    }
    private void isHit(){
        //检测我的坦克是否打到敌人
        for(int i=0;i<enemies.size();++i)
            for(int j=0;j<bullets.size();++j){
                Enemy enemy=enemies.get(i);
                Bullet bullet=bullets.get(j);
                if(enemy.isLived()&&bullet.getIsLived()) isHitEnemy(enemies.get(i),bullets.get(j));
            }
            //判断敌人的子弹是否击中英雄，顺便产生Boom
            //如果在RemoveDeadTank中产生，Boom会一直产生
            //因为这个类中无法将hero移除
        for(int i=0;i<Enemy.eBullets.size();++i){
            Bullet bullet=Enemy.eBullets.get(i);
            if(hero.isLived()&&bullet.getIsLived()){
                if(isHitEnemy(hero,bullet)){
                    hero.DyingTime=new Date().getTime();
                    //死亡减一条命
                    --lifeNum;
                    Boom boom=new Boom(hero.getX(),hero.getY(),this);
                    booms.add(boom);
                }
            }
        }
    }
    private void removeDeadTank(){
        //移除死亡的Enemy,同时产生爆炸效果
        for(int i=0;i<enemies.size();++i){
            Enemy item=enemies.get(i);
            if(!item.isLived()){
                Boom boom=new Boom(item.getX(),item.getY(),this);
                booms.add(boom);
                enemies.remove(i);
                if(numEnemies>0){
                    Enemy newEnemy=new Enemy();
                    Thread th=new Thread(newEnemy);
                    th.start();
                    enemies.add(newEnemy);
                    --numEnemies;
                }
                --i;
            }
        }

    }
    public void removeDeadBoom(){
        //System.out.println("现有boom："+booms.size()+"个");
        for(int i=0;i<booms.size();++i){
            if(!(booms.get(i).isLive())){
                booms.remove(i);
                --i;
            }
        }
    }
}
