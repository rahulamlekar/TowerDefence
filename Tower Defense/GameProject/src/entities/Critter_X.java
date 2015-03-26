package entities;

import java.awt.Color;

/*
 * Critter X is completely resitant to effects from towers. Average apart from that.
 */
public class Critter_X extends Critter {
	public Critter_X(int level, TDMap m) {
		super(level, m);
		//high reward
		reward = (int) (20*levelMultiplier);
		//med hitpoints
		currHitPoints = (100*levelMultiplier);
		maxHitPoints = currHitPoints;
		//low regen
		regen = 1*levelMultiplier;
		//low speed
		speed = (int) (5*levelMultiplier);
		//pure resistancfe
		resistance = 1;
		//set name
		name = "X Critter";
		cColor = Color.ORANGE;
	}
}
