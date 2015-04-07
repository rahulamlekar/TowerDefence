package models;

/*
 * A point class. Simply has an x point, and a y point. Used for positioning
 */

/**
 *
 * 
 * 
 */

public class Point {
	private int x;
	private int y;
	//constructors

    /**
     *
     * @param x
     * @param y
     */
    	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	//getters and setters

    /**
     *
     * @return
     */
    	public int getX() {
		return x;
	}

    /**
     *
     * @param x
     */
    public void setX(int x) {
		this.x = x;
	}

    /**
     *
     * @return
     */
    public int getY() {
		return y;
	}

    /**
     *
     * @param y
     */
    public void setY(int y) {
		this.y = y;
	}
	//allows you to set both coords of a point at once.

    /**
     *
     * @param x
     * @param y
     */
    	public void setPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	//see if one point equals another

    /**
     *
     * @param P
     * @return
     */
	public boolean equals(Point P){
		boolean result = false;
		//two points are equal IFF x1 = x2 and y1 = y2
		if(this.x == P.x && this.y == P.y){
			result = true;
		}
		return result;
	}
	
	//tostring
	public String toString(){
		String result = "Position: (" + this.x + ", " + this.y + ")";
		
		return result;
	}
}
