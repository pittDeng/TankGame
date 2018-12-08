package tank;

public class Enemy extends Tank implements Runnable {
    public static int sleepTime=150;
    public Enemy(int x,int y,int dir){
        super(x,y,1);
        setDir(dir);
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
