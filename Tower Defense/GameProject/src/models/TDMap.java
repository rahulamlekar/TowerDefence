package models;


import helpers.Artist_Swing;

import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yash Gupta
 */
public class TDMap implements DrawableEntity{
	//final variables
	public static final int MINWIDTH = 20, MAXWIDTH = 80, MINHEIGHT = 13, MAXHEIGHT = 50;
	public static final int DEFAULTGRIDWIDTH = 40;
	public static final int DEFAULTGRIDHEIGHT = 24;
    public static final int TOWER= 4;
    public static final int PATH= 2;
    private final int PIXELWIDTH = Artist_Swing.PIXELWIDTH;
    private final int PIXELHEIGHT = Artist_Swing.GAMEPIXELHEIGHT;
    
    private int grid[][];
    private MapTile gridTile[][];
    
    // The grid will be ALWAYS initialized and used as a width by height, that
    // will be implemented with graphics as horizontal by vertical blocks, that
    // go from top-left to bottom right. ALSO, it is ROWxCOLUMN!!
    private int gridWidth, gridHeight;

    // width will range from 13 to 50 and height will range from 20 to 80
    private String backdrop;
    private int start1, start2, end1, end2;
    private boolean isMapValid;

    private LinkedList<Integer> shortestPath;
    public int tileWidth_Pixel;
	public int tileHeight_Pixel;
	private ArrayList<IObserverTDMap> observers = new ArrayList<IObserverTDMap>();
	
    // Constructors
    public TDMap()
    {
        gridWidth= DEFAULTGRIDWIDTH;
        gridHeight= DEFAULTGRIDHEIGHT;
        initializeGrid();
       
        
        backdrop= "Generic";
        tileWidth_Pixel = PIXELWIDTH/gridWidth;
        tileHeight_Pixel = PIXELHEIGHT/gridHeight;
        //this.isMap();
    }
    


	public TDMap(int l, int h, String back)
    {
        if(gridWidth>=MINWIDTH&&gridWidth<=MAXWIDTH)
            gridWidth= l;
        else
            gridWidth=DEFAULTGRIDWIDTH;
        if(gridHeight>=MINHEIGHT&&gridHeight<=MAXHEIGHT)
            gridHeight= h;
        else
            gridHeight= DEFAULTGRIDHEIGHT;
        
        initializeGrid();
        backdrop= back;
        tileWidth_Pixel = PIXELWIDTH/gridWidth;
        tileHeight_Pixel = PIXELHEIGHT/gridHeight;
        //this.isMap();
    }
    
    public TDMap(String add)
    {
        if(!readMapFromFile(add))
        {
        	gridWidth = DEFAULTGRIDWIDTH;
        	gridHeight = DEFAULTGRIDHEIGHT;
        	int halfWay = gridWidth/2;
        	initializeGrid();
            for(int i = 0; i < halfWay; i++){
            	grid[i][3] = PATH;
            	gridTile[i][3].setTileValue(PATH);
            }
            for(int i = 3; i < 7; i++){
            	grid[halfWay][i] = PATH;
            	gridTile[halfWay][i].setTileValue(PATH);
            }
            for(int i = halfWay; i < gridWidth; i++){
            	grid[i][6] = PATH;
            	gridTile[i][6].setTileValue(PATH);
            }
            backdrop= "Generic";
            tileWidth_Pixel = PIXELWIDTH/gridWidth;
            tileHeight_Pixel = PIXELHEIGHT/gridHeight;
        }

    }
    private void initializeGrid() {
		// TODO Auto-generated method stub
    	 grid= new int[gridWidth][gridHeight];
         gridTile = new MapTile[gridWidth][gridHeight];
         for(int i = 0; i < gridWidth; i++){
        	 for(int j = 0; j < gridHeight; j++){
        		 gridTile[i][j] = new MapTile();
        	 }
         }
	}
    // This method initializes a new TDMap from a file.
    private boolean readMapFromFile(String add)
    {
        File f= new File(add);
        if(!f.exists())
            return false;
        else
        {
            FileInputStream fis;
            DataInputStream dis;
            try
            {
                fis = new FileInputStream(f);
                dis = new DataInputStream(fis);
                backdrop= dis.readUTF();
                gridWidth= dis.readInt();
                gridHeight= dis.readInt();
                initializeGrid();
                for(int i=0; i< gridWidth; i++){
                    for(int j=0; j< gridHeight; j++){
                    	int nextReadInt = dis.readInt();
                        grid[i][j]= nextReadInt;
                        gridTile[i][j].setTileValue(nextReadInt);
                    }
                }
                start1= dis.readInt();
                start2= dis.readInt();
                end1= dis.readInt();
                end2= dis.readInt();
                dis.close();
                fis.close();
                System.out.println("Map is valid?: " + isMap());
            }
            catch(IOException e)
            {
                return false;
            }
            return true;
        }
    }
    
    // This method instantiates the current TDMap to a file.
    public boolean writeMaptoFile(String add)
    {
        File f= new File(add);
        FileOutputStream fos;
        DataOutputStream dos;
        try
        {
            fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);
            dos.writeUTF(backdrop);
            dos.writeInt(gridWidth);
            dos.writeInt(gridHeight);
            for(int i=0; i< gridWidth; i++){
                for(int j=0; j< gridHeight; j++){
                    dos.writeInt(grid[i][j]); //TODO: OR dos.writeInt(gridTile[i][j].getTileValue())
                }
            }
            dos.writeInt(start1);
            dos.writeInt(start2);
            dos.writeInt(end1);
            dos.writeInt(end2);
            dos.close();
            fos.close();
        }
        catch(IOException e)
        {
            return false;
        }
        return true;
    }

    
    // By convention, I will denote PATH cells to be 2.
    public void toggleGrid(int i, int j)
    {
        if(((i!=start1) && (j!=start2)) || ((i!=end1) && (j!=end2)))
    		if((i<gridWidth)&&(j<gridHeight))
    			if(grid[i][j]==PATH)
    			{
    				grid[i][j]= TOWER;
    				gridTile[i][j].setTileValue(TOWER);
    			}
    			else
    			{
    				grid[i][j]= PATH;
    				gridTile[i][j].setTileValue(TOWER);
    			}
        TDMapUpdated();
    }
    // By convention, I will denote background/TOWER cells to be 4.

    
	public void reinitialize(int gridWidth, int gridHeight, String backdrop) {
			this.gridWidth= gridWidth;
			this.gridHeight= gridHeight;
			this.backdrop= backdrop;
			refresh();
	}
    // By convention, I will denote PATH cells to be 2.
    public void setAsPath(int i, int j)
    {
        if((i<gridWidth)&&(j<gridHeight)){
            grid[i][j]= PATH;
            gridTile[i][j].setTileValue(PATH);
        }
    }
    
    // By convention, I will denote background/TOWER cells to be 4.
    public void refresh()
    {
        grid = new int[gridWidth][gridHeight];
        gridTile = new MapTile[gridWidth][gridHeight];
    	for(int i=0; i< gridWidth; i++){
            for(int j=0; j< gridHeight; j++)
            {
            	grid[i][j]= TOWER;
            	gridTile[i][j]= new MapTile();
            	gridTile[i][j].setTileValue(TOWER);
            }
        }
        end1= -1;
    	end2= -1;
    	start1= -1;
    	start2= -1;
        tileWidth_Pixel = PIXELWIDTH/gridWidth;
        tileHeight_Pixel = PIXELHEIGHT/gridHeight;
    	TDMapReinitialized();
    }
    
    public void setStart(int i, int j)
    {
        start1= i;
        start2= j;
        setAsPath(i,j);
    }
    
    public void setEnd(int i, int j)
    {
        end1= i;
        end2= j;
        setAsPath(i,j);
    }
    
    
    // This method will return true if the Map is connected, and false
    // otherwise.
    // The way it is implemented is by applying a BREADTH-FIRST search algorithm
    // from the starting cell and then checking if the ending cell has been
    // explored or not. If the ending cell has been explored, then the PATH is
    // valid. This BFS also explores the shortest path from the End Cell to the
    // Start Cell to get rid of Loops, and the Critters optimize their attack.
    // This will be stored in shortestPath, as a LinkedList.
    // This method also initializes the boolean isMapValid to a T/F value.
    public boolean isMap()
    {
        LinkedList<Integer> explored= new LinkedList<>();
        LinkedList<Integer> frontier= new LinkedList<>();
        int parent[]= new int [(gridWidth*gridHeight)];
        frontier.addFirst(key(start1,start2));
        int t;
        while(!frontier.isEmpty())
        {
            t= frontier.removeFirst();
            explored.add(t);
            int i= arckeyi(t);
            int j= arckeyj(t);
            if((i-1)>-1) //TODO: convert to gridTile.
                if(grid[i-1][j]==PATH)
                    if(!explored.contains(key(i-1,j)))
                    {
                        frontier.addLast(key(i-1,j));
                        parent[key(i-1,j)]=t;
                    }
            if((i+1)<gridHeight)
                if(grid[i+1][j]==PATH)
                    if(!explored.contains(key(i+1,j)))
                    {
                        frontier.addLast(key(i+1,j));
                        parent[key(i+1,j)]=t;
                    }
            if((j-1)>-1)
                if(grid[i][j-1]==PATH)
                    if(!explored.contains(key(i,j-1)))
                    {
                        frontier.addLast(key(i,j-1));
                        parent[key(i,j-1)]=t;
                    }
            if((j+1)<gridHeight)
                if(grid[i][j+1]==PATH)
                    if(!explored.contains(key(i,j+1)))
                    {
                        frontier.add(key(i,j+1));
                        parent[key(i,j+1)]=t;
                    }
        }
        t= key(end1,end2);
        isMapValid= explored.contains(t);
        if(isMapValid)
        {
            shortestPath= new LinkedList<>();
            while(t!=key(start1,start2))
            {
                shortestPath.addFirst(t);
                t= parent[t];
            }
            shortestPath.addFirst(t);
        }
        return isMapValid;
    }
    
    // These are miscellaneous methods that assign a unique key value to each
    // individual cell in the grid and allow conversions between them.
    public int key(int i, int j)
    {
        return (gridWidth*j+i+1);
    }
    public int arckeyi(int k)
    {
        return ((k-1)%gridWidth);
    }
    public int arckeyj(int k)
    {
        return ((k-1)/gridWidth);
    }
    
    // This method provides an easy way to print out the grid to display the
    // map. It also prints out the shortest path the critters will take to move
    // from the Start cell to the End Cell.
    public void print()
    {
        System.out.println("Grid Size is "+gridWidth+" in horizontal width by "+gridHeight+" in vertical height:");
        for(int j=-2; j<gridWidth; j++)
            System.out.print("-");
        for(int i=0; i<gridHeight; i++)
        {
            System.out.print("\n|");
            for(int j=0; j<gridWidth; j++)
                if(grid[j][i]==TOWER)
                    System.out.print(" ");
                else if(grid[j][i]==PATH)
                    System.out.print("O");
            System.out.print("|");
        }
        System.out.println();
        for(int j=-2; j<gridWidth; j++)
            System.out.print("-");
        if(isMapValid)
            System.out.print("\nShortest path from Start to End is: ");
        for(Integer shortestPath1 : shortestPath) {
            System.out.print("(" + arckeyi(shortestPath1) + "," + arckeyj(shortestPath1) + ")\t");
        }
        System.out.println();
    }
    
    public int getTileWidth_pixel(){
    	return this.tileWidth_Pixel;
    }
    public int getTileHeight_pixel(){
    	return this.tileHeight_Pixel;
    }
    public int getPixelWidth(){
    	return PIXELWIDTH;
    }
    public int getPixelHeight(){
    	return PIXELHEIGHT;
    }
    public int getGridWidth()
    {
    	return gridWidth;
    }
    public int getGridHeight()
    {
    	return gridHeight;
    }
    public int getType(int x, int y)
    {
    	int type= grid[x][y];
    	return type;
    }
    public MapTile getTile(int x, int y){
    	MapTile tile = gridTile[x][y];
    	return tile;
    }
    public String getBackdrop()
    {
    	return backdrop;
    }
	public ArrayList<Point> getPointsOfShortestPath(){
		ArrayList<Point> pointsShortestPath = new ArrayList<Point>();
		if(shortestPath == null){
			int halfWay = this.gridWidth/2;
            for(int i = 0; i < halfWay; i++){
            	pointsShortestPath.add(new Point(i,3));
            }
            for(int i = 3; i < 7; i++){
            	pointsShortestPath.add(new Point(halfWay,i));
            }
            for(int i = halfWay + 1; i < gridWidth; i++){
            	pointsShortestPath.add(new Point(i,6));
            }
		}else{
			for(int i = 0; i < this.shortestPath.size(); i++){
				pointsShortestPath.add(new Point(arckeyi(shortestPath.get(i)), arckeyj(shortestPath.get(i))));
			}
		}
		return pointsShortestPath;
	}
	public Point getPosOfBlock_pixel(int x, int y){
		Point result = new Point((int) Math.ceil((x*tileWidth_Pixel)),(int) Math.ceil(y*tileHeight_Pixel));
		return result;
	}
	
	public ArrayList<Point> getPath_ListOfPixels(){
		ArrayList<Point> pixelPathToTravel = new ArrayList<Point>();
		ArrayList<Point> pathToTravel = getPointsOfShortestPath();
		String fromWhere = "";
		String toWhere = "";
		//First, get the first position in the path (where we start).
		Point firstPos = pathToTravel.get(0);
		//if we are along the y axis, start by default from the left
		if(firstPos.getX() == 0){
			fromWhere = "left";
		}else if(firstPos.getY() == 0){ //if along the x axis, start from the top
			fromWhere = "top";
		}else if(firstPos.getX() == this.getGridWidth()-1){ //if on the other side parallel to y axis, start right
			fromWhere = "right";
		}else if(firstPos.getY() == this.getGridWidth()-1){ //if on bottom parallel to x axis, start bot
			fromWhere = "bot";
		}
		//our current position is our first position
		Point currPos = firstPos;
		
		//our default start pixel position is by default the location (in pixels) of our block
		Point startBlockTopLeftPixel = this.getPosOfBlock_pixel(currPos.getX(), currPos.getY());
		//TODO: move to the start pixel Position from off the map
		Point startPixelPosition = null;
		//go through all of the blocks that we need to travel on.
		for(int i = 1; i < pathToTravel.size(); i++){
			//the next block we want to travel to is the one at i (starts at 1)
			Point nextPos = pathToTravel.get(i);
			
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
			Point endBlockTopLeft = this.getPosOfBlock_pixel(nextPos.getX(), nextPos.getY());
			Point endPixelPosition;
			
			//now we can get the center of our current block
			Point pixelCenterOfBlock = new Point((int)(startBlockTopLeftPixel.getX() + this.tileWidth_Pixel/2), (int)(startBlockTopLeftPixel.getY() + this.tileHeight_Pixel/2));
			
			//the  start pixel position is now adjusted to be either top middle, right middle, left middle or bottom middle (depending on fromwhere)
			startPixelPosition = getPixelPositionH1(fromWhere, startBlockTopLeftPixel, pixelCenterOfBlock);
			
			//the end pixel position is now adjusted to be either top middle, right middle, left middle, or bottom middle (depending on towhere)
			endPixelPosition = getPixelPositionH2(toWhere, endBlockTopLeft, pixelCenterOfBlock );

			addPixelPoints(pixelPathToTravel, startPixelPosition, pixelCenterOfBlock);
			addPixelPoints(pixelPathToTravel, pixelCenterOfBlock, endPixelPosition);
			//We can now move the critter from the start position to the center position
			//Then we can move the critter from the center position to the end position
			//Maybe method like: MoveCritter(critter, toPosition);
			
			//after being moved, we now set our fromWhere to be where we were going to, 
			fromWhere = invertWhere(toWhere);
			currPos = nextPos; //and now our current position is our next position.
			startBlockTopLeftPixel = endBlockTopLeft;
			
			//LOOP.
		}
		//now that we are outside loop, it means we are at the beginning of our last block. 
		//we must travel to the middle of the last block, and then travel to the appropriate side.
		Point lastPos = pathToTravel.get(pathToTravel.size()-1);
		//if we are along the y axis, start by default from the left
		if(lastPos.getX() == 0){
			toWhere = "left";
		}else if(lastPos.getY() == 0){ //if along the x axis, start from the top
			toWhere = "top";
		}else if(lastPos.getX() == this.getGridWidth()-1){ //if on the other side parallel to y axis, start right
			toWhere = "right";
		}else if(lastPos.getY() == this.getGridWidth()-1){ //if on bottom parallel to x axis, start bot
			toWhere = "bot";
		}
		
		Point finalPixelPosition = this.getPosOfBlock_pixel(lastPos.getX(), lastPos.getY());
		Point finalPixelCenterOfBlock = new Point((int)(finalPixelPosition.getX() + this.tileWidth_Pixel/2), (int)(finalPixelPosition.getY() + this.tileHeight_Pixel/2));
		finalPixelPosition = getPixelPositionH1(toWhere, finalPixelPosition, finalPixelCenterOfBlock);
		startPixelPosition = getPixelPositionH1(fromWhere, startBlockTopLeftPixel, finalPixelCenterOfBlock);
		
		addPixelPoints(pixelPathToTravel, startPixelPosition, finalPixelCenterOfBlock);
		addPixelPoints(pixelPathToTravel, finalPixelCenterOfBlock, finalPixelPosition);
		return pixelPathToTravel;
	}
	/*
	 * a method that makes the method to find the critter path simpler.
	 * This method "inverts" the "where" variable. If we are going to the "left", 
	 * the invert("left") = "right", and etc.
	 */
	private String invertWhere(String where){
		String result = "";
		if(where.equals("left")){
			result = "right";
		}else if(where.equals("right")){
			result = "left";
		}else if(where.equals("top")){
			result = "bot";
		}else if(where.equals("bot")){
			result = "top";
		}else{
			System.out.println("issue with where");
		}
		return result;
	}
	private Point getPixelPositionH1(String where, Point position, Point center){
		
		Point result = null;
		if(where.equals("left")){
			result = new Point(position.getX(),center.getY());
		}else if(where.equals("top")){
			result = new Point(center.getX() , position.getY());
		}else if(where.equals("bot")){
			result = new Point(center.getX(),position.getY() + this.tileHeight_Pixel);
		}else if(where.equals("right")){
			result = new Point(position.getX() + this.tileWidth_Pixel, center.getY());
		}
		
		return result;
	}
	private Point getPixelPositionH2(String where, Point position, Point center){
		
		Point result = null;
		if(where.equals("left")){
			result = new Point(position.getX() + this.tileWidth_Pixel,center.getY());
		}else if(where.equals("top")){
			result = new Point(center.getX() , position.getY()+ this.tileHeight_Pixel);
		}else if(where.equals("bot")){
			result = new Point(center.getX(),position.getY());
		}else if(where.equals("right")){
			result = new Point(position.getX(), center.getY());
		}
		
		return result;
	}
	
	private void addPixelPoints(ArrayList<Point> listToAdd, Point p1, Point p2){
		//System.out.println("Requested move from " + p1.toString() + " to " + p2.toString());
		listToAdd.add(p1);
		if(p1.getX() == p2.getX()){
			int step = 1;
			if(p2.getY() < p1.getY()){
				step = -1;
			}
			for(int i = 0; i < Math.abs(p2.getY() - p1.getY()); i++){
				listToAdd.add(new Point(p1.getX(), p1.getY() + (i+1)*step));
			}
			
		}else if(p1.getY() == p2.getY()){
			int step = 1;
			if(p2.getX() < p1.getX()){
				step = -1;
			}
			for(int i = 0; i < Math.abs(p2.getX() - p1.getX()); i++){
				listToAdd.add(new Point(p1.getX() + (i+1)*step, p1.getY()));
			}
		}else{
			System.out.println("Error with path points not lining up");
		}
	}
	
	public void updateAndDraw(Graphics g){
		Artist_Swing.drawMap(this, g);
	}
	 public void addObserver(IObserverTDMap toAddObserver)
	    {
	        observers.add(toAddObserver);
	    }
	    public void removeObserver(IObserverTDMap toAddObserver)
	    {
	        observers.remove(toAddObserver);
	    }
	    private void TDMapUpdated()
	    {
	        for(IObserverTDMap tempObserver: observers)
	        {
	            tempObserver.TDMapUpdated();
	        }
	    }
	    private void TDMapReinitialized()
	    {
	        for(IObserverTDMap tempObserver: observers)
	        {
	            tempObserver.TDMapReinitialized();
	        }
	    }
}

