package models;

import java.awt.Color;


/*
 * shuriken critter is Strong but slow
 */

/**
 *
 * 
 */

public class Critter_Shrunken extends Critter{

    /**
     *
     * @param level
     * @param m
     */
    public Critter_Shrunken(int level, TDMap m) {
		super(level, m);
		//average reward
		reward = (int) (10*levelMultiplier);
		//high hitpoints
		currHitPoints = (300*levelMultiplier);
		maxHitPoints = currHitPoints;
		//medium regen
		regen = 2*levelMultiplier;
		//low speed
		speed = (3*levelMultiplier);
		//med resistance
		resistance = 0.3*levelMultiplier;
		//set name
		name = "Shuriken Critter";
		cColor = Color.PINK;
	}
}
