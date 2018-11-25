package tank;

import java.awt.*;

public class Bullet implements Runnable{
    private static int speed=Parameter.bulletSpeed;
    private static int sleepTime=Parameter.bulletSleepTime;
    private static int bulletWidth=2;
    private int x;
    private int y;
    private int dir;
    private boolean isLived=true;

    public Bullet(int x,int y,int dir){
        this.x=x;
        this.y=y;
        this.dir=dir;
    }
    public void drawBullet(Graphics g){
        g.setColor(Color.CYAN);
        g.draw3DRect(x,y,bulletWidth,bulletWidth,false);
    }
    @Override
    public void run(){
        while(x>0&&x<Parameter.windowWidth&&y>0&&y<Parameter.windowHeight){
            switch (dir){
                case 0: y-=speed;
                        break;
                case 1: x+=speed;
                        break;
                case 2: y+=speed;
                        break;
                case 3: x-=speed;
                        break;
            }
            try{
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        this.isLived=false;
    }

    public boolean getIsLived() {
        return isLived;
    }
}
