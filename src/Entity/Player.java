package Entity;

import classes.Game;
import classes.Handler;
import classes.Id;
import Tile.Tile;
import graphics.Sprite;
import states.PlayerState;

import java.awt.*;

public class Player extends Entity{

    private PlayerState state;
    private int updates=0,playerCounter=0,immortalityCounter=0,deadTime=0;
    private int pixelsTravelled=0;
    public Player(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        state = PlayerState.SMALL;
    }

    public void render(Graphics g) {
        if(state!=PlayerState.IMMORTAL){
            g.drawImage(Game.playerSprite[playerCounter].getBufferedImage(),x,y,width,height,null);
        }
        else{
            if(immortalityCounter<10||(immortalityCounter>55&&immortalityCounter<65)||(immortalityCounter>115&&immortalityCounter<125)||(immortalityCounter>170)){
                g.drawImage(Game.playerSprite[playerCounter].getBufferedImage(),x,y,width,height,null);
            }
        }

    }
    public void die(){
        super.die();
        Game.deathScreen=true;
    }
    public void update() {
        x+=velX;
        y+=velY;
        updates++;

        if(goingDownPipe){
            pixelsTravelled+=velY;
        }
        if(goingUpPipe){
            pixelsTravelled-=velY;
        }

        if(state == PlayerState.IMMORTAL){
            immortalityCounter++;
            if(immortalityCounter==180){

                if(state==PlayerState.VERYBIG){
                    state=PlayerState.BIG;
                }else if(state==PlayerState.BIG){
                    state = PlayerState.SMALL;
                }
            }
        }
        if(moving){
            if(updates%30==0)playerCounter++;
        }else{
            if(updates%60==0)playerCounter++;
        }
        if(playerCounter>6||(jumping))playerCounter=0;

        for(int i=0;i<handler.tile.size();i++){
            Tile t = handler.tile.get(i);
            if(t.isSolid()&&!goingDownPipe){
                    if(getBoundsTop().intersects(t.getBounds())){
                        setVelY(0);
                        if(jumping){
                            jumping=false;
                            gravity=0.8;
                            falling=true;
                        }
                        if(t.getId()==Id.powerUpBlock){
                            if(getBoundsTop().intersects(t.getBounds())){
                                t.setActivated(true);
                            }
                        }
                    }
                    if(getBoundsBottom().intersects(t.getBounds())){
                        setVelY(0);
                        if(falling)falling=false;
                    }else if(!jumping&&!falling){
                        gravity=0.8;
                        falling=true;
                    }
                    if(getBoundsLeft().intersects(t.getBounds())){
                        setVelX(0);
                        x = t.getX()+t.width;
                    }
                    if(getBoundsRight().intersects(t.getBounds())){
                        setVelX(0);
                        x = t.getX()-this.width;
                    }
            }
            if(t.getId()==Id.coin){
                if(getBounds().intersects(t.getBounds())){
                    t.die();
                    Game.coins++;
                    System.out.println(Game.coins);
                }
            }
        }

        for(int i=0;i<handler.entity.size();i++){
            Entity e = handler.entity.get(i);
            if(e.getId()==Id.mushroom){
                if(getBounds().intersects(e.getBounds())){
                    e.die();
                    if(state == PlayerState.SMALL){
                        state = PlayerState.BIG;
                        this.width+=width/3;
                        this.height+=height/3;
                    }else if(state == PlayerState.BIG){
                        state = PlayerState.VERYBIG;
                        this.width+=width/3;
                        this.height+=height/3;
                    }
                    x-=width;
                    y-=height;
                }
            }else if(e.getId()==Id.goomba){
                if(getBoundsBottom().intersects(e.getBounds())){
                    jumping=true;
                    gravity=GRAVITY;
                    falling = false;
                    e.die();
                }else if(getBounds().intersects(e.getBounds())){
                    if(state==PlayerState.BIG||state==PlayerState.VERYBIG){
                        state = PlayerState.IMMORTAL;
                        width-=width/3;
                        height-=height/3;
                        y+=height;
                    }else if(state==PlayerState.SMALL){
                        die();
                    }
                }
            }
        }
        if(jumping&&!goingDownPipe){
            gravity-=GRAVITY_POWER;
            setVelY((int)-gravity);
            if(gravity<=0.6){
                jumping=false;
                falling=true;
            }
        }
        if(falling&&!goingDownPipe){
            gravity+=GRAVITY_POWER;
            setVelY((int)gravity);
        }
        if(goingDownPipe){
            for(int i=0;i<handler.tile.size();i++){
                Tile t = handler.tile.get(i);
                if(t.getId()==Id.pipeDown){
                    if(t.facing==2){
                        setVelY(2);
                        setVelX(0);
                        setX(t.x);
                    }
                    if(pixelsTravelled>t.height+height){
                        goingDownPipe=false;
                        pixelsTravelled=0;
                    }
                }
            }
        }
        if(goingUpPipe){
            for(int i=0;i<handler.tile.size();i++){
                Tile t = handler.tile.get(i);
                if(t.getId()==Id.pipeUp){
                    if(t.facing==0){
                        setVelY(-2);
                        setVelX(0);
                        setX(t.x);
                    }
                    if(pixelsTravelled>t.height+height){
                        goingUpPipe=false;
                        pixelsTravelled=0;
                    }
                }
            }
        }
    }

}
