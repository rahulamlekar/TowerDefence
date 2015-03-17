package src;

public class SampleCritter {
	
	private int ID;
	private int health;			
	private int Xpos;
	private int Ypos;
	private int speed;
	
	public SampleCritter(int ID, int health, int Xpos, int Ypos, int speed){
	this.ID=ID;
	this.health=health;
	this.Xpos=Xpos;
	this.Ypos=Ypos;
	this.speed=speed;	
	}
		
	// setters and getters
	
	public int getX(){
		return Xpos;
	}
	public void setX(int X){
		Xpos=X;
	}
	
	
	public int getY(){
		return Ypos;
	}
	public void setY(int Y){
		Ypos=Y;
	}
	
	public int getID(){
		return ID;
	}
	public void setID(int ID){
		this.ID=ID;
	}
	
	public int getSpeed(){
		return speed;
	}
		public void setSpeed(int speed){
			this.speed=speed;
	}
		
	public int getHealth(){
		return health;
	}
	public void setHealth(int health){
		this.health=health;
	}
}
