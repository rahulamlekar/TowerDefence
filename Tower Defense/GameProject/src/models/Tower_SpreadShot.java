package models;

import java.awt.Color;
import java.util.ArrayList;

public class Tower_SpreadShot extends Tower {

	public Tower_SpreadShot(String n, Point p, ArrayList<Critter> crittersOnMap) {
		super(n, p, crittersOnMap);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 5;
		rateOfFire = 1;
		range = 200;
		sellPrice = 125;
		upCost = 200;
		slowFactor = 0.0;
		damageOverTime = false;
		areaOfAffect = true;
		tColor = Color.YELLOW;
		shotColor = Color.YELLOW;
		buyCost = 250;
		slowTime = 0;
	}

}
