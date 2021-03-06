package examen2spaceinvaders;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Display
 * Object that handles a JFrame window for the game.
 *
 * @author César Barraza A01176786
 * @author Isabel Cruz A01138741
 * Date 9/March/2019
 * @version 1.0
 */
public class Display {
    /**
     * The JFrame that will represent our window.
     */
    private JFrame window;

    /**
     * The canvas that will be used to render graphics over our window.
     */
    private Canvas canvas;

    /**
     * The title that our window will have.
     */
    private String title;

    /**
     * The width that our window will have.
     */
    private int width;

    /**
     * The height that our window will have.
     */
    private int height;

    /**
     * Constructor for building the display with the desired properties.
     *
     * @param title
     * @param width
     * @param height
     */
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    /**
     * @return the canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * @return the canvas
     */
    public JFrame getWindow() {
        return window;
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
     * Creates a JFrame with a Canvas using the display's properties.
     */
    private void createDisplay() {
        // create the window object
        window = new JFrame(getTitle());
        window.setSize(new Dimension(getWidth(), getHeight()));
        window.setDefaultCloseOperation(EXIT_ON_CLOSE); // ends the program if the user clicks 'X' on the window
        window.setResizable(false);
        window.setLocationRelativeTo(null); // makes sure the window's initial position is centered on the screen
        window.setVisible(true);

        // create the canvas object and adjust it to our window
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(getWidth(), getHeight()));
        canvas.setMaximumSize(new Dimension(getWidth(), getHeight()));
        canvas.setFocusable(false);

        // add the canvas to the window
        window.add(canvas);
        window.pack();
    }
}