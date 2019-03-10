/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author anaisabelcruz
 */
public class Shield extends Item {
    
    private int lives;
    private boolean dead;
    
    public Shield(int x, int y, int width, int height) {
        super(x, y, width, height);
        lives = 5;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public boolean isDead() {
        return dead;
    }
    
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    
    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.shield, x, y, width, height, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Century Gothic", Font.PLAIN, 22));
        g.drawString(String.valueOf(getLives()), getX() + (getWidth() / 2) - 6, getY() + (getHeight() / 2));
    }
    
}
