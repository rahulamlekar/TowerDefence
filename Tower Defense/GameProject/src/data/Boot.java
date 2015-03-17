package data;

import static org.lwjgl.opengl.GL11.*;
import static helpers.Artist.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import entities.TDMap;

public class Boot {
	
	public Boot()
	{
		beginSession();
		
		TDMap tdMap= new TDMap("C:\\Users\\Yash Gupta\\OneDrive\\NetBeansProjects\\ECSE321PA1\\Try1.TDMap");
		while(!Display.isCloseRequested())
		{
			drawMap(tdMap);
			
			Display.update();
			Display.sync(20);
		}
	}
	
	public static void main(String args[])
	{
		new Boot();
	}
}
