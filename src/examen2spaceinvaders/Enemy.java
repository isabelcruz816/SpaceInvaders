/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2spaceinvaders;

import java.awt.Graphics;

/**
 *
 * @author anaisabelcruz
 * @author cesarbarraza
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
     * 
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public Enemy(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.animation = new Animation(Assets.alien, 109, 79, 0.5, 2);
        velX = 2;
    }

    public Bullet getBullet() {
        return bullet;
    }
    
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
    
    public Game getGame() {
        return game;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public boolean isDead() {
        return dead;
    }
    
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    public void shoot() {
        if(getBullet() == null) {
            setBullet(new Bullet(getX() + (getWidth() / 2 - 2), getY() + getHeight(), 2, 8, 5));
        }
    }
    
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

    @Override
    public void render(Graphics g) {
       g.drawImage(animation.getCurrentImageFrame(), x, y, width, height, null);
       if(getBullet() != null) {
           getBullet().render(g);
       }
    }
    
}
