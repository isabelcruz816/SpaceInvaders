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
    private int velX;
   
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
        velX = 4;
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

    @Override
    public void update() {
        // collission with borders
        if(getX() + getWidth() > getGame().getWidth()) {
            setX(getGame().getWidth() - getWidth());
            setVelX(getVelX() * -1);
        }
        if(getX() <= 0) {
            setX(0);
            setVelX(getVelX() * -1);
        }
        // update position
        setX(getX() + getVelX());
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(Assets.enemy, x, y, width, height, null);
    }
    
}
