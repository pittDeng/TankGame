package tank;

import java.util.Date;
import java.util.Vector;

public class Enemy extends Tank implements Runnable {
    public static int sleepTime=150;
    public static Vector<Bullet> eBullets=new Vector<Bullet>();
    public Enemy(){
        super(1);
    }



    @Override
    public void run() {
        //nstep记录自上次转向后共走了多少步
        int nstep=0;
        int oneDirectStep=(int)(10+Math.random()*10);
        boolean isTouchWall=false;
        while(true){
            switch (getDir()){
                case 0:isTouchWall=moveUp();
                    break;
                case 1:isTouchWall=moveRight();
                    break;
                case 2:isTouchWall=moveDown();
                    break;
                case 3:isTouchWall=moveLeft();
                    break;
            }
            ++nstep;
            //随机发射子弹
            if(nstep%4==(int)(Math.random()*4)){
                Bullet item= this.shoot();
                if(item!=null){
                    eBullets.add(item);
                }
                Thread th=new Thread(item);
                th.start();
            }
            try{
                Thread.sleep(sleepTime);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(isTouchWall||nstep>=oneDirectStep){
                setDir((int)(Math.random()*4));
                nstep=0;
                oneDirectStep=(int)(10+Math.random()*10);
            }
            if(!isLived()){
                break;
            }

        }

    }
}
