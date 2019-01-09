package Tile;

import classes.Game;
import classes.Handler;
import classes.Id;
import graphics.Sprite;

import java.awt.*;

public class Pipe extends Tile {
    private Sprite sprite;

    public Pipe(int x, int y, int width, int height, Id id, Handler handler, int facing,Sprite sprite) {
        super(x, y, width, height, id, handler);
        this.facing = facing;
        this.solid=true;
        this.sprite=sprite;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite.getBufferedImage(),x,y,width,height,null);
    }

    @Override
    public void update() {

    }
}
