package models;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * 
 * 
 */
public class Tower_Fire extends Tower {
	double DOT;
	int damageOverTimeLength = 20;
	static int buyCost = 200;
	double slowFactor;
	int slowTime;
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
		damage = 0.25;
		rateOfFire = 10;
		range = 100;
		sellPrice = 100;
		upCost = 400;
		slowFactor = 0.1;
		tColor = Color.red;
		shotColor = Color.red;
		buyCost = 200;
		slowTime = 25;
		DOT = 1;
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
    public void upgradeTower(){
    	if(level < MAXTOWERLEVEL){
	    	super.upgradeTower();
			this.slowTime = (this.slowTime + 20);
			this.slowFactor = this.slowFactor + 0.1;
			this.DOT = this.DOT*2;
			this.damageOverTimeLength = (int) (this.damageOverTimeLength*1.5);
    	}
    }

}
