package examen2spaceinvaders;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * MouseManager
 * 
 * Listens for mouse events such as released and pressed buttons.
 * @author César Barraza A01176786
 * Date 07/Feb/2019
 * @version 1.0
 */
public class MouseManager implements MouseListener, MouseMotionListener {
    /**
     * The mouse coordinates in the screen.
     */
    private int x;
    private int y;
    
    /**
     * Arrays that hold the state of all possible buttons.
     */
    private boolean buttons[];
    private boolean pressedButtons[];
 
    /**
     * Initializes the size of the buttons array.
     */
    MouseManager() {
        buttons = new boolean[4];
        pressedButtons = new boolean[4];
    }
    
    /**
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the new x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the new y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Fires when the user pressed a mouse button.
     * @param e holds information about the pressed button
     */
    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }
    
    /**
     * Fires when the user released a pressed mouse button.
     * @param e holds information about the released button
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }
    
    /**
     * Fires when the mouse is moved without pressing any buttons.
     * @param e holds information about the mouse position
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        setX(e.getX());
        setY(e.getY());
    }

    /**
     * Fires when the mouse is moved when the user is pressing buttons.
     * @param e holds information about the mouse position
     */
    @Override
    public void mouseDragged(MouseEvent e) { 
        setX(e.getX());
        setY(e.getY());
    }
    
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
    /**
     * Determines the state of a button.
     * @param button the button's index
     * @return whether the button is down or not
     */
    public boolean isButtonDown(int button) {
        return buttons[button];
    }
    
    /**
     * Determines if a button was just pressed.
     * @param button the button's index
     * @return whether the button was just pressed or not
     */
    public boolean isButtonPressed(int button) {
        return buttons[button] && !pressedButtons[button];
    }
    
    /**
     * Determines if a button was just released.
     * @param button the button's index
     * @return whether the button was just released or not
     */
    public boolean isButtonReleased(int button) {
        return !buttons[button] && pressedButtons[button];
    }
    
    /**
     * Determines if the mouse is inside a rectangular area in the screen.
     * @param rect the rectangular area in the screen
     * @return whether or not the mouse is inside the rectangle
     */
    public boolean intersects(Rectangle rect) {
        return ((getX() >= rect.getX() && getX() <= rect.getX() + rect.getWidth()) &&
                (getY() >= rect.getY() && getY() <= rect.getY() + rect.getHeight()));
    }
    
    /**
     * Updates the state of the mouse buttons.
     */
    public void update() {
        for(int i = 1; i <= 3; i++) {
            pressedButtons[i] = buttons[i];
        }
    }
}
