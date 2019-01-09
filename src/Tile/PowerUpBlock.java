package Tile;

import Entity.PowerUp.Mushroom;
import classes.Game;
import classes.Handler;
import classes.Id;
import graphics.Sprite;

import java.awt.*;

public class PowerUpBlock extends Tile {

    private int spriteY = getY();
    private boolean poppedUp = false;

    public PowerUpBlock(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        this.solid = true;
    }

    @Override
    public void render(Graphics g) {
        if(!poppedUp) g.drawImage(Game.mushroomSprite.getBufferedImage(),x,spriteY,width,height,null);
        if(!activated) g.drawImage(Game.powerUpSprite.getBufferedImage(),x,y,width,height,null);
        else g.drawImage(Game.usedPowerUpSprite.getBufferedImage(),x,y,width,height,null);

    }

    @Override
    public void update() {
        if(activated&&!poppedUp){
            spriteY--;
            if(spriteY<=y-height){
                handler.addEntity(new Mushroom(x,spriteY,width,height,Id.mushroom,handler));
                poppedUp = true;
            }
        }
    }
}
