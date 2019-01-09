package Entity;

import Tile.Tile;
import classes.Game;
import classes.Handler;
import classes.Id;

import java.awt.*;
import java.util.Random;

public class Mob extends Entity {

    private int frame = 0,updates = 0;
    private Random rand = new Random();

    public Mob(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        int dir = rand.nextInt(2);
        switch (dir){
            case 0: setVelX(-2);break;
            case 1: setVelX(2);break;
        }
    }


    public void render(Graphics g) {
        if(velX<0)
            g.drawImage(Game.mobSprite[4+frame].getBufferedImage(),x,y,width,height,null);
        else if(velX>0){
            g.drawImage(Game.mobSprite[frame].getBufferedImage(),x,y,width,height,null);
        }
    }

    @Override
    public void update() {
        updates++;
        x+=velX;
        y+=velY;
        if(updates%30==0){
            frame++;
        }
        if(frame>3) frame=0;
        for(int i=0;i<handler.tile.size();i++){
            Tile t = handler.tile.get(i);
            if(t.isSolid()){
                if(getBoundsBottom().intersects(t.getBounds())){
                    setVelY(0);
                    if(falling)falling=false;
                }else if(!falling&&!jumping){
                    gravity=0.8;
                    falling=true;
                }
                if(getBoundsLeft().intersects(t.getBounds())){
                    setVelX(1);
                }
                if(getBoundsRight().intersects(t.getBounds())){
                    setVelX(-1);
                }
            }
        }
        if(falling){
            gravity+=GRAVITY_POWER*2;
            setVelY((int)gravity);
        }
    }
}
