package Tile;

import classes.Handler;
import classes.Id;

import java.awt.*;

public abstract class Tile {

    public int x,y;
    public int width, height;
    public boolean solid;
    public Id id;
    public Handler handler;
    public boolean activated = false;
    public int facing;

    public Tile(int x, int y, int width, int height, Id id,Handler handler){
        this.x = x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.id=id;
        this.handler=handler;
    }

    public abstract void render(Graphics g);

    public abstract void update();

    public void die(){
        handler.removeTile(this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Rectangle getBounds(){
        return new Rectangle(getX(),getY(),width,height);
    }
    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

}
