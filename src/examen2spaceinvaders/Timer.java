package examen2spaceinvaders;

/**
 * Timer
 * 
 * Makes reacting to events based on time easier to manage.
 * @author Cesar Barraza
 * Date 30/Jan/2019
 * @version 1.0
 */
public class Timer {
    /**
     * Used for counting frames passed.
     */
    private double count;
    
    /**
     * Used to check when this timer should activate.
     */
    private double secondsToActivate;
    
    /**
     * Determines if the timer is activated or not.
     */
    private boolean activated;

    /**
     * Initializes the timer.
     * @param secondsToActivate seconds it takes to fire this timer.
     */
    public Timer(double secondsToActivate) {
        this.secondsToActivate = secondsToActivate * 60.0d;
        this.count = 0.0d;
        this.activated = false;
    }

    /**
     * @return whether this timer is activated or not
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sets the timer to its initial state.
     */
    public void restart() {
        count = 0.0d;
        activated = false;
    }

    /**
     * Updates the timer state.
     */
    public void update() {
        if(!activated) {
            count += 1.0d;
            if(count >= secondsToActivate) {
                activated = true;
            }
        }
    }
}
