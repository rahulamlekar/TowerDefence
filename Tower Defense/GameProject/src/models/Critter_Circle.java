package models;

import java.awt.Color;

/*
 * Circle critter is average, run of the mill critter
 */
public class Critter_Circle extends Critter{
	
    /**
     *
     * @param level
     * @param m
     */
    public Critter_Circle(int level, TDMap m) {
		super(level, m);
		//average reward
		reward = (int) (10*levelMultiplier);
		//average hitpoints
		currHitPoints = (100*levelMultiplier);
		maxHitPoints = currHitPoints;
		//low regen
		regen = (1*levelMultiplier);
		//average speed
		speed = (5*levelMultiplier);
		//no resistance
		resistance = 0;
		//set name
		name = "Circle Critter";
		cColor = Color.CYAN;
	}

	
}
