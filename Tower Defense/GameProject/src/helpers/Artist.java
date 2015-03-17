package helpers;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.TDMap;

public class Artist {
	
	public static final int WIDTH=1280;
	public static final int HEIGHT=800;
	
	
	public static void beginSession()
	{
		Display.setTitle("Tower Defense - Group 6");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		
		
	}
	
	public static void drawQuad(float x, float y, float height, float width)
	{
		glBegin(GL_QUADS);
		glVertex2f(x,y);
		glVertex2f(x,y+width);
		glVertex2f(x+height,y+width);
		glVertex2f(x+height,y);
		glEnd();
		
	}
	
	public static void drawQuadTexture(Texture tex, float x, float y, float height, float width)
	{
		tex.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		tex.release();
		glEnd();
		glLoadIdentity();
	}
	
	public static Texture loadTexture(String path, String fileType)
	{
		Texture tex= null;
		InputStream in= ResourceLoader.getResourceAsStream(path);
		try {
			tex= TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	
	public static void drawMap(TDMap tdMap)
	{
		int mapWidth=tdMap.getWidth();
		int mapHeight=tdMap.getHeight();
		int scaledWidth=(int) WIDTH/mapWidth;
		int scaledHeight=(int) HEIGHT/mapHeight;
		String back= tdMap.getBackdrop();
		Texture texPath= null;
		Texture texTower= null; 
		texPath= loadTexture("res/GenericPath.png", "PNG");
		texTower= loadTexture("res/GenericTower.png", "PNG");
		for(int i=0; i<mapWidth; i++)
		{
			for(int j=0; j<mapHeight; j++)
			{
				int tileType= tdMap.getType(i, j);
				if(tileType==2)
					drawQuadTexture(texPath, i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
				else
					drawQuadTexture(texTower, i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
			}
		}
	}
}
