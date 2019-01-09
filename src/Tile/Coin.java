package Tile;

import classes.Game;
import classes.Handler;
import classes.Id;

import java.awt.*;

public class Coin extends Tile {
    public Coin(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.coinSprite.getBufferedImage(),x,y,width,height,null);
    }

    @Override
    public void update() {

    }
}
