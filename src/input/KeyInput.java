package input;

import classes.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Entity.Entity;
import classes.Id;
import Tile.Tile;

public class KeyInput implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {
        //not using
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key  = e.getKeyCode();
        for(Entity en: Game.handler.entity) {
            if(en.getId()== Id.player)
                switch (key) {
                    case KeyEvent.VK_0:
                        Game.deathScreen=true;
                    case KeyEvent.VK_SPACE:
                        if(!en.jumping){
                            en.jumping=true;
                            en.gravity=en.GRAVITY;
                        }
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(2);
                        en.setMoving(true);
                        break;
                    case KeyEvent.VK_A:
                        en.setVelX(-2);
                        en.setMoving(true);
                        break;
                        case KeyEvent.VK_S:
                           for(int j=0;j<Game.handler.tile.size();j++){
                               if(en.goingDownPipe) return;
                               Tile t = Game.handler.tile.get(j);
                               if(t.getId()==Id.pipeDown){
                                   if(en.getBoundsBottom().intersects(t.getBounds())){
                                       if(!en.goingDownPipe) en.goingDownPipe=true;
                                   }
                               }
                           }
                           break;
                           case KeyEvent.VK_W:
                               for(int j=0;j<Game.handler.tile.size();j++){
                                   if(en.goingUpPipe) return;
                                   Tile t = Game.handler.tile.get(j);
                                   if(t.getId()==Id.pipeUp){
                                       if(en.getBoundsTop().intersects(t.getBounds())){
                                           en.setX(t.x+en.width);
                                           en.setY(t.y+t.height);
                                           if(!en.goingUpPipe) en.goingUpPipe=true;
                                       }
                                   }
                               }
                               break;
                    default:
                        break;
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key  = e.getKeyCode();
        for(Entity en: Game.handler.entity) {
            if(en.getId()== Id.player)
                switch (key) {
                    case KeyEvent.VK_D:
                        en.setVelX(0);
                        en.setMoving(false);
                        break;
                    case KeyEvent.VK_A:
                        en.setVelX(0);
                        en.setMoving(false);
                        break;
                    default:
                        break;
                }
        }

    }
}
