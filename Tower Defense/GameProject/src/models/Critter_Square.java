package models;

import java.awt.Color;


/*
 * Square Critters are weak but travel in groups
 */
public class Critter_Square extends Critter{

	public Critter_Square(int level, TDMap m) {
		super(level, m);
		//low reward
		reward = (int) (12*levelMultiplier);
		//low hitpoints
		currHitPoints = (180*levelMultiplier);
		maxHitPoints = currHitPoints;
		//no regen
		regen = 0;
		//low speed
		speed = (5*levelMultiplier);
		resistance = 0;
		//set name
		name = "Square Critter";
		cColor = Color.RED;
	}

}
