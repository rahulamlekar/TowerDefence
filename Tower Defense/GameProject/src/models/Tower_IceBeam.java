package models;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * 
 * 
 */
public class Tower_IceBeam extends Tower {
	//properties for the ice beam tower
	static int buyCost = 150;
	double slowFactor;
	int slowTime;
    /**
     *
     * @param n
     * @param p
     * @param crittersOnMap
     */
    public Tower_IceBeam(String n, Point p, ArrayList<Critter> crittersOnMap) {
		super(n, p, crittersOnMap);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		damage = 0.25;
		rateOfFire = 10;
		range = 100;
		sellPrice = 75;
		upCost = 350;
		slowFactor = 0.5;
		tColor = Color.WHITE;
		shotColor = Color.WHITE;
		buyCost = 150;
		slowTime = 30;
	}
	
	/*
	 * (non-Javadoc)
	 * @see models.Tower#shootTarget(models.Critter, java.awt.Graphics)
	 * Shoots the target and applies slow
	 */
    protected void shootTarget(Critter target, Graphics g){
		super.shootTarget(target, g);
		target.slowCritter(this.slowFactor, this.slowTime);
	}
	
    /**
     *
     * @return
     */
    public static int getBuyPrice(){	
		return buyCost;
	}
    /*
     * (non-Javadoc)
     * @see models.Tower#upgradeTower()
     * Upgrades the tower and the slow
     */
    public void upgradeTower(){
    	if(level < MAXTOWERLEVEL){
	    	super.upgradeTower();
			this.slowTime = (this.slowTime + 20);
			this.slowFactor = this.slowFactor + 0.1;
    	}
    }

}
