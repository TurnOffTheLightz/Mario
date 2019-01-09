package Entity;

import classes.Game;
import classes.Handler;
import classes.Id;

import java.awt.*;

public abstract class Entity {

    public int x,y;
    public int width, height;
    public int velX,velY;
    public boolean solid;
    public Id id;
    public Handler handler;
    public boolean jumping=false;
    public boolean falling=true;
    public boolean moving=false;
    public double gravity=0.0;
    public static final double GRAVITY = 5.5;
    public static final double GRAVITY_POWER = 0.03;
    public boolean goingDownPipe = false;
    public boolean goingUpPipe = false;

    public Entity(int x, int y, int width, int height, Id id, Handler handler){
        this.x = x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.id=id;
        this.handler=handler;
        this.solid = false;
    }

    public abstract void render(Graphics g);

    public abstract void update();

    public void die(){
        Game.lives--;
        handler.removeEntity(this);
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
    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Rectangle getBounds(){
        return new Rectangle(getX(),getY(),width,height);
    }
    public Rectangle getBoundsTop(){
        return new Rectangle(getX()+5,getY(),width-10,5);
    }
    public Rectangle getBoundsBottom(){
        return new Rectangle(getX()+5,getY()+height,width-10,5);
    }
    public Rectangle getBoundsLeft(){
        return new Rectangle(getX(),getY()+5,5,height-5);
    }
    public Rectangle getBoundsRight(){
        return new Rectangle(getX()+width-5,getY()+5,5,height-5);
    }

}
