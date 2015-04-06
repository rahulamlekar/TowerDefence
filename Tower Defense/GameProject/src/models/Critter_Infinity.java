package models;

import java.awt.Color;


/*
 * infinity critter is BOSS critter. Hard to kill, but high rewards
 */
public class Critter_Infinity extends Critter{
	public Critter_Infinity(int level, TDMap m) {
		super(level, m);
		//high reward
		reward = (int) (20*levelMultiplier);
		//high hitpoints
		currHitPoints = (150*levelMultiplier);
		maxHitPoints = currHitPoints;
		//high regen
		regen = (2*levelMultiplier);
		//medium-high speed
		speed = (8*levelMultiplier);
		//high resistance
		resistance = 0.5*levelMultiplier;
		//set name
		name = "Infinity Critter";
		cColor = Color.YELLOW;
	}
}
