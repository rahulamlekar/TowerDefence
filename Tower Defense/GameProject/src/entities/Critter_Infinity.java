package entities;

import helpers.EntityColor;

/*
 * infinity critter is BOSS critter. Hard to kill, but high rewards
 */
public class Critter_Infinity extends Critter{
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
		speed = (int) (10*levelMultiplier);
		//high resistance
		resistance = 0.5*levelMultiplier;
		//set name
		name = "Infinity Critter";
		strength = 3;
		color = "Yellow";
	}
}
