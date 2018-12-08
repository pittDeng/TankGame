package tank;

import game.TankGame;

import java.awt.*;
import java.util.Date;

public class Hero extends Tank {
    public long DyingTime;
    public Hero(){
        super(0);

    }
    public void relive(){
        if(new Date().getTime()-DyingTime>=3000){
            this.setLived(true);
            this.setRandomPosition();
        }

    }

}
