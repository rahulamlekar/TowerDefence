package entities;

import java.awt.Color;
import java.util.ArrayList;

public class Tower_IceBeam extends Tower {

	public Tower_IceBeam(String n, Point p, ArrayList<Critter> crittersOnMap, TDMap map) {
		super(n, p, crittersOnMap, map);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 0.1;
		rateOfFire = 10;
		range = 100;
		sellPrice = 10;
		upCost = 10;
		slowFactor = 0.5;
		damageOverTime = false;
		areaOfAffect = false;
		tColor = new Color(100,100,100);
		shotColor = Color.WHITE;
		buyCost = 150;
	}

}
