package entities;
/*
 * Square Critters are weak but travel in groups
 */
public class Critter_Square extends Critter{

	public Critter_Square(int level, TDMap m) {
		super(level, m);
		//low reward
		reward = (int) (5*levelMultiplier);
		//low hitpoints
		currHitPoints = (50*levelMultiplier);
		maxHitPoints = currHitPoints;
		//no regen
		regen = 0;
		//low speed
		speed = (int) (10*levelMultiplier);
		resistance = 0;
		//set name
		name = "Square Critter";
		strength = 1;
		
	}

}
