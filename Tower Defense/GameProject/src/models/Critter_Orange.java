package models;

import java.awt.Color;

/*
 * Critter X is completely resitant to effects from towers. Average apart from that.
 */

/**
 *
 * 
 * 
 */

public class Critter_Orange extends Critter {

    /**
     *
     * @param level
     * @param m
     */
    public Critter_Orange(int level, TDMap m) {
		super(level, m);
		double levelMultiplier = calculateLevelMultiplier();
		//high reward
		reward = (int) (12*levelMultiplier);
		//med hitpoints
		currHitPoints = (175*levelMultiplier);
		maxHitPoints = currHitPoints;
		//low regen
		regen = 1*levelMultiplier;
		//low speed
		speed = Math.min(4*levelMultiplier, MAXSPEED);
		//pure resistancfe
		resistance = 1;
		//set name
		name = "X Critter";
		cColor = Color.ORANGE;
	}
}
