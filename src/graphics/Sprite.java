package graphics;

import java.awt.image.BufferedImage;

public class Sprite {

    public BufferedImage image;

    public Sprite(SpriteSheet sheet,int x,int y){
        this.image = sheet.getSprite(x,y);
    }

    public BufferedImage getBufferedImage(){
        return image;
    }
}
