package entities;

import java.awt.Color;
import java.util.ArrayList;

public class BombTower extends Tower {

	public BombTower(String n, Point p, int size, ArrayList<Critter> crittersOnMap) {
		super(n, p, size, crittersOnMap);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 10;
		rateOfFire = 10;
		range = 10;
		buyCost = 10;
		sellPrice = 10;
		upCost = 10;
		slow = false;
		damageOverTime = false;
		areaOfAffect = true;
		tColor = new Color(175,175,175);
	}

}
