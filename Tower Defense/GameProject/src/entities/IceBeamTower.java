package entities;

import java.awt.Color;
import java.util.ArrayList;

public class IceBeamTower extends Tower {

	public IceBeamTower(String n, Point p, int size, ArrayList<Critter> crittersOnMap) {
		super(n, p, size, crittersOnMap);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 0.1;
		rateOfFire = 10;
		range = 100;
		buyCost = 10;
		sellPrice = 10;
		upCost = 10;
		slow = true;
		damageOverTime = false;
		areaOfAffect = false;
		tColor = new Color(100,100,100);
	}

}
