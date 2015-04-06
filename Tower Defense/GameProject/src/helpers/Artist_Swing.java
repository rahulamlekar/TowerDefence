package helpers;
import javax.swing.JFrame;

import models.Critter;
import models.Point;
import models.TDMap;
import models.Tower;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *  This class relates to all the UI aspects of the game play.
 */
public class Artist_Swing extends Helper{
	
    /**
     *  The default pixel width for the screen.
     */
    public static final int PIXELWIDTH=1280;

    /**
     *  The default pixel height for the screen.
     */
    public static final int PIXELHEIGHT=800;

    /**
     *  The default height for the map shown on the screen.
     */
    public static final int GAMEPIXELHEIGHT = 700;
	private int gridWidth;
	private int gridHeight;
	
	//public GameController controller = new GameController();
	private static Artist_Swing artist = new Artist_Swing();
	
	private Artist_Swing(){
		gridWidth = TDMap.DEFAULTGRIDWIDTH;
		gridHeight = TDMap.DEFAULTGRIDHEIGHT;
	}
	
    /**
     *
     * @param width
     */
    public void setGridWidth(int width){
		this.gridWidth = width;
	}

    /**
     *
     * @param height
     */
    public void setGridHeight(int height){
		this.gridHeight = height;
	}

    /**
     *
     * @return
     */
    public static Artist_Swing getInstance(){
		return artist;
	}

    /**
     *
     * @param g
     * @param c
     * @param x
     * @param y
     * @param radius
     */
    public static void drawEmptyCircle(Graphics g, Color c, int x, int y, int radius){
		g.setColor(c);
		g.drawOval(x-radius, y-radius, radius*2, radius*2);
	}

    /**
     *
     * @param g
     * @param c
     * @param x
     * @param y
     * @param radius
     */
    public static void drawFilledCircle(Graphics g, Color c, int x, int y, int radius){
		g.setColor(c);
		g.drawOval(x-radius, y-radius, radius*2, radius*2);
		g.fillOval(x-radius, y-radius, radius*2, radius*2);
	}

    /**
     *
     * @param g
     * @param c
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void drawFilledQuad(Graphics g, Color c, int x, int y, int width, int height)
	{
		g.setColor(c);
		g.drawRect(x,y, width, height);
    	g.fillRect(x,y, width, height);
	}

    /**
     *
     * @param g
     * @param c
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void drawEmptyQuad(Graphics g, Color c, int x, int y, int width, int height){
		g.setColor(c);
		g.drawRect(x, y, width, height);
	}
	
    /**
     *  Draws the map in the background. All the other objects are drawn over it.
     * @param tdMap
     * @param g
     */
    public static void drawMap(TDMap tdMap, Graphics g)
	{
		
		int mapWidth=tdMap.getGridWidth();
		int mapHeight=tdMap.getGridHeight();
		int scaledWidth=(int) PIXELWIDTH/mapWidth;
		int scaledHeight=(int) (GAMEPIXELHEIGHT)/mapHeight;
		String back= tdMap.getBackdrop();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1));
		for(int i=0; i<mapWidth; i++)
		{
			for(int j=0; j<mapHeight; j++)
			{
				int tileType= tdMap.getType(i, j);
				if(tileType==TDMap.PATH){
					drawFilledQuad(g,new Color(102, 51, 0), i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
					drawEmptyQuad(g, Color.gray, i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
				}else{
					drawFilledQuad(g,Color.GREEN, i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
					drawEmptyQuad(g, Color.GRAY, i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
				}
			}
		}
	}
	

    /**
     *  Draws the critter and it's current health bar above it.
     * @param crit
     * @param g
     */
    	public static void drawCritter(Critter crit, Graphics g){
		int size = crit.getSize();
		int posX = crit.getPixelPosition().getX();
		int posY = crit.getPixelPosition().getY();
		int healthBarGap = 3;
		int healthBarHeight = 2;
		int bufferSize = 2;
		
		//drawing the space behind the critter.
		drawFilledQuad(g, new Color(102, 51, 0), posX - bufferSize, posY - bufferSize - healthBarGap - healthBarHeight,  size + 2*bufferSize,size + healthBarGap + healthBarHeight + 2*bufferSize);
		//Drawing the actual critter
		drawFilledQuad(g, crit.getColor(),posX, posY, size, size);
		//if the critter is burning, we draw an Orange around it
		if(crit.isBurning()){
			drawEmptyQuad(g, Color.orange, posX, posY, size, size);
		}
		
		//Healthbar:
		drawFilledQuad(g, Color.GREEN,posX, posY - healthBarGap - bufferSize,size, healthBarHeight);
		double currHP = crit.getHitPoints();
		double maxHP = crit.getMaxHitPoints();
		int redAmount = (int) (size*(1-currHP/maxHP));
		if(redAmount != 0){
			drawFilledQuad(g, Color.RED,posX, posY - healthBarGap - bufferSize,redAmount, healthBarHeight);
		}
		
	}

    /**
     *  Draws a Tower, and indicates its current level by Squares inside of it.
     * @param tow
     * @param g
     */
    
	public static void drawTower(Tower tow, Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1));

		int tileWidth = PIXELWIDTH/Artist_Swing.getInstance().gridWidth;
		int tileHeight = (GAMEPIXELHEIGHT)/Artist_Swing.getInstance().gridHeight;
		
		Color outlineColor;
		if(tow.isSelected()){
			outlineColor = Color.blue;
		}else{
			outlineColor = Color.black;
		}

		drawFilledQuad(g, Color.gray, tow.getPosX(), tow.getPosY(), tileWidth, tileHeight);
		drawEmptyQuad(g, outlineColor, tow.getPosX(), tow.getPosY(), tileWidth, tileHeight);
		drawFilledCircle(g, tow.getColor(), tow.getPosX() + tileWidth/2, tow.getPosY() + tileHeight/2, tileWidth/4);
		
		//for upgrades, we draw a circle (in white) around the main circle of the tower for each upgrade level!
		int spaceBetweenCircles = (int) (((double)tileWidth)/16); //16 since max tower level is 4. (so the circle doesn't go out of bounds)
		for(int i = 1; i < tow.getLevel(); i++){
			drawEmptyCircle(g, Color.white, tow.getPosX() + tileWidth/2, tow.getPosY() + tileHeight/2, tileWidth/4 + i*spaceBetweenCircles);
		}
	}
	

    /**
     *  Draws a shot from a tower to a critter.
     * @param tow
     * @param crit
     * @param g
     */
    	public static void drawShot(Tower tow, Critter crit, Graphics g){
		int tileWidth = PIXELWIDTH/Artist_Swing.getInstance().gridWidth;
		int tileHeight = (GAMEPIXELHEIGHT)/Artist_Swing.getInstance().gridHeight;
		//get tower color info,
		g.setColor(tow.getShotColor());
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(tow.getPosX() +tileWidth/2, tow.getPosY() + tileHeight/2, crit.getPixelPosition().getX() + crit.getSize()/2, crit.getPixelPosition().getY() + crit.getSize()/2);
	}
	
	

	
}
