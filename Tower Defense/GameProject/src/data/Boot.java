package data;
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
		while(!Display.isCloseRequested())
		{
			drawMap(tdMap);
			Critter c1 = new Critter_X(1, tdMap);
			c1.traversePath();
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
