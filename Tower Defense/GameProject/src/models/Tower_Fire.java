package models;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Yash Gupta
 */
public class Tower_Fire extends Tower {
	double DOT;
	int damageOverTimeLength = 20;
	static int buyCost;

    /**
     *
     * @param n
     * @param p
     * @param crittersOnMap
     */
    public Tower_Fire(String n, Point p, ArrayList<Critter> crittersOnMap) {
		super(n, p, crittersOnMap);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 0.1;
		rateOfFire = 2;
		range = 100;
		sellPrice = 100;
		upCost = 200;
		slowFactor = 0.0;
		damageOverTime = true;
		areaOfAffect = false;
		tColor = Color.red;
		shotColor = Color.red;
		buyCost = 200;
		slowTime = 25;
		DOT = 5;
	}
	
    /**
     *
     * @param target
     * @param g
     */
    protected void shootTarget(Critter target, Graphics g){
		super.shootTarget(target, g);
		target.damageOverTimeCritter(this.DOT, this.damageOverTimeLength);
	}
	
    /**
     *
     * @return
     */
    public static int getBuyPrice(){	
		return buyCost;
	}

}
