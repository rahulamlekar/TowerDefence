package models;
import helpers.Artist_Swing;
import helpers.GameClock;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/*
 * Critter abstract class from which all critters extend. Has certain attributes and methods including 
 * taking a step, getting damaged, etc.
 */
public abstract class Critter extends Subject implements DrawableEntity {
	//Constants
	public static final int MAXWAVENUMBER = 50;
	//attributes of the critter
	//tangible properties of critter
	protected double currHitPoints;
	protected double maxHitPoints;
	protected double speed;
	protected int size;
	protected double regen;
	protected double resistance;
	protected Color cColor;
	
	//intangible properties
	protected int reward;
	protected int level;
	protected double levelMultiplier;
	protected String name;
	protected double slowFactor;
	protected int slowTime;
	protected int beenSlowedFor;
	
	//state properties
	protected Point _pixelPosition;
	protected boolean active;
	protected boolean alive;
	protected boolean reachedEnd;
	protected ArrayList<Point> pixelPathToFollow;
	//protected ArrayList<Point> newPixelPathToFollow;
	protected double indexInPixelPath;
	protected int intIndexInPixelPath;
	
	 
	//constructor
	public Critter(int level, TDMap m){
		//set the level from input
		this.level = level;
		//set the default values for all critters
		levelMultiplier = 1 + 5*((double)(level -1))/((double)MAXWAVENUMBER);
		reachedEnd = false; //none have reached end to start
		active = false; //none are active to start
		alive = true;
		pixelPathToFollow = m.getPath_ListOfPixels();
		//newPixelPathToFollow = m.getPath_ListOfPixels();
		for(Point p : pixelPathToFollow){
			System.out.println(p.toString());
		}
		indexInPixelPath = 0; //all start at beginning of path
		size = 6; //this can be changed...
		this._pixelPosition = new Point(-1,-1);
		slowFactor = 0;
		slowTime = 0;
		beenSlowedFor = 0;
	}
	
	//getters and setters
	public double getIndexInPixelPath(){
		return this.indexInPixelPath;
	}
	public ArrayList<Point> getListPixelPath(){
		return this.pixelPathToFollow;
	}
	/*public ArrayList<Point> getNewListPixelPath(){
		return this.newPixelPathToFollow;
	}*/
	public void setSlowFactor(double slowFactor){
		if(this.slowFactor < slowFactor){
			this.slowFactor = slowFactor;
		}
		beenSlowedFor = 0;
	}
	public Color getColor(){
		return cColor;
	}
	public Point getPixelPosition(){
		return _pixelPosition;
	}
	public boolean hasReachedEnd(){
		return reachedEnd;
	}
	public boolean isAlive(){
		return alive;
	}
	public int getSize(){
		return size;
	}
	public int getLoot(){
		return reward;
	}
	public void setHitboxRadius(int size){
		this.size = size;
	}
	public double getHitPoints() {
		return currHitPoints;
	}
	public void setHitPoints(double hitPoints) {
		this.currHitPoints = hitPoints;
	}
	public double getMaxHitPoints(){
		return maxHitPoints;
	}
	public double getRegen() {
		return regen;
	}
	public void setRegen(double regen) {
		this.regen = regen;
	}
	public double getRawSpeed() {
		return speed;
	}
	public void setRawSpeed(int speed) {
		this.speed = speed;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isActive(){
		return active;
	}
	public void setActive(boolean act){
		active = act;
	}
	public double getSpeed(){
		return speed;
	}
	//END OF Getters and Setters
	/*
	 * @see entities.DrawableEntity#updateAndDraw()
	 * This must have all properties of the critter that change with time
	 * These properties are its position and its health.
	 */
	public void updateAndDraw(Graphics g){
		if(this.isActive()){
			updateHealth();
			updatePositionAndDraw(g);
		}
	}
	/*
	 * updates the health of the critter (called on every "tick" of the clock)
	 */
	private void updateHealth(){
		//simply update the hitpoints. This should be called every update instance.
		//if our regen will not push us over our limit, simply regen
		if(this.currHitPoints + this.regen < this.maxHitPoints){
			this.currHitPoints = this.currHitPoints + this.regen;	
		}else{ //otherwise just set us to the max regen value.
			this.currHitPoints = this.maxHitPoints;
		}
		
	}
	/*
	 * updates the position (and draws it), called on every tick of clock
	 */
	private void updatePositionAndDraw(Graphics g){
		//if we haven't yet moved, 
		if(indexInPixelPath == 0){
			//place us on the map at the initial position.
			_pixelPosition = pixelPathToFollow.get(0);
		}

		if(beenSlowedFor < this.slowTime){
			beenSlowedFor +=1;
		}else{
			slowFactor = 0;
		}
		//the next index is our current index + our speed*our clock
		indexInPixelPath += (1.0-slowFactor)*this.speed*GameClock.getInstance().deltaTime(); //synced with time
		int indexToMoveTo = (int) indexInPixelPath;
		//If we aren't going to pass the end, we move our critter.
		if(indexInPixelPath < pixelPathToFollow.size()){
			moveAndDrawCritter(indexToMoveTo, g);
		}else{
			//we have reached the end
			reachedEnd = true; 
			//this critter is no longer active
			active = false;
			//notify our observers.
			this.notifyObs();
		}
	}
	/*
	 * Moves the critter to a given position and draws it as it moves.
	 */
	private void moveAndDrawCritter(int index, Graphics g){
		while(intIndexInPixelPath<index){
			intIndexInPixelPath +=1;
			this._pixelPosition.setPoint(this.pixelPathToFollow.get(intIndexInPixelPath).getX(), this.pixelPathToFollow.get(intIndexInPixelPath).getY());
			this.drawCritter(g);
		}
	}
	

	
	private void drawCritter(Graphics g) {
		//System.out.println("Just tried to draw a critter at " + this._pixelPosition.toString());
		Artist_Swing.drawCritter(this,g);
    }
	
	//Damages the critter for a certain amount (Will likely be removed in final version)
	public void damage(double dam){
		if(this.currHitPoints - dam > 0){
			this.currHitPoints -= dam;
		}else{
			this.currHitPoints = 0;
			this.active = false;
			this.alive = false;
			this.notifyObs();
		}
	}
	public void slowCritter(double sFactor, int sTime){
		this.setSlowFactor(sFactor);
		this.slowTime = sTime;
	}
	
	
	//ToString
	public String toString(){
		String result = "";
		result += "\nHP: " + currHitPoints + "/" + maxHitPoints + "\n";
		result += "Regen = " + this.regen + "\n";
		return result;
	}

	public int getIndexOfPosition(Point currPos) {
		// TODO Auto-generated method stub
		int result = 0;
		for(int i = 1; i < pixelPathToFollow.size(); i++){
			Point p = pixelPathToFollow.get(i);
			if(p.getX() == currPos.getX() && p.getY() == currPos.getY()){
				result = i; 
				break;
			}
		}
		return result;
	}
	
}
