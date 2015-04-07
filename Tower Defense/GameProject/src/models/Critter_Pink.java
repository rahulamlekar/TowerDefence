package models;

import java.awt.Color;


/*
 * Pink critter is Strong but slow
 */

/**
 *
 * 
 */

public class Critter_Pink extends Critter{

    /**
     *
     * @param level
     * @param m
     */
    public Critter_Pink(int level, TDMap m) {
		super(level, m);
		double levelMultiplier = calculateLevelMultiplier();
		//average reward
		reward = (int) (10*levelMultiplier);
		//high hitpoints
		currHitPoints = (300*levelMultiplier);
		maxHitPoints = currHitPoints;
		//medium regen
		regen = 2*levelMultiplier;
		//low speed
		speed = Math.min(2*levelMultiplier, MAXSPEED);
		//med resistance
		resistance = 0.3*levelMultiplier;
		//set name
		name = "Shuriken Critter";
		cColor = Color.PINK;
	}
}
