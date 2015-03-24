package data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import entities.*;
import static org.lwjgl.opengl.GL11.*;
import static helpers.Artist.*;
import helpers.Artist;
import helpers.GameClock;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import sun.misc.Queue;
import entities.TDMap;

public class Boot {
	
	public Boot()
	{
		Artist.beginSession();
		
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
		Critter_Circle c2 = new Critter_Circle(1, tdMap);
		Queue<Critter> allCritters = new Queue<Critter>();
		allCritters.enqueue(c1);
		allCritters.enqueue(c2);
		
		ArrayList<Critter> crittersOnMap = new ArrayList<Critter>();
		crittersOnMap.add(c1);
		
		//The drawing loop.
		while(!Display.isCloseRequested())
		{
			tdMap.updateAndDraw();
			//update all of my critters.
			for(int i = 0; i < crittersOnMap.size(); i++){
				crittersOnMap.get(i).updateAndDraw();
			}
			//once our first one has got somewhere...?
			if(crittersOnMap.get(crittersOnMap.size()-1).getPixelPosition().getX() > 10 && allCritters.isEmpty() == false){
				try {
					crittersOnMap.add(allCritters.dequeue());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//"pauses" the game
			if(crittersOnMap.get(crittersOnMap.size()-1).getPixelPosition().getX()> 500){
				GameClock.getInstance().setDeltaTime(0);
			}
			
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
