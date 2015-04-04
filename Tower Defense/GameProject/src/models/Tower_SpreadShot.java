
package models;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Tower_SpreadShot extends Tower {
	private int amountOfTargets = 3;
	public Tower_SpreadShot(String n, Point p, ArrayList<Critter> crittersOnMap) {
		super(n, p, crittersOnMap);
		//these variables are all explicitly written as all laser towers will have the same starting stats
		//all values are place holders
		damage = 0.5;
		rateOfFire = 10;
		range = 200;
		sellPrice = 125;
		upCost = 200;
		slowFactor = 0.0;
		damageOverTime = false;
		areaOfAffect = true;
		tColor = Color.YELLOW;
		shotColor = Color.YELLOW;
		buyCost = 250;
		slowTime = 0;
	}
	
	//overrides the updateAndDraw method in the tower class to shoot more than one critter.
	public void updateAndDraw(Graphics g){	
		ArrayList<Critter> inRangeC = new ArrayList<Critter>();
		ArrayList<Critter> targets = new ArrayList<Critter>();
		
		this.drawTower(g);
		inRangeC = this.findCrittersInRange(potentialCrittersInRange);
		Critter targetedCritter = this.selectTarget(this, inRangeC);
		for(int i = 0; i < amountOfTargets; i++){
			targets.add(targetedCritter);
			inRangeC.remove(targetedCritter);
			targetedCritter = this.selectTarget(this, inRangeC);
		}
		if(!targets.isEmpty()){
			for(Critter c : targets){
				if(c != null){
					this.shootTarget(c, g);
				}
			}
		}
	}

}
