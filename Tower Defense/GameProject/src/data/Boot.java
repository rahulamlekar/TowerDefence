package data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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
		ArrayList<DrawableEntity> drawableEntities = new ArrayList<DrawableEntity>();
		int activeCrittersIndex = 0;
		
		//get the map and add it to the drawableEntities (to be updated)
		TDMap tdMap= new TDMap("res/DIRTMAP1.TDMap");
		drawableEntities.add(tdMap);
		
		//get the critters and add them to the drawableEntities (to be updated)
		ArrayList<Critter> crittersToBePlaced = CritterGenerator.getGeneratedCritterWave(1, tdMap);
		for(int i = 0; i < crittersToBePlaced.size(); i++){
			drawableEntities.add(crittersToBePlaced.get(i));
		}
		
		//The drawing loop.
		while(!Display.isCloseRequested())
		{
			
			if(activeCrittersIndex ==0){
				crittersToBePlaced.get(activeCrittersIndex).setActive(true);
				activeCrittersIndex +=1;
			}else if(activeCrittersIndex < crittersToBePlaced.size()){
				if(crittersToBePlaced.get(activeCrittersIndex-1).getPixelPosition().getX() > 50){
					crittersToBePlaced.get(activeCrittersIndex).setActive(true);
					activeCrittersIndex +=1;
				}
			}
			
			//"pauses" the game
			//if(crittersToBePlaced.get(0).getPixelPosition().getX()> 520){
			//	GameClock.getInstance().setDeltaTime(0);
			//}
			
			updateAndDrawAllEntities(drawableEntities);
			Display.update();
			Display.sync(20);
		}
		
		//close the display instantly after this (otherwise there will be a delay)
		Display.destroy();
	}
	
	public void updateAndDrawAllEntities(ArrayList<DrawableEntity> entities){
		//update and draw all drawableEntities.
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).updateAndDraw();
		}
	}
	public static void pause(){
		GameClock.getInstance().setDeltaTime(0);
	}

}
