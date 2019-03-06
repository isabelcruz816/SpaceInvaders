/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2spaceinvaders;

import java.awt.image.BufferedImage;

/**
 *
 * @author Cesar Barraza
 */
public class Animation {
    private BufferedImage image;
    private int width;
    private int height;
    private Timer timer;
    private double speed;
    private int frame;
    private int maxFrames;
    
    public Animation(BufferedImage image, int width, int height, double speed, int maxFrames) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = 0;
        this.timer = new Timer(speed);
        this.maxFrames = maxFrames;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    public int getFrame() {
        return frame;
    }
    
    public void setFrame(int frame) {
        this.frame = frame;
    }
    
    public void update() {
        timer.update();
        if(timer.isActivated()) {
            frame++;
            if(frame == maxFrames) {
                frame = 0;
            }
            timer.restart();
        }
    }
    
    public BufferedImage getImageFrame(int column) {
        return image.getSubimage(width * column, 0, width, height);
    }
}
