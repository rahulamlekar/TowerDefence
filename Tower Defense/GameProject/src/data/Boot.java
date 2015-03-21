package data;
import java.util.ArrayList;

import entities.*;
import static org.lwjgl.opengl.GL11.*;
import static helpers.Artist.*;
import helpers.Artist;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import entities.TDMap;

public class Boot {
	
	public Boot()
	{
		beginSession();
		
		TDMap tdMap= new TDMap("res/DIRTMAP1.TDMap");
		ArrayList<Point> pixelPathList = tdMap.getPath_ListOfPixels();
		ArrayList<Point> pathList = tdMap.getPointsOfShortestPath();
		//for(int i = 0; i < pathList.size(); i++){
			//System.out.print(pathList.get(i).toString() + " --> ");
		//}
		//for(int i = 0; i < pixelPathList.size(); i ++){
		//	System.out.println(pixelPathList.get(i).toString());
		//}
		Critter_Square c1 = new Critter_Square(1, tdMap);
		while(!Display.isCloseRequested())
		{
			drawMap(tdMap);
			
			
			c1.Update();

			Display.update();
			Display.sync(20);
		}
		
		//close the display instantly after this (otherwise there will be a delay)
		Display.destroy();
	}
	
	public static void main(String args[])
	{
		new Boot();
	}
}
