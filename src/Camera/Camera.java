package Camera;
import classes.Game;
import Entity.Entity;
public class Camera {
    private int x,y;

    public void tick(Entity player){
        setX(-player.getX()+Game.WIDTH*Game.SCALE/2-100);
        setY(-player.getY()+Game.HEIGHT*Game.SCALE/2);
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
}
