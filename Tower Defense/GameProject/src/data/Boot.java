package data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JOptionPane;

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

public class Boot implements IObserver {
	private int lives;
	public Boot()
	{

		lives = 10;
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
			crittersToBePlaced.get(i).addObserver(this); //makes this an observer of critter
		}
		
		Tower tf1 = new IceBeamTower("tf1", tdMap.getPosOfBlock_pixel(5, 1), tdMap.xBlock, crittersToBePlaced);
		Tower tf2 = new LaserTower("tf2", tdMap.getPosOfBlock_pixel(25, 1), tdMap.xBlock, crittersToBePlaced);
		drawableEntities.add(tf1);
		drawableEntities.add(tf2);
		
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
			//don't breakpoint here please
			entities.get(i).updateAndDraw();
		}
	}
	
	public static void pause(){
		GameClock.getInstance().setDeltaTime(0);
	}
	
	@Override
	public void update() {
		//I know that something has changed about my subject.
		//if subject.reachedEnd =true, then change lives
		//if subject.died = true, give gold
		
		//check multiple things that could have changed. First, lives
		lives -=1;
		if(lives == 0){
			Boot.pause();
			JOptionPane.showMessageDialog(null, "Game Over", "InfoBox: Information", JOptionPane.INFORMATION_MESSAGE);
		}
		//System.out.println("Lives = " + this.lives);
	}

	
}
