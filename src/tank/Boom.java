package tank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Boom {
    public static Image img1=null;
    public static Image img2=null;
    public static Image img3=null;
    public static int ImageWidth=Tank.wheelLength;
    public static int initializedLife=9;
    static {
        try {
            img1 = ImageIO.read(new File("bomb_1.gif"));
            img2=ImageIO.read(new File("bomb_2.gif"));
            img3=ImageIO.read(new File("bomb_3.gif"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private int x;
    private int y;
    private int life=initializedLife;
    private JPanel panel;
    private boolean isLive=true;
    public Boom(int x,int y,JPanel panel){
        this.x=x;
        this.y=y;
        this.panel=panel;
    }

    public void paint(Graphics g) {
        if(life>6) {
            g.drawImage(img1,x,y,Boom.ImageWidth,Boom.ImageWidth,panel);
        }else if(life>3){
            g.drawImage(img2,x,y,Boom.ImageWidth,Boom.ImageWidth,panel);
        }else if(life>=0){
            g.drawImage(img3,x,y,Boom.ImageWidth,Boom.ImageWidth,panel);
        }else{
            this.isLive=false;
        }
        --life;

    }

    public boolean isLive() {
        return isLive;
    }
}

