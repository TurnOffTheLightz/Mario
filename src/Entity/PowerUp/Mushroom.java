package Entity.PowerUp;
import Entity.Entity;
import Tile.Tile;
import classes.Game;
import classes.Handler;
import classes.Id;

import java.awt.*;
import java.util.Random;

public class Mushroom extends Entity{

    private Random rand = new Random();

    public Mushroom(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        this.solid = true;
        int dir = rand.nextInt(2);
        switch(dir){
            case 0:
                setVelX(-1);
                break;
            case 1: setVelX(1);
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.mushroomSprite.getBufferedImage(),x,y,width,height,null);
    }

    @Override
    public void update() {
        x+=velX;
        y+=velY;
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
            gravity+=GRAVITY_POWER;
            setVelY((int)gravity);
        }
    }
}
