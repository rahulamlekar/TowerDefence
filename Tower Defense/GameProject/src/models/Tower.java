package models;

import helpers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import strategies.*;

public abstract class Tower implements DrawableEntity{
	final static int MAXTOWERLEVEL = 4;
	final static String DEFAULTSTRATEGY = "Closest";
	
	Point position;
	int slowTime;
	double damage;
	int rateOfFire;
	int range;
	static int buyCost;
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
	
	public int getSellPrice(){	
		return sellPrice;
	}
	public static int getBuyPrice(){	
		return buyCost;
	}
	public int getUpPrice(){
		return upCost;
	}
	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	public int getPosX(){	
		return position.getX();
	}
	public int getPosY(){	
		return position.getY();
	}
	public int getRange(){	
		return range;
	}
	public String getName(){	
		return name;
	}
	public boolean getEnabled(){	
		return enabled;
	}
	public void setEnabled(boolean state){
		enabled = state;
	}
	public Color getColor(){
		return tColor;
	}
	public boolean isSelected(){
		return selected;
	}
	public IStrategy getStrategy(){
		return this.strategy;
	}
	public void setSelected(boolean s){
		selected = s;
	}
	public static String getDefaultStrategy(){
		return DEFAULTSTRATEGY;
	}
	public void setSlowTime(int time){
		this.slowTime = time;
	}
	public int getSlowTime(){
		return this.slowTime;
	}
	public void setColor(Color newColor){
		
		tColor = newColor;
	}
	public Color getShotColor(){
		return shotColor;
	}
	public static int getMaxTowerLevel(){
		return MAXTOWERLEVEL;
	}
	public void setCrittersOnMap(ArrayList<Critter> crittersOnMap){
		this.potentialCrittersInRange = crittersOnMap;
	}
	public int getLevel(){
		return level;
	}
	public void updateAndDraw(Graphics g){	
		ArrayList<Critter> inRangeC = new ArrayList<Critter>();
		this.drawTower(g);
		inRangeC = this.findCrittersInRange(potentialCrittersInRange);
		Critter targetedCritter = this.selectTarget(this, inRangeC);
		if(targetedCritter != null){
			this.shootTarget(targetedCritter, g);
		}
	}
	
	public void drawTower(Graphics g) {
		//System.out.println("Just tried to draw a critter at " + this._pixelPosition.toString());
		Artist_Swing.drawTower(this,g);
    }
	
	protected Critter selectTarget(Tower tf1, ArrayList<Critter> crittersInR){
		Critter target = strategy.findTarget(tf1, crittersInR);
		return target;
	}
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
	protected void shootTarget(Critter target, Graphics g){
		if(enabled){
			for(int i = 0; i < this.rateOfFire * GameClock.getInstance().deltaTime(); i++){
				target.damage(damage);
				Artist_Swing.drawShot(this, target, g);
			}
		
		}
	} 
	//upgrade the towers values and level
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
