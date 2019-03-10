/**
 * Star
 * 
 * Class to manage Stars
 * @author César Barraza A01176786
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
 * @version 1.0
 */
package examen2spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Cesar Barraza
 * @author Isabel Cruz
 */
public class Star extends Item {
    /**
     * Star items.
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
    public Star(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }
    /**
     * Getter of star speed
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }
    /**
     * Setter of star speed
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    /**
     * Updates star.
     */
    @Override
    public void update() {
        setY(getY() + getSpeed());
    }
    /**
     * Paints star.
     * @param g 
     */
    @Override
    public void render(Graphics g) {   
        g.setColor(Color.WHITE);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
