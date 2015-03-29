package entities;

import java.awt.Color;
import java.util.ArrayList;

public class Tower_Bomb extends Tower {

	public Tower_Bomb(String n, Point p, ArrayList<Critter> crittersOnMap, TDMap map) {
		super(n, p, crittersOnMap, map);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 5;
		rateOfFire = 1;
		range = 500;
		sellPrice = 10;
		upCost = 10;
		slowFactor = 0.0;
		damageOverTime = false;
		areaOfAffect = true;
		tColor = new Color(175,175,175);
		shotColor = Color.YELLOW;
		buyCost = 250;
	}

}
