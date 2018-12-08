package tank;

import game.TankGame;

import java.awt.*;
import java.util.Date;
import java.util.Vector;

abstract public class Tank {
    public static int wheelWidth=5* Parameter.sizeNum;
    public static int wheelLength=30* Parameter.sizeNum;
    public static int bodyWidth=10* Parameter.sizeNum;
    public static int bodyLength=20* Parameter.sizeNum;
    public static int diameter=10* Parameter.sizeNum;
    public static int cannonWidth=4* Parameter.sizeNum;
    public static int tankWidth=2*wheelWidth+bodyWidth;
    public static int tankHeight=wheelLength;
    public static int speed=5;
    public  static Color bodyColor=Color.YELLOW;
    public  static Color cannonColor=Color.YELLOW;
    public static Color hbodyColor=Color.CYAN;
    public static Color hcannonColor=Color.BLUE;

    private long lastShootTime=new Date().getTime();
    private long twoShootInterVal=Parameter.twoShootInterval;
    private int x;
    private int y;
    private int type;
    private int dir;
    private int xBullet;
    private int yBullet;
    private boolean isLived=true;
    public Tank(int type){
        this.setRandomPosition();
        this.type=type;
    }

    private void verticalPaint(Graphics g){
        //画竖直状态下除了炮筒之外的东西
        if(type==0) g.setColor(hbodyColor);
        else g.setColor(bodyColor);
        g.fill3DRect(x,y,wheelWidth,wheelLength,false);
        g.fill3DRect(x+wheelWidth+bodyWidth,y,wheelWidth,wheelLength,false);
        g.fill3DRect(x+wheelWidth,y+wheelWidth,bodyWidth,bodyLength,false);
        if(type==0)g.setColor(hcannonColor);
        else g.setColor(cannonColor);
        g.fillOval(x+wheelWidth+(bodyWidth-diameter)/2,y+wheelWidth+(bodyLength-diameter)/2,diameter,diameter);

    }
    private void horizontalPaint(Graphics g){
        //画水平状态下除了炮筒之外的东西
        if(type==0) g.setColor(hbodyColor);
        else g.setColor(bodyColor);
        g.fill3DRect(x,y,wheelLength,wheelWidth,false);
        g.fill3DRect(x,y+wheelWidth+bodyWidth,wheelLength,wheelWidth,false);
        g.fill3DRect(x+wheelWidth,y+wheelWidth,bodyLength,bodyWidth,false);
        if(type==0)g.setColor(hcannonColor);
        else g.setColor(cannonColor);
        g.fillOval(x+wheelWidth+(bodyLength-diameter)/2,y+wheelWidth+(bodyWidth-diameter)/2,diameter,diameter);
    }

    public void paint(Graphics g) {
        switch(getDir()){
            case 0: verticalPaint(g);
                    g.fill3DRect(x+wheelWidth+(bodyWidth-cannonWidth)/2,y,cannonWidth,wheelWidth+bodyLength/2,false);
                    break;
            case 1: horizontalPaint(g);
                    g.fill3DRect(x+wheelWidth+(bodyLength)/2,y+wheelWidth+(bodyWidth-cannonWidth)/2,wheelWidth+bodyLength/2,cannonWidth,false);
                    break;
            case 2: verticalPaint(g);
                    g.fill3DRect(x+wheelWidth+(bodyWidth-cannonWidth)/2,y+wheelWidth+bodyLength/2,cannonWidth,wheelWidth+bodyLength/2,false);
                    break;
            case 3: horizontalPaint(g);
                    g.fill3DRect(x,y+wheelWidth+(bodyWidth-cannonWidth)/2,wheelWidth+bodyLength/2,cannonWidth,false);
                    break;
        }


    }
    public Bullet shoot(){
        //每次都按shoot都要生成一个线程
        if(new Date().getTime()-lastShootTime<twoShootInterVal)return null;
        lastShootTime=new Date().getTime();
        Bullet item=null;
        //判断是敌机产生的子弹还是Hero产生的子弹，
        // 创建不同的
        //子弹类
        if(this.type==0){
            this.setBulletPosition();
            item=new Bullet(xBullet,yBullet,this.dir);
        }else{
            this.setBulletPosition();
            item=new EBullet(xBullet,yBullet,this.dir);
        }
        Thread th=new Thread(item);
        th.start();
        return item;


        //System.out.println("The number of the bullets:"+bullets.size());
    }
    //随机生成位置
    protected void setRandomPosition(){
        this.x=(int)((TankGame.width-tankWidth)*Math.random());
        this.y=(int)((TankGame.height-tankHeight)*Math.random());
        this.setDir((int)(4*Math.random()));
    }
    /*---------------------------------------------
    * 以下是几句话的简单函数*/
    public boolean moveUp(){
        y-=speed;
        if(y<0){
            y+=speed;
            return true;
        }
        return false;
    }
    public boolean moveDown(){
        y+=speed;
        if(y> TankGame.height-tankHeight){
            y-=speed;
            return true;
        }
        return false;
    }
    public boolean moveLeft(){
        x-=speed;
        if(x<0){
            x+=speed;
            return true;
        }
        return false;
    }
    public boolean moveRight(){
        x+=speed;
        if(x>TankGame.width-tankWidth){
            x-=speed;
            return true;
        }
        return false;
    }
    private void setBulletPosition(){
        switch (dir){
            case 0:xBullet=x+wheelWidth+bodyWidth/2;
                yBullet=y;
                break;
            case 1: xBullet=x+wheelLength;
                yBullet=y+wheelWidth+bodyWidth/2;
                break;
            case 2: xBullet=x+wheelWidth+bodyWidth/2;
                yBullet=y+wheelLength;
                break;
            case 3: xBullet=x;
                yBullet=y+wheelWidth+bodyWidth/2;
                break;
        }
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
        //修改子弹生成的位置

    }

    public boolean isLived() {
        return isLived;
    }

    public void setLived(boolean lived) {
        isLived = lived;
    }

}
