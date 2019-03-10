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
    private Bullet playerBullet;

    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    @Override
    public void update() {
        if(playerBullet != null) {
         playerBullet.update();   
        }
        if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_RIGHT)) {
            setX(getX() + 6);
        }
        else if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_LEFT)) {
            setX(getX() - 6);
        }
        
        //shoots
        if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_SPACE)) {
            if(playerBullet == null) {
                playerBullet = new Bullet(400, 400, 5, 5, -5);
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
        if(playerBullet != null) {
            playerBullet.render(g);
        }
    }
    
}
