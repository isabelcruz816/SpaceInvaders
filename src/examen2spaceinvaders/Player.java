/**
 * Player
 * 
 * Class to manage Player
 * @author César Barraza A01176786
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
 * @version 1.0
 */
package examen2spaceinvaders;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author anaisabelcruz
 * @author Cesar Barraza
 */
public class Player extends Item {
    /**
     * Player items.
     */
    private Game game;
    private Bullet bullet;
    /**
     * Class constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param width player's width
     * @param height player's height
     * @param game Game
     */
    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }
    /**
     * Getter of game.
     * @return game.
     */
    public Game getGame() {
        return game;
    }
    /**
     * Getter of the bullet
     * @return bullet.
     */
    public Bullet getBullet() {
        return bullet;
    }
    /**
     * Setter of bullet
     * @param bullet 
     */
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
    /**
     * Updates player.
     */
    @Override
    public void update() {
        if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_RIGHT)) {
            setX(getX() + 6);
        }
        else if(getGame().getKeyManager().isKeyDown(KeyEvent.VK_LEFT)) {
            setX(getX() - 6);
        }
        
        // shoot bullet
        if(getGame().getKeyManager().isKeyPressed(KeyEvent.VK_SPACE)) {
            if(getBullet() == null) {
                setBullet(new Bullet(getX() + (getWidth() / 2 - 2), getY() - 1, 4, 4, -5));
                Assets.shoot.play();
            }
        }
        
        // update bullet
        if(getBullet() != null) {
            getBullet().update();   
            // check if bullet goes out of the screen
            if(getBullet().getY() <= 0) {
                setBullet(null);
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
    /**
     * Paints player.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, x, y, width, height, null);
        if(bullet != null) {
            bullet.render(g);
        }
    }
    
}
