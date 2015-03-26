package entities;

import java.awt.Color;

/*
 * Circle critter is average, run of the mill critter
 */
public class Critter_Circle extends Critter{
	
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
		speed = (int) (8*levelMultiplier);
		//no resistance
		resistance = 0;
		//set name
		name = "Circle Critter";
		cColor = Color.CYAN;
	}

	
}
