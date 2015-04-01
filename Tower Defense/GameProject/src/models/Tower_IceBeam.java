package models;

import java.awt.Color;
import java.util.ArrayList;

public class Tower_IceBeam extends Tower {

	public Tower_IceBeam(String n, Point p, ArrayList<Critter> crittersOnMap) {
		super(n, p, crittersOnMap);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 0.1;
		rateOfFire = 10;
		range = 100;
		sellPrice = 75;
		upCost = 200;
		slowFactor = 0.5;
		damageOverTime = false;
		areaOfAffect = false;
		tColor = Color.white;
		shotColor = Color.WHITE;
		buyCost = 150;
		slowTime = 30;
	}

}
