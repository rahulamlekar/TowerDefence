package helpers;
import javax.swing.JFrame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import entities.Critter;
import entities.TDMap;
import entities.Tower;

public class Artist_Swing extends JFrame{
	
	public static final int PIXELWIDTH=1280;
	public static final int PIXELHEIGHT=800;
	public static Artist_Swing artist = new Artist_Swing();
	//public GameController controller = new GameController();
	
	private void Artist_Swing(){
	}
	public static Artist_Swing getInstance(){
		return artist;
	}
	public static void drawEmptyCircle(Graphics g, Color c, int x, int y, int radius){
		g.setColor(c);
		g.drawOval(x-radius, y-radius, radius*2, radius*2);
	}
	public static void drawFilledCircle(Graphics g, Color c, int x, int y, int radius){
		g.setColor(c);
		g.drawOval(x-radius, y-radius, radius*2, radius*2);
		g.fillOval(x-radius, y-radius, radius*2, radius*2);
	}
	public static void drawFilledQuad(Graphics g, Color c, int x, int y, int width, int height)
	{
		g.setColor(c);
		g.drawRect(x,y, width, height);
    	g.fillRect(x,y, width, height);
	}
	public static void drawEmptyQuad(Graphics g, Color c, int x, int y, int width, int height){
		g.setColor(c);
		g.drawRect(x, y, width, height);
	}
	
	public static void drawMap(TDMap tdMap, Graphics g)
	{
		
		int mapWidth=tdMap.getGridWidth();
		int mapHeight=tdMap.getGridHeight();
		int scaledWidth=(int) PIXELWIDTH/mapWidth;
		int scaledHeight=(int) PIXELHEIGHT/mapHeight;
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
	public static void drawCritter(Critter crit, Graphics g){
		//g.setColor(crit.getColor());
		drawFilledQuad(g, crit.getColor(),crit.getPixelPosition().getX(), crit.getPixelPosition().getY(), crit.getSize(), crit.getSize());
	}
	/*
	 * draws the tower, and indicates its current level by Squares inside of it.
	 */
	public static void drawTower(Tower tow, Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1));
		int towerWidth = tow.getMapTowerIsOn().getTileWidth_pixel();
		int towerHeight = tow.getMapTowerIsOn().getTileHeight_pixel();
		
		drawFilledQuad(g, Color.gray, tow.getPosX(), tow.getPosY(), towerWidth, towerHeight);
		drawEmptyQuad(g, Color.black, tow.getPosX(), tow.getPosY(), towerWidth, towerHeight);
		drawFilledCircle(g, tow.getColor(), tow.getPosX() + towerWidth/2, tow.getPosY() + towerHeight/2, towerWidth/4);
		
		//for upgrades, we draw a circle (in white) around the main circle of the tower for each upgrade level!
		int spaceBetweenCircles = (int) (((double)towerWidth)/16); //16 since max tower level is 4. (so the circle doesn't go out of bounds)
		for(int i = 1; i < tow.getLevel(); i++){
			drawEmptyCircle(g, Color.white, tow.getPosX() + towerWidth/2, tow.getPosY() + towerHeight/2, towerWidth/4 + i*spaceBetweenCircles);
		}
	}
	public static void drawShot(Tower tow, Critter crit, Graphics g){
		//get tower color info,
		g.setColor(tow.getShotColor());
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(tow.getPosX() +tow.getMapTowerIsOn().getTileWidth_pixel()/2, tow.getPosY() + tow.getMapTowerIsOn().getTileHeight_pixel()/2, crit.getPixelPosition().getX() + crit.getSize()/2, crit.getPixelPosition().getY() + crit.getSize()/2);
	}
	

	
}
