package models;

import java.awt.Color;


/*
 * Red Critters are weak 
 */

/**
 *
 * 
 * 
 */

public class Critter_Red extends Critter{

    /**
     *
     * @param level
     * @param m
     */
    public Critter_Red(int level, TDMap m) {
		super(level, m);
		double levelMultiplier = calculateLevelMultiplier();
		//low reward
		reward = (int) (12*levelMultiplier);
		//low hitpoints
		currHitPoints = (180*levelMultiplier);
		maxHitPoints = currHitPoints;
		//no regen
		regen = 0;
		//low speed
		speed = Math.min(4*levelMultiplier, MAXSPEED);
		resistance = 0;
		//set name
		name = "Square Critter";
		cColor = Color.RED;
	}

}
