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
    public static final int COLOR_RED = 0;
    public static final int COLOR_GREEN = 1;
    public static final int COLOR_BLUE = 2;
    
    private int speed;
    private Timer colorTimer;
    private int color;
    
    public Star(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
        this.colorTimer = new Timer(0.05);
        this.color = Util.randNum(0, 2);
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    public void update() {
        setY(getY() + getSpeed());
        colorTimer.update();
        if(colorTimer.isActivated()) {
            setColor(Util.randNum(COLOR_RED, COLOR_BLUE));
            colorTimer.restart();
        }
    }
    
    public void render(Graphics g) {
        Color color = Color.RED;
        switch(getColor()) {
            case COLOR_RED:
                color = Color.RED;
                break;
            case COLOR_GREEN:
                color = Color.GREEN;
                break;
            case COLOR_BLUE: 
                color = Color.CYAN;
                break;
        }
        
        g.setColor(color);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
