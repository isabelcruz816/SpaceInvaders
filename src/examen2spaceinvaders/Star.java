/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Cesar Barraza
 */
public class Star extends Item {
    private int speed;
    
    public Star(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    @Override
    public void update() {
        setY(getY() + getSpeed());
    }
    
    @Override
    public void render(Graphics g) {   
        g.setColor(Color.WHITE);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
