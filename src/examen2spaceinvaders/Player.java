/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2spaceinvaders;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author anaisabelcruz
 */
public class Player extends Item {
    private Game game;
    private Bullet bullet;

    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    public Bullet getBullet() {
        return bullet;
    }
    
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
    
    @Override
    public void update() {
        if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_RIGHT)) {
            setX(getX() + 6);
        }
        else if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_LEFT)) {
            setX(getX() - 6);
        }
        
        // shoot bullet
        if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_SPACE)) {
            if(getBullet() == null) {
                setBullet(new Bullet(getX() + (getWidth() / 2 - 2), getY() - 1, 4, 4, -5));
            }
        }
        
        // update bullet
        if(getBullet() != null) {
            getBullet().update();   
            // check if bullet goes out of the screen
            if(getBullet().getY() <= 0) {
                setBullet(null);
            }
        }
        
        // collision with border
        if(getX() + getWidth() >= getGame().getWidth()) {
            setX(getGame().getWidth() - getWidth());
        }
        if(getX() <= 0) {
            setX(0);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, x, y, width, height, null);
        if(bullet != null) {
            bullet.render(g);
        }
    }
    
}
