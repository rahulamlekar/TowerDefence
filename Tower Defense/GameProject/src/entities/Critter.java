package entities;
import helpers.Artist;

import java.util.ArrayList;
import java.util.LinkedList;

/*
 * Critter abstract class from which all critters extend. Has certain attributes and methods including 
 * taking a step, getting damaged, etc.
 */
public abstract class Critter {
	//Constants
	public static final int MAXWAVENUMBER = 50;
	//general attributes
	protected double levelMultiplier;
	//attributes of the critter
	 protected String name;
	 protected int reward;
	 protected double currHitPoints;
	 protected double maxHitPoints;
	 protected double regen;
	 protected int speed;
	 protected int level;
	 protected double resistance;
	 protected int hitboxRadius;
	 
	 protected Point _pixelPosition;
	 //protected Point lastPixelPosition;
	 //protected Point nextPixelPosition;
	 
	 protected boolean reachedEnd;
	 protected int strength;
	 protected ArrayList<Point> pixelPathToFollow;
	 protected int indexInPixelPath;
	 //this is an example map class from which we will use the (shell) functions.
	 protected TDMap map;
	 
	//constructor
	public Critter(int level, TDMap m){
		this.level = level;
		_pixelPosition = new Point(-1, -1);
		//lastPixelPosition = new Point(-1,-1);
		//nextPixelPosition = new Point(-1,-1);
		map = m;
		levelMultiplier = 1 + level/MAXWAVENUMBER;
		reachedEnd = false;
		pixelPathToFollow = m.getPath_ListOfPixels();
		indexInPixelPath = 0;
	}

	//Start of getters and setters
	public Point getPixelPosition(){
		return _pixelPosition;
	}
	
	public int getHitboxRadius(){
		return hitboxRadius;
	}
	public void setHitboxRadius(int hbr){
		hitboxRadius = hbr;
	}
	public double getHitPoints() {
		return currHitPoints;
	}
	public void setHitPoints(double hitPoints) {
		this.currHitPoints = hitPoints;
	}
	public double getRegen() {
		return regen;
	}
	public void setRegen(double regen) {
		this.regen = regen;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	//END OF Getters and Setters
	//so update call.... Stephen Poole. Basically, one second has passed. So I want to move my critter from 
	//one position to a certain other position........ HOW........ HMMM......
	public void Update(){
		if(indexInPixelPath == 0){
			_pixelPosition = pixelPathToFollow.get(0);
		}
		indexInPixelPath +=this.speed;
		moveCritter(pixelPathToFollow.get(indexInPixelPath));
	}
	

	
	public void moveCritter(Point toPosition){
		System.out.println("Requested to move critter from " + this._pixelPosition.toString() + " to " + toPosition.toString());
		
		Point p1 = this._pixelPosition;
		Point p2 = toPosition;
		int step = -1;
		int moveInX = Math.abs(p2.getX() - p1.getX());
		if(p2.getX() > p1.getX()){
			step = 1;
		}
		for(int i = 0; i < moveInX; i++){
			_pixelPosition.setX(_pixelPosition.getX() + step);
			Artist.drawCritter(this);
		}
		
		int moveInY = Math.abs(p2.getY() - p1.getY());
		if(p2.getY() > p1.getY()){
			step = 1;
		}
		for(int i = 0; i < moveInY; i++){
			_pixelPosition.setY(_pixelPosition.getY() + step);
			Artist.drawCritter(this); //TODO: Fix this. Should be time dependent. 
		}
		
		//set the pixel position to the new position
		this._pixelPosition = toPosition;
		
	}
	
	
	//updates the critter health.
	public void updateCritterHealth(){
		//simply update the hitpoints. This should be called every update instance.
		//if our regen will not push us over our limit, simply regen
		if(this.currHitPoints + this.regen < this.maxHitPoints){
			this.currHitPoints = this.currHitPoints + this.regen;	
		}else{ //otherwise just set us to the max regen value.
			this.currHitPoints = this.maxHitPoints;
		}
		
	}
	//Damages the critter for a certain amount (Will likely be removed in final version)
	public void damage(double dam){
		if(this.currHitPoints - dam > 0){
			this.currHitPoints -= dam;
		}else{
			this.currHitPoints = 0;
			System.out.println("Critter has been killed. User will receive " + this.reward + " coins.\n");
		}
	}
	//ToString
	public String toString(){
		String result = "";
		result = name + "\n";// + currPixelPosition.toString();
		result += "\nHP: " + currHitPoints + "/" + maxHitPoints + "\n";
		result += "Regen = " + this.regen + "\n";
		return result;
	}
}
