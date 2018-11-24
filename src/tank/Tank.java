package tank;

import java.awt.*;

abstract public class Tank {
    public static int wheelWidth=5* Parameter.sizeNum;
    public static int wheelLength=30* Parameter.sizeNum;
    public static int bodyWidth=10* Parameter.sizeNum;
    public static int bodyLength=20* Parameter.sizeNum;
    public static int diameter=10* Parameter.sizeNum;
    public static int cannonWidth=4* Parameter.sizeNum;
    public static int speed=5;
    public  static Color bodyColor=Color.YELLOW;
    public  static Color cannonColor=Color.YELLOW;
    public static Color hbodyColor=Color.CYAN;
    public static Color hcannonColor=Color.BLUE;
    private int x;
    private int y;
    private int type;
    private int dir;
    public Tank(int x,int y,int type){
        this.x=x;
        this.y=y;
        this.type=type;
        this.dir=dir;
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
    public void moveUp(){y-=speed;}
    public void moveDown(){y+=speed;}
    public void moveLeft(){x-=speed;}
    public void moveRight(){x+=speed;}
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
    }
}
