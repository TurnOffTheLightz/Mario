package classes;

import Camera.Camera;
import Entity.Entity;
import Entity.Player;
import Tile.Tile;
import Tile.Wall;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.KeyInput;
import states.PlayerState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Game extends Canvas implements Runnable{


    public static final int WIDTH =270;
    public static final int HEIGHT = WIDTH/14*10;
    public static final int SCALE = 4;
    public static final String TITLE = "MARIO";

    private Thread thread;
    private boolean running = false;
    public static Handler handler;



    public static SpriteSheet sheet;
    public static Sprite mushroomSprite;
    public static Sprite wallSprite;
    public static Sprite powerUpSprite;
    public static Sprite usedPowerUpSprite;
    public static Sprite pipeDownSprite;
    public static Sprite pipeUpSprite;
    public static Sprite coinSprite;


    public static Sprite[]playerSprite;
    public static Sprite []mobSprite;

    public static Camera cam;

    public static boolean deathScreen = false;
    private int timeDead=0;
    public static int lives=5;

    public static int coins=0;

    public Game(){
        Dimension size =  new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        JFrame jframe = new JFrame(TITLE);
        jframe.setSize(WIDTH*SCALE,HEIGHT*SCALE);
        jframe.setLocationRelativeTo(null);
        jframe.add(this);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.pack();
    }

    public void init(){
        handler = new Handler();
        sheet = new SpriteSheet("/spriteSheet.png");
        cam = new Camera();
        addKeyListener(new KeyInput());
        wallSprite = new Sprite(sheet,1,1);
        playerSprite = new Sprite[7];
        for(int i=0;i<playerSprite.length;i++){
            playerSprite[i] = new Sprite(sheet,i+1,3);
        }
        mushroomSprite = new Sprite(sheet,3,2);
        mobSprite = new Sprite[8];
        for(int i=0;i<mobSprite.length;i++){
            mobSprite[i] = new Sprite(sheet,i+1,4);
        }
        powerUpSprite = new Sprite(sheet,2,2);
        usedPowerUpSprite = new Sprite(sheet,2,1);
        pipeDownSprite = new Sprite(sheet,4,1);
        pipeUpSprite = new Sprite(sheet,5,1);
        coinSprite = new Sprite(sheet,4,2);

        handler.createLevel(handler.getMap());

    }

    public static void main(String[] args) {
        Game g = new Game();
        g.start();
    }

    @Override
    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        int frames = 0;
        int updates = 0;
        while(running){
            long now = System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime = now;

            while(delta>=1){
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer+=1000;
                System.out.println("FPS: "+frames + "\tUPDATES: "+updates);
                frames=0;
                updates=0;
            }
        }
    }

    private synchronized void start(){
        if(running) return;
        running = true;
        thread = new Thread(this,"Thread");
        thread.start();
    }
    private synchronized void stop(){
        if(!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs==null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());

        g.drawImage(coinSprite.getBufferedImage(),10,17,100,100,null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier",Font.ITALIC,24));
        g.drawString("x"+Integer.toString(coins),110,75);


        if(!deathScreen){
            g.translate(cam.getX(),cam.getY());
            handler.render(g);
        }else{
            g.setColor(Color.black);
            g.fillRect(0,0,getWidth(),getHeight());
            g.drawImage(playerSprite[0].getBufferedImage(),WIDTH*SCALE/2-100,HEIGHT*SCALE/2-100,200,200,null);
            g.setFont(new Font("Courier",Font.BOLD,50));
            g.setColor(Color.WHITE);
            g.drawString("x"+Integer.toString(lives),WIDTH*SCALE/2-200,HEIGHT*SCALE/2);
        }

        g.dispose();
        bs.show();
    }
    public void update(){
        handler.update();
        for(Entity en:handler.entity){
            if(en.getId()==Id.player)
                cam.tick(en);
        }
        if(deathScreen){
            timeDead++;
            if(timeDead>=180){
                deathScreen=false;
                timeDead=0;
                handler.clearLevel();
                handler.createLevel(handler.getMap());
            }
        }
    }
}
