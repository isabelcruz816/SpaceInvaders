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
public class Enemy extends Item{
    /**
     * Items of enemy.
     */
    private Bullet bullet;
    private Game game;
   
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
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void update() {
        setX(getX() + 6);
        
        // collission with borders
        if(getX() + getWidth() > getGame().getWidth()) {
            setX(getGame().getWidth() - getWidth());
        }
        if(getX() <= 0) {
            setX(0);
        }
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(Assets.enemy, x, y, width, height, null);
    }
    
}
