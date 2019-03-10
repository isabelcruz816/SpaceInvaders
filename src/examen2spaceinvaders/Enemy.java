package examen2spaceinvaders;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Enemy
 * 
 * Class to manage Enemy objects
 * @author César Barraza A01176786
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
 * @version 1.0
 */
public class Enemy extends Item {
    /**
     * Items of enemy.
     */
    private Bullet bullet;
    private Game game;
    private int velX;
    private boolean dead;
    private Animation animation;
    
    /**
     * Class constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param width enemy width
     * @param height enemy height
     */
    public Enemy(int x, int y, int width, int height, Game game, BufferedImage img) {
        super(x, y, width, height);
        this.game = game;
        this.animation = new Animation(img, 109, 79, 0.5, 2);
        velX = 2;
    }
    /**
     * Getter of bullet
     * @return bullet
     */
    public Bullet getBullet() {
        return bullet;
    }
    /**
     * Setter of bullet
     * @param bullet 
     */
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
    /**
     * Getter of game
     * @return game
     */
    public Game getGame() {
        return game;
    }
    /**
     * Getter of velocity in x axis
     * @return velX
     */
    public int getVelX() {
        return velX;
    }
    /**
     * Setter of velocity in x axis
     * @param velX 
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }
    /**
     * Boolean of player life.
     * @return dead
     */
    public boolean isDead() {
        return dead;
    }
    /**
     * Setter of player's dead
     * @param dead 
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    /**
     * Shoot method for player
     */
    public void shoot() {
        // if bullet does not exist, create bullet
        if(getBullet() == null) {
            setBullet(new Bullet(getX() + (getWidth() / 2 - 2), getY() + getHeight(), 2, 8, 5));
        }
    }
    /**
     * Updates player.
     */
    @Override
    public void update() {
        // update position
        setX(getX() + getVelX());
        
        // update the bullet
        if(getBullet() != null) {
            getBullet().update();
            
            // if it goes outside of the screen, delete it
            if(getBullet().getY() + getBullet().getHeight() >= getGame().getHeight()) {
                setBullet(null);
            }
        }
        
        // update animation
        animation.update();
    }
    /**
     * Paints player.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
       g.drawImage(animation.getCurrentImageFrame(), x, y, width, height, null);
       if(getBullet() != null) {
           getBullet().render(g);
       }
    }
    
}
