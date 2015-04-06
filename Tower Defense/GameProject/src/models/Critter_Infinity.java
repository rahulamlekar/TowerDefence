package models;

import java.awt.Color;


/*
 * infinity critter is BOSS critter. Hard to kill, but high rewards
 */

/**
 *
 * @author Yash Gupta
 */

public class Critter_Infinity extends Critter{

    /**
     *
     * @param level
     * @param m
     */
    public Critter_Infinity(int level, TDMap m) {
		super(level, m);
		//high reward
		reward = (int) (30*levelMultiplier);
		//high hitpoints
		currHitPoints = (200*levelMultiplier);
		maxHitPoints = currHitPoints;
		//high regen
		regen = (3*levelMultiplier);
		//medium-high speed
		speed = (10*levelMultiplier);
		//high resistance
		resistance = 0.5*levelMultiplier;
		//set name
		name = "Infinity Critter";
		cColor = Color.YELLOW;
	}
}
