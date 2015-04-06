package models;

import java.awt.Graphics;

/*
 * This is an interface from which all game entities much implement.
 * This is so that all entities have an updateAndDraw() method, for chronology.
 */

/**
 *
 * 
 * @author Yash Gupta
 */

public interface DrawableEntity {
	//this method ensures that all drawable entities are synced with time.

    /**
     *
     * @param g
     */
    	public void updateAndDraw(Graphics g);
}
