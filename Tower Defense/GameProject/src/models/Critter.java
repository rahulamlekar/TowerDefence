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

/**
 *
 * 
 */

public abstract class Critter extends Subject implements DrawableEntity {
	//Constants

    /**
     *
     */
    	public static final int MAXCRITTERLEVEL = 50;
    	public static final int MAXSPEED = 15;
    	public static final String CRITTERMESSAGE = "Below is a description of each of the colored critters.\n\n" + "Yellow:\t\t\tBoss Critter. Very hard to kill\n\n" + "White:\t\t\tFast but weak\n\n" + "Red:\t\t\tSlightly below average\n\n" + "Pink:\t\t\tStrong but slow\n\n" + "Orange:\t\t\tCompletely resistant to fire and slow\n\n" + "Cyan:\t\t\tAverage Critter\n\n";  
	//attributes of the critter
	//tangible properties of critter

    /**
     *
     */
    	protected double currHitPoints;

    /**
     *
     */
    protected double maxHitPoints;

    /**
     *
     */
    protected double speed;

    /**
     *
     */
    protected int size;

    /**
     *
     */
    protected double regen;

    /**
     *
     */
    protected double resistance;

    /**
     *
     */
    protected Color cColor;
	
	//intangible properties

    /**
     *
     */
    	protected int reward;

    /**
     *
     */
    protected int level;

    /**
     *
     */
    //protected double levelMultiplier;

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected double slowFactor;

    /**
     *
     */
    protected int slowTime;

    /**
     *
     */
    protected int beenSlowedFor;
	private double damageOverTimeVal;
	private int dotTime;
	private int beenDOTFor;
	private boolean burning;
	
	//state properties

    /**
     *
     */
    	protected Point _pixelPosition;

    /**
     *
     */
    protected boolean active;

    /**
     *
     */
    protected boolean alive;

    /**
     *
     */
    protected boolean reachedEnd;

    /**
     *
     */
    protected ArrayList<Point> pixelPathToFollow;
	//protected ArrayList<Point> newPixelPathToFollow;

    /**
     *
     */
    	protected double indexInPixelPath;

    /**
     *
     */
    protected int intIndexInPixelPath;

	
	 
	//constructor

    /**
     *
     * @param level
     * @param m
     */
    public Critter(int level, TDMap m){
    	this.level = level;
    	setInitialValues();
    	//set the level from input
		pixelPathToFollow = m.getPath_ListOfPixels();
		//sets the size to scale with the grid size (bigger blocks = bigger critters)
		size = Math.min(m.getTileWidth_pixel(), m.getTileHeight_pixel())/5; 
    }
    /*
     * sets the initial values of the critter attributes.
     */
	public void setInitialValues(){
		reachedEnd = false; //none have reached end to start
		active = false; //none are active to start
		alive = true; //they are all alive to start
		indexInPixelPath = 0; //all start at beginning of path
		this._pixelPosition = new Point(-1,-1);
		slowFactor = 0;
		slowTime = 0;
		beenSlowedFor = 0;
		damageOverTimeVal = 0;
		dotTime = 0;
		beenDOTFor = 0;
		burning =false;
	}
    protected double calculateLevelMultiplier(){
    	double i = 1 + 1*((double)(level-1))/((double)MAXCRITTERLEVEL);
    	
    	if(level > 20){
    		i = 1 + (level+1)*((double)(level-1))/((double)MAXCRITTERLEVEL);
    	}else if(level > 10){
    		i =1 + 2*(level+1)*((double)(level -1))/((double)MAXCRITTERLEVEL);
    	}else if(level > 5){
    		i =1 + (level+1)*((double)(level -1))/((double)MAXCRITTERLEVEL);
    	}
    	return i;
    }
	//getters and setters

    /**
     *
     * @return
     */
    	public double getIndexInPixelPath(){
		return this.indexInPixelPath;
	}

    /**
     *
     * @return
     */
    public ArrayList<Point> getListPixelPath(){
		return this.pixelPathToFollow;
	}
	/*public ArrayList<Point> getNewListPixelPath(){
		return this.newPixelPathToFollow;
	}*/

    /**
     *
     * @param slowFactor
     */
    
	public void setSlowFactor(double slowFactor){
		if(this.slowFactor < slowFactor){
			this.slowFactor = slowFactor;
		}
		beenSlowedFor = 0;
	}
	private void setDOTAmount(double dot) {
		if(this.damageOverTimeVal < dot){
			this.damageOverTimeVal = dot;
		}
		beenDOTFor = 0;
	}
	
    /**
     *
     * @return
     */
    public Color getColor(){
		return cColor;
	}
	
    /**
     *
     * @return
     */
    public Point getPixelPosition(){
		return _pixelPosition;
	}

    /**
     *
     * @return
     */
    public boolean hasReachedEnd(){
		return reachedEnd;
	}

    /**
     *
     * @return
     */
    public boolean isAlive(){
		return alive;
	}

    /**
     *
     * @return
     */
    public boolean isBurning() {
		return burning;
	}

    /**
     *
     * @return
     */
    public int getSize(){
		return size;
	}

    /**
     *
     * @return
     */
    public int getLoot(){
		return reward;
	}

    /**
     *
     * @param size
     */
    public void setHitboxRadius(int size){
		this.size = size;
	}

    /**
     *
     * @return
     */
    public double getHitPoints() {
		return currHitPoints;
	}

    /**
     *
     * @param hitPoints
     */
    public void setHitPoints(double hitPoints) {
		this.currHitPoints = hitPoints;
	}

    /**
     *
     * @return
     */
    public double getMaxHitPoints(){
		return maxHitPoints;
	}

    /**
     *
     * @return
     */
    public double getRegen() {
		return regen;
	}

    /**
     *
     * @param regen
     */
    public void setRegen(double regen) {
		this.regen = regen;
	}

    /**
     *
     * @return
     */
    public double getRawSpeed() {
		return speed;
	}

    /**
     *
     * @param speed
     */
    public void setRawSpeed(int speed) {
		this.speed = speed;
	}

    /**
     *
     * @return
     */
    public int getLevel() {
		return level;
	}

    /**
     *
     * @param level
     */
    public void setLevel(int level) {
		this.level = level;
	}

    /**
     *
     * @return
     */
    public boolean isActive(){
		return active;
	}

    /**
     *
     * @param act
     */
    public void setActive(boolean act){
		active = act;
	}

    /**
     *
     * @return
     */
    public double getSpeed(){
		return speed;
	}
	//END OF Getters and Setters
	/*
	 * @see entities.DrawableEntity#updateAndDraw()
	 * This must have all properties of the critter that change with time
	 * These properties are its position and its health.
	 */

    /**
     *
     * @param g
     */
    
	public void updateAndDraw(Graphics g){
		if(this.isActive()){
			//do slowing
			if(beenSlowedFor < this.slowTime){
				beenSlowedFor +=1*GameClock.getInstance().deltaTime();
			}else{
				slowFactor = 0;
			}
			//do damages over time
			if(beenDOTFor < this.dotTime){
				beenDOTFor +=1*GameClock.getInstance().deltaTime();
				burning = true;
			}else{
				damageOverTimeVal = 0;
				burning = false;
			}
			
			updateHealth();
			updatePositionAndDraw(g);
		}
	}
	/*
	 * updates the health of the critter (called on every "tick" of the clock)
	 */
	private void updateHealth(){
		int timePassed = GameClock.getInstance().deltaTime();
		double dotPerTime = damageOverTimeVal*timePassed;
		double dotTaken = dotPerTime*(1.0-resistance);
		//simply update the hitpoints. This should be called every update instance.
		if(this.currHitPoints + this.regen*timePassed - dotTaken <=0){
			this.currHitPoints = 0;
			this.active = false;
			this.alive = false;
			this.notifyObs();
			//if our regen will not push us over our limit, simply regen
		}else if(this.currHitPoints + this.regen*timePassed - dotTaken < this.maxHitPoints){
			this.currHitPoints = this.currHitPoints + this.regen*timePassed - dotTaken;	
		}else{
			//otherwise just set us to the max regen value.
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
		
		//the next index is our current index + our speed*our clock
		indexInPixelPath += (1.0-(1.0-resistance)*slowFactor)*this.speed*GameClock.getInstance().deltaTime(); //synced with time
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
		if(intIndexInPixelPath == index){
			this.drawCritter(g);
		}else{
			while(intIndexInPixelPath<index){
				intIndexInPixelPath +=1;
				this._pixelPosition.setPoint(this.pixelPathToFollow.get(intIndexInPixelPath).getX(), this.pixelPathToFollow.get(intIndexInPixelPath).getY());
				this.drawCritter(g);
			}
		}
	}
	
	private void drawCritter(Graphics g) {
		//System.out.println("Just tried to draw a critter at " + this._pixelPosition.toString());
		Artist_Swing.drawCritter(this,g);
    }
	
	//Damages the critter for a certain amount (Will likely be removed in final version)

    /**
     *
     * @param dam
     */
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

    /**
     *
     * @param sFactor
     * @param sTime
     */
    public void slowCritter(double sFactor, int sTime){
		this.setSlowFactor(sFactor);
		this.slowTime = sTime;
	}

    /**
     *
     * @param dot
     * @param damageOverTimeLength
     */
    public void damageOverTimeCritter(double dot, int damageOverTimeLength) {
		this.setDOTAmount(dot);
		this.dotTime = damageOverTimeLength;
		
	}
	
	//ToString
	public String toString(){
		String result = "";
		result += "\nHP: " + currHitPoints + "/" + maxHitPoints + "\n";
		result += "Regen = " + this.regen + "\n";
		return result;
	}




	
}
