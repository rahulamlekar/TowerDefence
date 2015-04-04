package models;

import java.awt.Color;
import java.util.ArrayList;

public class Tower_Laser extends Tower{

	public Tower_Laser(String name, Point p, ArrayList<Critter> crittersOnMap) {
		super(name,p,crittersOnMap);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 0.5;
		rateOfFire = 10;
		range = 300;
		sellPrice = 50;
		upCost = 200;
		slowFactor = 0;
		damageOverTime = false;
		areaOfAffect = false;
		tColor = Color.black;
		shotColor = Color.black;
		buyCost = 100;
		slowTime = 0;
	}

	
}
