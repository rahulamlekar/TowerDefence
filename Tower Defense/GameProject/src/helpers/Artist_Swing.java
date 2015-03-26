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
	
	public static void drawQuad(Graphics g, Color c, int x, int y, int height, int width)
	{
		g.setColor(c);
		g.drawRect(x,y, width, height);
    	g.fillRect(x,y, width, height);
	}
	
	
	public static void drawMap(TDMap tdMap, Graphics g)
	{
		
		int mapWidth=tdMap.getWidth();
		int mapHeight=tdMap.getHeight();
		int scaledWidth=(int) PIXELWIDTH/mapWidth;
		int scaledHeight=(int) PIXELHEIGHT/mapHeight;
		String back= tdMap.getBackdrop();

		for(int i=0; i<mapWidth; i++)
		{
			for(int j=0; j<mapHeight; j++)
			{
				int tileType= tdMap.getType(i, j);
				if(tileType==1){
					drawQuad(g,new Color(102, 51, 0), i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
				}else{
					drawQuad(g,Color.GREEN, i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
				}
			}
		}
	}
	public static void drawCritter(Critter crit, Graphics g){
		//g.setColor(crit.getColor());
		drawQuad(g, crit.getColor(),crit.getPixelPosition().getX(), crit.getPixelPosition().getY(), crit.getSize(), crit.getSize());
	}
	public static void drawTower(Tower tow, Graphics g){
		drawQuad(g,tow.getColor(), tow.getPosX(), tow.getPosY(), tow.getSize(), tow.getSize());
	}
	public static void drawShot(Tower tow, Critter crit, Graphics g){
		//get tower color info,
		g.setColor(Color.BLACK);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(tow.getPosX() +tow.getSize()/2, tow.getPosY() + tow.getSize()/2, crit.getPixelPosition().getX(), crit.getPixelPosition().getY());
	}
	

	
}
