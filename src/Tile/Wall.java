package Tile;

import classes.Game;
import classes.Handler;
import classes.Id;

import java.awt.*;

public class Wall extends Tile{
    public Wall(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        this.solid = true;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.wallSprite.getBufferedImage(),x,y,width,height,null);
    }

    @Override
    public void update() {

    }
}
