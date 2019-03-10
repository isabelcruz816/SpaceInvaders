package examen2spaceinvaders;

import java.awt.image.BufferedImage;

/**
 * Assets
 * 
 * Helper class to manage all the assets that the game will use.
 * @author César Barraza A01176786
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
 * @version 1.0
 */
public class Assets {
    /**
     * Images that will be used by the game.
     */
    public static BufferedImage playerBullet;
    public static BufferedImage enemyBullet;
    public static BufferedImage player;
    public static BufferedImage alien1;
    public static BufferedImage alien2;
    public static BufferedImage alien3;
    public static BufferedImage shield;
    
    /**
     * Audio that will be used by the game.
     */
    public static SoundClip shoot;
    public static SoundClip playerExplosion;
    public static SoundClip invaderExplosion;
    
    /**
     * Loads all the assets that the game needs.
     */
    public static void init() {
        playerBullet = ImageLoader.loadImage("/images/playerBullet.png");
        enemyBullet = ImageLoader.loadImage("/images/enemyBullet.png");
        player = ImageLoader.loadImage("/images/player1.png");
        alien1 = ImageLoader.loadImage("/images/alien1.png");
        alien2 = ImageLoader.loadImage("/images/alien2.png");
        alien3 = ImageLoader.loadImage("/images/alien3.png");
        shield = ImageLoader.loadImage("/images/shield.png");
        shoot = new SoundClip("/audio/shoot.wav");
        playerExplosion = new SoundClip("/audio/playerExplosion.wav");
        invaderExplosion = new SoundClip("/audio/invaderExplosion.wav");
    }
}