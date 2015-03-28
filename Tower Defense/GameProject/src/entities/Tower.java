package entities;

import helpers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import strategy.*;

public abstract class Tower extends Subject implements DrawableEntity{

	  Point position;
	  int size;
	  
	  double damage;
	  int rateOfFire;
	  int range;
	  int buyCost;
	  int sellPrice;
	  int upCost;
	  String name;
	  int level;
	  //boolean slow;
	  double slowFactor;
	  boolean damageOverTime;
	  boolean areaOfAffect;
	  Color tColor;
	  private IStrategy strategy;
	  // inRangeC helps keep track of all the critters in the range of the tower and makes it easier 
	  //for the findCrittersInRange method to be called by an observer.
	  //ArrayList<Critter> inRangeC;
	  ArrayList<Critter> crittersOnMap;
	  
	  //current CritterShell being targeted
	  //Critter targeted;
	  
	public Tower(String n, Point p, int size, ArrayList<Critter> crittersOnMap){
		position = p;
		name = n;
		level = 1;
		//inRangeC = new ArrayList<Critter>();
		this.size = size;
		this.crittersOnMap = crittersOnMap;
		strategy = new Closest();
	}
	public Color getColor(){
		return tColor;
	}
	public void setCrittersOnMap(ArrayList<Critter> crittersOnMap){
		this.crittersOnMap = crittersOnMap;
	}
	public int getSize(){
		return size;
	}
	
	public void updateAndDraw(Graphics g){	
		ArrayList<Critter> inRangeC = new ArrayList<Critter>();
		this.drawTower(g);
		inRangeC = this.findCrittersInRange(crittersOnMap);
		Critter targeted = selectTarget(this, inRangeC);
		if(targeted != null){
			this.shootTarget(targeted, g);
		}
	}
	
	public void drawTower(Graphics g) {
		//System.out.println("Just tried to draw a critter at " + this._pixelPosition.toString());
		Artist_Swing.drawTower(this,g);
    }
	
	public Critter selectTarget(Tower tf1, ArrayList<Critter> crittersInR){
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
	public boolean inRange(Critter a){
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
	
		for(int i = 0; i<a.size();i++){
			if(a.get(i).isActive()){
				if(inRange(a.get(i))){
					
					crittersInRange.add(a.get(i));
					//the inRangeC is kept up to date as critters enter the tower's range
					//inRangeC.add(a.get(i));
					this.notifyObservers();
				}
			}
		}
		
		return crittersInRange;
		
	}
	/*
	public ArrayList<Critter> getInRangeC() {
		return inRangeC;
	}*/
/*	
	public void setTarget(Critter c){
		
		if(c.getHitPoints()>0)
			targeted = c;
	}*/
	
	//deals damage based on amount of damage of the tower
	public void shootTarget(Critter target, Graphics g){
		for(int i = 0; i < this.rateOfFire * GameClock.getInstance().deltaTime(); i++){
		  target.damage(damage);
		  target.setSlowFactor(this.slowFactor);
		  Artist_Swing.drawShot(this, target, g);
		}
	} 
	//upgrade the towers values and level
	public void upgradeTower(){
		
		level = level + 1;
		upCost = upCost + 50;
		damage = damage + 1; 
		rateOfFire = rateOfFire + 1;
		sellPrice = sellPrice + 50;
		range = range + 1;
	}
	
	public int getSellPrice(){
		
		return sellPrice;
	}
	
	public int getBuyPrice(){
		
		return buyCost;
	}
	
	public int getUpPrice(){
		
		return upCost;
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
	

}
