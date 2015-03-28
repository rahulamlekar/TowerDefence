package entities;

/*
 * A point class. Simply has an x point, and a y point. Used for positioning
 */
public class Point {
	private int x;
	private int y;
	//constructors
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	//getters and setters
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	//allows you to set both coords of a point at once.
	public void setPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	//see if one point equals another
	public boolean equals(Point P){
		if(this.x == P.x && this.y == P.y){
			return true;
		}
		return false;
	}
	public String toString(){
		String result = "Position: (" + this.x + ", " + this.y + ")";
		
		return result;
	}
}
