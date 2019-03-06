/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2spaceinvaders;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Cesar Barraza
 */
public class Bullet extends Item {
    private int speed;
    
    public Bullet(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }
    
    @Override
    public void update() {
        setY(getY() + speed);
    }
    
    @Override
    public void render(Graphics g) {
        BufferedImage img = (speed > 0 ? Assets.enemyBullet : Assets.playerBullet);
        g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
    }
}
