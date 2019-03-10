package examen2spaceinvaders;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Bullet
 * 
 * Class to manage Bullet objects
 * @author César Barraza A01176786
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
 * @version 1.0
 */
public class Bullet extends Item {
    /**
     * Bullet's speed.
     */
    private int speed;
    
    /**
     * Class constructor
     * @param x
     * @param y
     * @param width
     * @param height
     * @param speed 
     */
    public Bullet(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }
    /**
     * Updates bullet.
     */
    @Override
    public void update() {
        setY(getY() + speed);
    }
    /**
     * Paints bullet.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        BufferedImage img = (speed > 0 ? Assets.enemyBullet : Assets.playerBullet);
        g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
    }
}
