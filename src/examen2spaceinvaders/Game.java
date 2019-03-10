package examen2spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Game Handles everything that the game needs to function correctly.
 *
 * @author César Barraza A01176786 
 * @author Isabel Cruz A01138741
 * Date 09/March/2019
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
    private Shield[] shields;
    private Enemy[][] enemies;
    private int direction;
    private boolean paused;
    private boolean gameOver;
    private int changes;
    private int lives;
    private int destroyed;
    private int score;
    private int highscore;
    
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
     * @return the destroyed enemies
     */
    public int getDestroyed() {
        return destroyed;
    }
    
    /**
     * @param destroyed destoryed enemies to set
     */
    public void setDestroyed(int destroyed) {
        this.destroyed = destroyed;
    }
    
    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * @param score score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * @return the highscore
     */
    public int getHighscore() {
        return highscore;
    }
    
    /**
     * @param highscore to set
     */
    public void setHighscore(int highscore) {
        this.highscore = highscore;
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
        shields = new Shield[4];

        // create shields
        shields[0] = new Shield(60, 500, 78, 45);
        shields[1] = new Shield(250, 500, 78, 45);
        shields[2] = new Shield(450, 500, 78, 45);
        shields[3] = new Shield(660, 500, 78, 45);
                
        // create stars
        for (int i = 0; i < MAX_STARS; i++) {
            int size = Util.randNum(1, 2);
            stars.add(new Star(Util.randNum(0, getWidth()), Util.randNum(0, getHeight()), size, size, size));
        }

        loadHighscore();
        initAliens();
        direction = 1;
        changes = 0;
        setPaused(false);
        setGameOver(false);
        setLives(3);
        setDestroyed(0);
        setScore(0);
    }

    /**
     * Creates the aliens of the game.
     */
    private void initAliens() {
        int tempY = 4;
        for (int r = 0; r < MAX_ENEMY_ROWS; r++) {
            int tempX = 4;
            for (int c = 0; c < MAX_ENEMY_COLUMNS; c++) {
                int w = 40;
                int h = 35;
                int posX = w * c + 20;
                int posY = h * r + 15;
                BufferedImage img = null;
                if(r == 0) {
                    img = Assets.alien3;
                } else if(r == MAX_ENEMY_ROWS - 1) {
                    img = Assets.alien2;
                } else {
                    img = Assets.alien1;
                }
                enemies[r][c] = new Enemy(posX + tempX, posY + tempY, w, h, this, img);
                tempX += 4;
            }
            tempY += 4;
        }   
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
            if(changes >= 5) {
                dirConstant = 2;
            } 
            if(changes >= 9) {
                dirConstant = 3;
            }
            if(changes >= 16) {
                dirConstant = 4;
            }
            if(changes >= 35) {
                dirConstant = 5;
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
                        saveHighscore();
                        return;
                    }
                    
                    // check for bullet collision against enemy
                    if(player.getBullet() != null) {
                        if(enemy.intersects(player.getBullet())) {
                            setDestroyed(getDestroyed() + 1);
                            enemy.setDead(true);
                            player.setBullet(null);
                            Assets.invaderExplosion.play();
                            
                            int points = 0;
                            if(r == 0) {
                                points = 30;
                            } else if(r == MAX_ENEMY_ROWS - 1) {
                                points = 10;
                            } else {
                                points = 5;
                            }
                            setScore(getScore() + points);
                            continue;
                        }
                    }
                    
                    // check for bullet collision against player
                    if(enemy.getBullet() != null) {
                        if(enemy.getBullet().intersects(player)) {
                            setLives(getLives() - 1);
                            enemy.setBullet(null);
                            Assets.playerExplosion.play();
                            
                            if(getLives() <= 0) {
                                setGameOver(true);
                                saveHighscore();
                                return;
                            }
                        }
                    }
                    
                    // check for bullets collision
                    if(enemy.getBullet() != null && player.getBullet() != null) {
                        if(enemy.getBullet().intersects(player.getBullet())) {
                            enemy.setBullet(null);
                            player.setBullet(null);
                        }
                    }
                    
                    // check collision enemybullet with shield
                    for (int i = 0; i < 4; i++) {
                        if (enemy.getBullet() != null) {
                            if(shields[i].isDead()) {
                                continue;
                            }
                            if (enemy.getBullet().intersects(shields[i])) {
                                shields[i].setLives(shields[i].getLives() - 1);
                                enemy.setBullet(null);

                                if (shields[i].getLives() == 0) {
                                    shields[i].setDead(true);
                                }
                            }
                        }
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
        for (int i = 0; i < 4; i++) {
            if (player.getBullet() != null) {
                if(shields[i].isDead()) {
                    continue;
                }
                if (player.getBullet().intersects(shields[i])) {
                    shields[i].setLives(shields[i].getLives() - 1);
                    player.setBullet(null);

                    if (shields[i].getLives() == 0) {
                        shields[i].setDead(true);
                    }
                }
            }
        }
        
        // check if all enemies were killed to respawn them
        if(getDestroyed() >= MAX_ENEMY_ROWS * MAX_ENEMY_COLUMNS) {
            initAliens();
            changes = 0;
            direction = 1;
            setDestroyed(0);
        }
        
        // check if player pauses the game
        if(getKeyManager().isKeyPressed(KeyEvent.VK_P) && !isGameOver()) {
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
            
            // render shields
            for(int i = 0; i < 4; i++) {
                if(shields[i].isDead()) {
                    continue;
                }
                shields[i].render(g);
            }

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
            
            // render lives and score
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, 210, 100);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Century Gothic", Font.PLAIN, 22));
            g.drawString("Lives: " + getLives(), 15, 30);
            g.drawString("Score: " + getScore(), 15, 52);
            g.drawString("Highscore: " + getHighscore(), 15, 74);
            
            // render paused screen
            if (isPaused()) {
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(new Font("Century Gothic", Font.BOLD, 40));
                g.drawString("PAUSED", 328, 300);
                // Menu
                g.setColor(Color.MAGENTA);
                g.setFont(new Font("Century Gothic", Font.PLAIN, 22));
                g.drawString("Menu: ", 630, 30);
                g.drawString("L - Load game", 630, 52);
                g.drawString("P - Pause game", 630, 74);
                g.drawString("S - Save game", 630, 96);
                g.drawString("R - Restart", 630, 118);
            }
            
            // render game over screen
            if (isGameOver()) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.RED);
                g.setFont(new Font("Century Gothic", Font.BOLD, 40));
                g.drawString("You got invaded!", 230, 300);
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
            fw.write(String.valueOf(getScore()) + '\n');
            
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
            
            // save shields
            for(int i = 0; i < 4; i++) {
                fw.write(String.valueOf(shields[i].getLives()) + '\n');
                int dead = shields[i].isDead() ? 1 : 0;
                fw.write(String.valueOf(dead) + '\n');
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
            setScore(Integer.parseInt(br.readLine()));
            
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
            
            // load shields info
            for(int i = 0; i < 4; i++) {
                shields[i].setLives(Integer.parseInt(br.readLine()));
                int dead = Integer.parseInt(br.readLine());
                shields[i].setDead(dead == 1);
            }
            
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void saveHighscore() {
        try {
            // load highscore and compare
            loadHighscore();
            
            // create the file
            FileWriter fw = new FileWriter("hs.txt"); 
            if(getScore() > getHighscore()) {
                setHighscore(getScore());
                fw.write(String.valueOf(getScore()) + '\n');   
            } else {
                fw.write(String.valueOf(getHighscore()) + '\n');
            }
            
            fw.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void loadHighscore() {
        try {
            // check if file exists 
            if(!new File("hs.txt").exists()) {
                setHighscore(0);
                return;
            }
            
            // read the file
            BufferedReader br = new BufferedReader(new FileReader("hs.txt"));  
            setHighscore(Integer.parseInt(br.readLine()));
            br.close();
        } catch(IOException ex) {
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
