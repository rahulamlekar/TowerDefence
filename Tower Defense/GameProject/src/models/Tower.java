package models;

import helpers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import strategies.*;

/**
 *
 * 
 * 
 */
public abstract class Tower implements DrawableEntity{
	final static int MAXTOWERLEVEL = 4;
	final static String DEFAULTSTRATEGY = "Closest";
	
	Point position;
	int slowTime;
	double damage;
	int rateOfFire;
	int range;

	int sellPrice;	
	int upCost;
	String name;
	int level;
	//boolean slow;
	double slowFactor;
	boolean damageOverTime;
	boolean areaOfAffect;
	Color tColor;
	Color shotColor;
	private IStrategy strategy;
	// inRangeC helps keep track of all the critters in the range of the tower and makes it easier 
	//for the findCrittersInRange method to be called by an observer.
	//ArrayList<Critter> inRangeC;
	ArrayList<Critter> potentialCrittersInRange;
	//private TDMap map;
	//current CritterShell being targeted
	//Critter targeted;
	//checks if the tower is enabled
	private boolean enabled;
	private boolean selected;
	
    /**
     *
     * @param n
     * @param p
     * @param crittersOnMap
     */
    public Tower(String n, Point p, ArrayList<Critter> crittersOnMap){
		position = p;
		name = n;
		level = 1;
		//inRangeC = new ArrayList<Critter>();
		this.potentialCrittersInRange = crittersOnMap;
		strategy = new Closest();
		//this.map = map;
		enabled = true;
		selected = false;
	}
	
    /**
     *
     * @return
     */
    public int getSellPrice(){	
		return sellPrice;
	}

    /**
     *
     * @return
     */
    public int getUpPrice(){
		return upCost;
	}

    /**
     *
     * @param strategy
     */
    public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	
    /**
     *
     * @return
     */
    public int getPosX(){	
		return position.getX();
	}
	
    /**
     *
     * @return
     */
    public int getPosY(){	
		return position.getY();
	}
	
    /**
     *
     * @return
     */
    public int getRange(){	
		return range;
	}
	
    /**
     *
     * @return
     */
    public String getName(){	
		return name;
	}
	
    /**
     *
     * @return
     */
    public boolean getEnabled(){	
		return enabled;
	}

    /**
     *
     * @param state
     */
    public void setEnabled(boolean state){
		enabled = state;
	}

    /**
     *
     * @return
     */
    public Color getColor(){
		return tColor;
	}

    /**
     *
     * @return
     */
    public boolean isSelected(){
		return selected;
	}

    /**
     *
     * @return
     */
    public IStrategy getStrategy(){
		return this.strategy;
	}

    /**
     *
     * @param s
     */
    public void setSelected(boolean s){
		selected = s;
	}

    /**
     *
     * @return
     */
    public static String getDefaultStrategy(){
		return DEFAULTSTRATEGY;
	}

    /**
     *
     * @param time
     */
    public void setSlowTime(int time){
		this.slowTime = time;
	}

    /**
     *
     * @return
     */
    public int getSlowTime(){
		return this.slowTime;
	}

    /**
     *
     * @param newColor
     */
    public void setColor(Color newColor){
		
		tColor = newColor;
	}

    /**
     *
     * @return
     */
    public Color getShotColor(){
		return shotColor;
	}

    /**
     *
     * @return
     */
    public static int getMaxTowerLevel(){
		return MAXTOWERLEVEL;
	}

    /**
     *
     * @param crittersOnMap
     */
    public void setCrittersOnMap(ArrayList<Critter> crittersOnMap){
		this.potentialCrittersInRange = crittersOnMap;
	}

    /**
     *
     * @return
     */
    public int getLevel(){
		return level;
	}
	
    /**
     *
     * @param g
     */
    public void updateAndDraw(Graphics g){	
		ArrayList<Critter> inRangeC = new ArrayList<Critter>();
		this.drawTower(g);
		inRangeC = this.findCrittersInRange(potentialCrittersInRange);
		Critter targetedCritter = this.selectTarget(this, inRangeC);
		if(targetedCritter != null){
			if(enabled){
				this.shootTarget(targetedCritter, g);
			}
		}
	}
	
    /**
     *
     * @param g
     */
    public void drawTower(Graphics g) {
		//System.out.println("Just tried to draw a critter at " + this._pixelPosition.toString());
		Artist_Swing.drawTower(this,g);
    }
	
    /**
     *
     * @param tf1
     * @param crittersInR
     * @return
     */
    protected Critter selectTarget(Tower tf1, ArrayList<Critter> crittersInR){
		Critter target = strategy.findTarget(tf1, crittersInR);
		return target;
	}

    /**
     *
     * @param a
     * @return
     */
    public double distanceToCritter(Critter a){
	    double deltaX = a.getPixelPosition().getX()-this.getPosX();
	    double deltaY = a.getPixelPosition().getY()-this.getPosY();
		//finds the distance between a creep and a tower.
		double critterDistance = Math.sqrt((deltaX)*(deltaX) + (deltaY)*(deltaY));
		
		return critterDistance;
	}
	
	//checking if a critter is in range of a tower
	private boolean inRange(Critter a){
		boolean result = true;
		//finds the distance between a creep and a tower.
		int critterDistance = (int) distanceToCritter(a);
		
		if(a.getSize()+this.getRange()<critterDistance){
			result = false;
		}
		
		return result;
	}
	
	//returns the critters that are in range of a tower

    /**
     *
     * @param a
     * @return
     */
    	public ArrayList<Critter> findCrittersInRange(ArrayList<Critter> a){
		
		ArrayList<Critter> crittersInRange = new ArrayList<Critter>();
		if(a != null){
			for(int i = 0; i<a.size();i++){
				if(a.get(i).isActive()){
					if(inRange(a.get(i))){
						crittersInRange.add(a.get(i));
						//this.notifyObservers();
					}
				}
			}
		}
		return crittersInRange;
		
	}
	
	//deals damage based on amount of damage of the tower

    /**
     *
     * @param target
     * @param g
     */
    	protected void shootTarget(Critter target, Graphics g){
		for(int i = 0; i < this.rateOfFire * GameClock.getInstance().deltaTime(); i++){
			target.damage(damage);
			Artist_Swing.drawShot(this, target, g);
		}
	} 
	//upgrade the towers values and level

    /**
     *
     */
    	public void upgradeTower(){
		if(level < MAXTOWERLEVEL){
			level = level + 1;
			upCost = upCost + 50;
			damage = damage + 0.2; 
			rateOfFire = rateOfFire + 1;
			sellPrice = sellPrice + 50;
			range = range + 1;
			if(this.slowTime != 0){
				this.slowTime = (this.slowTime + 20);
				this.slowFactor = this.slowFactor + 0.1;
			}
		}
	}

	/*
	//applies slow, damage over time, or area of affect depending on the tower
	public void applyAffect(Critter a){
		
		if(slow==true){
			
			System.out.println("Critter is slowed.");
		}
		
		if(damageOverTime==true){
			
			System.out.println("Critter is dealt damage over time.");
		}
			
		if(areaOfAffect==true){
				
			System.out.println("Critter is dealt area of affect damage.");
			
		}
	}*/

	public String toString(){
		String result = "";
		result += this.getName() + ", ";
		result += "Level = " +this.getLevel() + "/" + MAXTOWERLEVEL + ", ";
		result += "Upgrade cost = " + this.getUpPrice() +  ", ";
		result += "Sell price = " + this.getSellPrice() + ", ";
		result += "Strategy:";
		
		return result;
	}
	

}
