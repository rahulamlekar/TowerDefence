package entities;

import java.awt.Color;
import java.util.ArrayList;

public class Tower_Fire extends Tower {

	public Tower_Fire(String n, Point p, ArrayList<Critter> crittersOnMap, TDMap map) {
		super(n, p, crittersOnMap, map);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 0.1;
		rateOfFire = 10;
		range = 100;
		sellPrice = 10;
		upCost = 200;
		slowFactor = 0.0;
		damageOverTime = true;
		areaOfAffect = false;
		tColor = Color.red;
		shotColor = Color.red;
		buyCost = 200;
	}

}
