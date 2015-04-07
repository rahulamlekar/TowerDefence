package models;

import java.awt.Color;


/*
 * White critter is fast but weak compared to other critters
 */

/**
 *
 * 
 */

public class Critter_White extends Critter{

    /**
     *
     * @param level
     * @param m
     */
    public Critter_White(int level, TDMap m) {
		super(level, m);
		double levelMultiplier = calculateLevelMultiplier();
		//average reward
		reward = (int) (7*levelMultiplier);
		//low hitpoints
		currHitPoints = (140*levelMultiplier);
		maxHitPoints = currHitPoints;
		//does not regenerate health
		regen = 0;
		//fast
		speed = Math.min(10*levelMultiplier, MAXSPEED);
		//does not resist effects
		resistance = 0;
		//set name
		name = "Arrow Critter";
		cColor = Color.WHITE;
	}
}
