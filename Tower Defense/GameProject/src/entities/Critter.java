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
	 protected ArrayList<Point> shortestPath;
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
		shortestPath = m.getPointsOfShortestPath();
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
	
	/*Moves the critter by ONE PIXEL in the correct direction. Note that this requires that the path is 
	  created as a ONE dimensional line (which is the plan). The graphics will be different, but 
	  underneath the path will have an effective width of 1 pixel.*/
	public void takeAStep(){
		
	}
	public void traversePath(){
		
		String fromWhere = "";
		String toWhere = "";
		//First, get the first position in the path (where we start).
		Point firstPos = shortestPath.get(0);
		//if we are along the y axis, start by default from the left
		if(firstPos.getX() == 0){
			fromWhere = "left";
		}else if(firstPos.getY() == 0){ //if along the x axis, start from the top
			fromWhere = "top";
		}else if(firstPos.getX() == map.getWidth()-1){ //if on the other side parallel to y axis, start right
			fromWhere = "right";
		}else if(firstPos.getY() == map.getWidth()-1){ //if on bottom parallel to x axis, start bot
			fromWhere = "bot";
		}
		//our current position is our first position
		Point currPos = firstPos;
		
		//our default start pixel position is by default the location (in pixels) of our block
		Point startPixelPosition = map.getPosOfBlock_pixel(currPos.getX(), currPos.getY());
		//TODO: move to the start pixel Position from off the map
		
		//go through all of the blocks that we need to travel on.
		for(int i = 1; i < shortestPath.size(); i++){
			//the next block we want to travel to is the one at i (starts at 1)
			Point nextPos = shortestPath.get(i);
			
			//Figure out where we need to travel to (we already have where from)
			//if our x stays the same, we move vertically.
			if(nextPos.getX() == currPos.getX()){
				if(nextPos.getY() - currPos.getY() == 1){
					//we are moving downwards... So our toWhere will be bot
					toWhere = "bot";
				}else{
					//we are moving upwards.
					toWhere = "top";
				}
				//if our y stays the same, we move horizontally (either left or right)
			}else if(nextPos.getY() == currPos.getY()){
				if(nextPos.getX() - currPos.getX() ==1){
					toWhere = "right";
				}else{
					toWhere = "left";
				}
			}else{
				System.out.println("Error, point moves too much...");
			}
			//our default end position is the position of our next block.
			Point endPixelPosition = map.getPosOfBlock_pixel(nextPos.getX(), nextPos.getY());
			//now we can get the center of our current block
			Point pixelCenterOfBlock = new Point((int)(startPixelPosition.getX() + map.xBlock/2), (int)(startPixelPosition.getY() + map.yBlock/2));
			
			//the  start pixel position is now adjusted to be either top middle, right middle, left middle or bottom middle (depending on fromwhere)
			startPixelPosition = getPixelPosition(fromWhere, startPixelPosition, pixelCenterOfBlock);
			//the end pixel position is now adjusted to be either top middle, right middle, left middle, or bottom middle (depending on towhere)
			endPixelPosition = getPixelPosition(toWhere, endPixelPosition, pixelCenterOfBlock);
			
			//TODO: Move the critter
			//We can now move the critter from the start position to the center position
			//Then we can move the critter from the center position to the end position
			//Maybe method like: MoveCritter(critter, toPosition);
			
			//after being moved, we now set our fromWhere to be where we were going to, 
			fromWhere = toWhere;
			currPos = nextPos; //and now our current position is our next position.
			startPixelPosition = endPixelPosition;
			//LOOP.
		}
		//now that we are outside loop, it means we are at the beginning of our last block. 
		//we must travel to the middle of the last block, and then travel to the appropriate side.
		Point lastPos = shortestPath.get(shortestPath.size()-1);
		//if we are along the y axis, start by default from the left
		if(lastPos.getX() == 0){
			toWhere = "left";
		}else if(lastPos.getY() == 0){ //if along the x axis, start from the top
			toWhere = "top";
		}else if(lastPos.getX() == map.getWidth()-1){ //if on the other side parallel to y axis, start right
			toWhere = "right";
		}else if(lastPos.getY() == map.getWidth()-1){ //if on bottom parallel to x axis, start bot
			toWhere = "bot";
		}
		Point finalPixelPosition = map.getPosOfBlock_pixel(lastPos.getX(), lastPos.getY());
		Point finalPixelCenterOfBlock = new Point((int)(finalPixelPosition.getX() + map.xBlock/2), (int)(finalPixelPosition.getY() + map.yBlock/2));
		finalPixelPosition = getPixelPosition(toWhere, finalPixelPosition, finalPixelCenterOfBlock);
		
		//TODO: move the critter from its currentPosition to the finalPixelCenterOfBlock and then to the finalPixelPosition
		//Maybe method like: MoveCritter(critter, toPosition);
	}
	
	public void moveCritter(Point toPosition){
		int move;
		//move the critter from the first position to the next position
		if(toPosition.getX() == _pixelPosition.getX()){
			Artist.drawCritter(this);
			if(_pixelPosition.getY() < toPosition.getY()){
				move = 1;
			}else{
				move = -1;
			}
			for(int i = 0; i < Math.abs(_pixelPosition.getY() - toPosition.getY()); i++){
				_pixelPosition.setY(_pixelPosition.getY() + move);
				Artist.drawCritter(this);
			}
			
		}else if(toPosition.getY() == _pixelPosition.getY()){
			if(_pixelPosition.getX() < toPosition.getX()){
				move = 1;
			}
			else{
				move = -1;
			}
			for(int i = 0; i < Math.abs(_pixelPosition.getY() - toPosition.getY()); i++){
				_pixelPosition.setX(_pixelPosition.getX() + move);
				Artist.drawCritter(this);
			}
		}else{
			System.out.println("error with points pixels etc");
		}
		
		//set the pixel position to the new position
		this._pixelPosition = toPosition;
		
	}
	public Point getPixelPosition(String where, Point position, Point center){
		
		Point result = null;
		if(where == "left"){
			result = new Point(position.getX(),center.getY());
		}else if(where == "top"){
			result = new Point(center.getX() , position.getY());
		}else if(where == "bot"){
			result = new Point(center.getX(),position.getY() + map.yBlock);
		}else if(where == "right"){
			result = new Point(position.getX() + map.xBlock, center.getY());
		}
		
		return result;
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
