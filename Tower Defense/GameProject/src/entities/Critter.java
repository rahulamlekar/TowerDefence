package entities;
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
	protected int speed;
	protected int size;
	protected double regen;
	protected double resistance;
	protected Color cColor;
	
	
	//intangible properties
	protected int reward;
	protected int level;
	protected double levelMultiplier;
	protected String name;
	
	//state properties
	protected Point _pixelPosition;
	protected boolean active;
	protected boolean reachedEnd;
	protected ArrayList<Point> pixelPathToFollow;
	protected int indexInPixelPath;
	 
	//MAP (gets the only instance of the map)
	protected TDMap map;
	
	 
	//constructor
	public Critter(int level, TDMap m){
		//set the level from input
		this.level = level;
		//set the default values for all critters
		levelMultiplier = 1 + level/MAXWAVENUMBER;
		reachedEnd = false; //none have reached end to start
		active = false; //none are active to start
		pixelPathToFollow = m.getPath_ListOfPixels();
		indexInPixelPath = 0; //all start at beginning of path
		size = 6; //this can be changed...
		map = m; //shouldn't be here!
		this._pixelPosition = new Point(-1,-1);
	}
	
	//getters and setters
	public Color getColor(){
		return cColor;
	}
	public Point getPixelPosition(){
		return _pixelPosition;
	}
	
	public int getSize(){
		return size;
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
	public boolean isActive(){
		return active;
	}
	public void setActive(boolean act){
		active = act;
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
	public void updateHealth(){
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
	public void updatePositionAndDraw(Graphics g){
		//if we haven't yet moved, 
		if(indexInPixelPath == 0){
			//place us on the map at the initial position.
			_pixelPosition = pixelPathToFollow.get(0);
		}
		//the next index is our current index + our speed*our clock
		indexInPixelPath += this.speed*GameClock.getInstance().deltaTime(); //synced with time
		
		//If we aren't going to pass the end, we move our critter.
		if(indexInPixelPath < pixelPathToFollow.size()){
			moveAndDrawCritter(pixelPathToFollow.get(indexInPixelPath),  g);
		}else{
			//we have reached the end
			reachedEnd = true; 
			//this critter is no longer active
			active = false;
			//notify our observers.
			this.notifyObservers();
		}
	}

	/*
	 * Moves the critter to a given position and draws it as it moves.
	 */
	public void moveAndDrawCritter(Point toPosition, Graphics g){
		//For debugging
		//System.out.println("Requested to move critter from " + this._pixelPosition.toString() + " to " + toPosition.toString());
		
		Point p1 = this._pixelPosition;
		Point p2 = toPosition;
		if(p1.equals(p2)){
			this.drawCritter(g);
		}else{
			int step = -1;
			int moveInX = Math.abs(p2.getX() - p1.getX());
			if(p2.getX() > p1.getX()){
				step = 1;
			}
			for(int i = 0; i < moveInX; i++){
				_pixelPosition.setX(_pixelPosition.getX() + step);
				this.drawCritter(g);
			}
		
			int moveInY = Math.abs(p2.getY() - p1.getY());
			if(p2.getY() > p1.getY()){
					step = 1;
			}
			for(int i = 0; i < moveInY; i++){
				_pixelPosition.setY(_pixelPosition.getY() + step);
				this.drawCritter(g);
			}
		}
		//set the pixel position to the new position
		this._pixelPosition = toPosition;
	}
	
	public void drawCritter(Graphics g) {
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
			//TODO: Critter has died
			//System.out.println("Critter has been killed. User will receive " + this.reward + " coins.\n");
		}
	}

	
	
	//ToString
	public String toString(){
		String result = "";
		result += "\nHP: " + currHitPoints + "/" + maxHitPoints + "\n";
		result += "Regen = " + this.regen + "\n";
		return result;
	}
	
}
