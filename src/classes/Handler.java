package classes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import Entity.Entity;
import Entity.Mob;
import Entity.Player;
import Entity.PowerUp.Mushroom;
import Tile.Pipe;
import Tile.Tile;
import Tile.Wall;
import Tile.PowerUpBlock;
import Tile.Coin;
import graphics.Sprite;

import javax.imageio.ImageIO;

public class Handler {
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();

    public BufferedImage map;

    public Handler(){
        try {
            map = ImageIO.read(getClass().getResource("/map1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void render(Graphics g){
        for(Entity en: entity){
            en.render(g);
        }
        for(Tile ti: tile){
            ti.render(g);
        }

        for(int i=0;i<entity.size();i++){
            Entity en = entity.get(i);
            en.update();
        }
        for(Tile ti: tile){
            ti.update();
        }

    }

    public void update(){

    }

    public BufferedImage getMap(){
        return map;
    }


    public void addEntity(Entity en) {
        entity.add(en);
    }
    public void removeEntity(Entity en){
        entity.remove(en);
    }
    public void addTile(Tile ti) {
        tile.add(ti);
    }
    public void removeTile(Tile ti){
        tile.remove(ti);
    }
    public void createLevel(BufferedImage map){
        int width = map.getWidth();
        int height = map.getHeight();


        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){

                int pixel = map.getRGB(x,y);

                int red = (pixel>>16)&0xff;
                int green = (pixel>>8)&0xff;
                int blue = (pixel)&0xff;

                if(red==0&&green==0&&blue==255){
                    addEntity(new Player(x*64,y*64,64,64,Id.player,this));
                }
                if(red==0&&green==0&&blue==0){
                    addTile(new Wall(x*64,y*64,64,64,Id.wall,this));
                }
                if(red==255&&green==0&&blue==0){
                    addEntity(new Mushroom(x*64,y*64,64,64,Id.mushroom,this));
                }
                if(red==0&&green==255&&blue==0){
                    addEntity(new Mob(x*64,y*64,64,64,Id.goomba,this));
                }
                if(red==255&&green==60&&blue==0){
                    addTile(new PowerUpBlock(x*64,y*64,64,64,Id.powerUpBlock,this));
                }
                if(red==99&&green==99&&blue==99){
                    addTile(new Pipe(x*64,y*64,64,64*9,Id.pipeDown,this,2,Game.pipeDownSprite));
                }
                if(red==98&&green==99&&blue==99){
                    addTile(new Pipe(x*64,y*64,64,64*9,Id.pipeUp,this,0,Game.pipeUpSprite));
                }
                if(red==255&&green==216&&blue==0){
                    addTile(new Coin(x*64,y*64,48,48,Id.coin,this));
                }
            }
        }
    }
    public void clearLevel(){
        entity.clear();
        tile.clear();
    }
}
