package examen2spaceinvaders;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Game Handles everything that the game needs to function correctly.
 *
 * @author César Barraza A01176786 Date 30/Jan/2019
 * @version 1.0
 */
public class Game implements Runnable {

    /**
     * Game Constants
     */
    public static final int MAX_STARS = 200;
    public static final int MAX_ENEMY_ROWS = 5;
    public static final int MAX_ENEMY_COLUMNS = 10;

    /**
     * The display's properties.
     */
    private String title;
    private int width;
    private int height;

    /**
     * The display that represents the game's window.
     */
    private Display display;

    /**
     * The game's input managers.
     */
    private KeyManager keyManager;
    private MouseManager mouseManager;

    /**
     * The main game loop thread.
     */
    private Thread thread;

    /**
     * Denotes whether or not the game is running.
     */
    private boolean isRunning;

    /**
     * The game items.
     */
    private Player player;
    private ArrayList<Star> stars;
    private Enemy[][] enemies;
    private int direction;
    private boolean paused;
    private boolean gameOver;
    private int changes;
    private int lives;
    
    /**
     * The game timers
     */

    /**
     * Initializes the game object with the desired display properties.
     *
     * @param title
     * @param width
     * @param height
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isRunning = false;
        this.keyManager = new KeyManager();
        this.mouseManager = new MouseManager();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the paused state
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @param paused new paused state to set
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * @return game over state
     */
    public boolean isGameOver() {
        return gameOver;
    }
    
    /**
     * @param gameOver the gameover state to set
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    /**
     * @return the lives
     */
    public int getLives() {
        return lives;
    }
    
    /**
     * @param lives lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    /**
     * @return the keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * @return the mouseManager
     */
    public MouseManager getMouseManager() {
        return mouseManager;
    }

    /**
     * Starts all initializations needed to start the game.
     */
    private void init() {
        // create the display
        display = new Display(getTitle(), getWidth(), getHeight());
        display.getWindow().addKeyListener(getKeyManager());
        display.getWindow().addMouseListener(getMouseManager());
        display.getWindow().addMouseMotionListener(getMouseManager());
        display.getCanvas().addMouseListener(getMouseManager());
        display.getCanvas().addMouseMotionListener(getMouseManager());

        // initialize the game's assets
        Assets.init();

        // start the game
        initItems();
    }

    /**
     * Creates the items that will be used in the game.
     */
    private void initItems() {
        player = new Player((getWidth() / 2) - 24, 570, 22, 22, this);
        stars = new ArrayList();
        enemies = new Enemy[MAX_ENEMY_ROWS][MAX_ENEMY_COLUMNS];

        // create stars
        for (int i = 0; i < MAX_STARS; i++) {
            int size = Util.randNum(1, 2);
            stars.add(new Star(Util.randNum(0, getWidth()), Util.randNum(0, getHeight()), size, size, size));
        }

        //create aliens
        int tempY = 4;
        for (int r = 0; r < MAX_ENEMY_ROWS; r++) {
            int tempX = 4;
            for (int c = 0; c < MAX_ENEMY_COLUMNS; c++) {
                int w = 40;
                int h = 35;
                int posX = w * c + 20;
                int posY = h * r + 15;
                enemies[r][c] = new Enemy(posX + tempX, posY + tempY, w, h, this);
                tempX += 4;
            }
            tempY += 4;
        }
        direction = 1;
        changes = 0;
        setPaused(false);
        setGameOver(false);
        setLives(3);
    }

    /**
     * Starts the main game thread.
     */
    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stops the game thread execution.
     */
    public synchronized void stop() {
        if (isRunning) {
            isRunning = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the game every frame.
     */
    private void update() {
        if (!isPaused() && !isGameOver()) {
            player.update();

            // update stars
            for (int i = 0; i < stars.size(); i++) {
                Star star = stars.get(i);

                // if stars reaches end of screen, reset position
                if (star.getY() >= getHeight()) {
                    star.setY(0);
                    star.setX(Util.randNum(0, getWidth()));
                }
                star.update();
            }

            // update enemies
            int startingDirection = direction;
            int dirConstant = 1;
            if(changes >= 3) {
                dirConstant = 2;
            } 
            if(changes >= 6) {
                dirConstant = 3;
            }
            if(changes >= 10) {
                dirConstant = 4;
            }
            for (int r = 0; r < MAX_ENEMY_ROWS; r++) {
                for (int c = 0; c < MAX_ENEMY_COLUMNS; c++) {
                    // if we already changed direction in this frame, exit
                    if (startingDirection != direction) {
                        break;
                    }

                    Enemy enemy = enemies[r][c];
                    if (enemy.isDead()) {
                        continue;
                    }

                    // update this enemy
                    enemy.setVelX(direction);
                    enemy.update();

                    // check for player collision
                    if(enemy.intersects(player)) {
                        setGameOver(true);
                        return;
                    }
                    
                    // shoot randomly
                    int rng = Util.randNum(0, 1000);
                    if (rng == 10) {
                        enemy.shoot();
                    }

                    // collission with borders
                    if (enemy.getX() + enemy.getWidth() >= getWidth()) {
                        direction = dirConstant * -1;
                    } else if (enemy.getX() <= 0) {
                        direction = dirConstant;
                    }

                    // if we changed direction, set it to all enemies
                    if (startingDirection != direction) {
                        changes++;
                        System.out.println(changes);
                        if (direction < 0) {
                            for (int c2 = 0; c2 < MAX_ENEMY_COLUMNS; c2++) {
                                Enemy e = enemies[r][c2];
                                e.setX(e.getX() - Math.abs(direction));
                            }
                        } else {
                            enemy.setX(enemy.getX() + direction);
                        }

                        // move enemies down
                        for (int r2 = 0; r2 < MAX_ENEMY_ROWS; r2++) {
                            for (int c2 = 0; c2 < MAX_ENEMY_COLUMNS; c2++) {
                                Enemy newEnemy = enemies[r2][c2];
                                newEnemy.setY(newEnemy.getY() + 10);
                            }
                        }
                    }
                }
            }
        }
        
        // check if player pauses the game
        if(getKeyManager().isKeyPressed(KeyEvent.VK_P)) {
            setPaused(!isPaused());
        }
        
        // check if player wants to restart
        if(getKeyManager().isKeyPressed(KeyEvent.VK_R)) {
            initItems();
        }
        
        // check if player wants to save
        if(getKeyManager().isKeyPressed(KeyEvent.VK_G)) {
            saveGame();
        }
        
        // check if player wants to load
        if(getKeyManager().isKeyPressed(KeyEvent.VK_C)) {
            loadGame();
        }
        
        // update input
        getKeyManager().update();
        getMouseManager().update();
    }

    /**
     * Renders the game every frame.
     */
    private void render() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();

        // if there's no buffer strategy yet, create it
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            // clear screen
            Graphics g = bs.getDrawGraphics();
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            for (int i = 0; i < stars.size(); i++) {
                stars.get(i).render(g);
            }

            player.render(g);

            // render enemies
            for (int r = 0; r < MAX_ENEMY_ROWS; r++) {
                for (int c = 0; c < MAX_ENEMY_COLUMNS; c++) {
                    Enemy enemy = enemies[r][c];
                    if (enemy.isDead()) {
                        continue;
                    }
                    enemy.render(g);
                }
            }
            
            // render lives
            g.setColor(Color.WHITE);
            g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
            g.drawString("Lives: " + getLives(), 20, 40);
            
            // render paused screen
            if (isPaused()) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(new Font("Century Gothic", Font.BOLD, 40));
                g.drawString("PAUSED", 328, 300);
            }
            
            // render game over screen
            if (isGameOver()) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.RED);
                g.setFont(new Font("Century Gothic", Font.BOLD, 40));
                g.drawString("You got invaded!", 242, 300);
            }
            
            // actually render the whole scene
            bs.show();
            g.dispose();
        }
    }

    /**
     * Creates a text file which stores the game's current state.
     */
    private void saveGame() {
        try {
            // create the file
            FileWriter fw = new FileWriter("save.txt");

            // save player info
            fw.write(String.valueOf(player.getX()) + '\n');
            fw.write(String.valueOf(player.getY()) + '\n');
            fw.write(String.valueOf(getLives()) + '\n');
            
            // save enemies state info
            fw.write(String.valueOf(direction) + '\n');
            fw.write(String.valueOf(changes) + '\n');
            
            // save enemies info
            for(int r = 0; r < MAX_ENEMY_ROWS; r++) {
                for(int c = 0; c < MAX_ENEMY_COLUMNS; c++) {
                    Enemy enemy = enemies[r][c];
                    fw.write(String.valueOf(enemy.getX()) + '\n');
                    fw.write(String.valueOf(enemy.getY()) + '\n');
                    int dead = enemy.isDead() ? 1 : 0;
                    fw.write(String.valueOf(dead) + '\n');
                }
            }
            
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Attempts to load a save file that contains the state of another game, and sets it to the current game.
     */
    private void loadGame() {
        try {
            // check if file exists 
            if(!new File("save.txt.").exists()) {
                return;
            }
            
            // read the file
            BufferedReader br = new BufferedReader(new FileReader("save.txt"));

            // load player info
            player.setX(Integer.parseInt(br.readLine()));
            player.setY(Integer.parseInt(br.readLine()));
            setLives(Integer.parseInt(br.readLine()));
            
            // load alien state info
            direction = Integer.parseInt(br.readLine());
            changes = Integer.parseInt(br.readLine());
            
            // load alien info
            for(int r = 0; r < MAX_ENEMY_ROWS; r++) {
                for(int c = 0; c < MAX_ENEMY_COLUMNS; c++) {
                    Enemy enemy = enemies[r][c];
                    enemy.setX(Integer.parseInt(br.readLine()));
                    enemy.setY(Integer.parseInt(br.readLine()));
                    int dead = Integer.parseInt(br.readLine());
                    enemy.setDead(dead == 1);
                }
            }
            
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * The main game loop.
     */
    @Override
    public void run() {
        init();

        // set up timing constants
        final int maxFPS = 60;
        final double timeTick = 1000000000 / maxFPS;

        // set up timing variables
        double delta = 0.0d;
        long now = 0;
        long lastTime = System.nanoTime();

        // start game loop
        while (isRunning) {
            // calculate delta
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;

            // make sure game always plays at desired fps
            if (delta >= 1.0d) {
                update();
                render();
                delta--;
            }
        }

        // once game loop is over, close the game
        stop();
    }
}
