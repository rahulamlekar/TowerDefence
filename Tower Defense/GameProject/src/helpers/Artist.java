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

import entities.Critter;
import entities.TDMap;

public class Artist {
	
	public static final int PIXELWIDTH=1280;
	public static final int PIXELHEIGHT=800;

	public static void beginSession()
	{
		Display.setTitle("Tower Defense - Group 6");
		try {
			Display.setDisplayMode(new DisplayMode(PIXELWIDTH, PIXELHEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, PIXELWIDTH, PIXELHEIGHT, 0, 1, -1);
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
		int scaledWidth=(int) PIXELWIDTH/mapWidth;
		int scaledHeight=(int) PIXELHEIGHT/mapHeight;
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
				if(tileType==2){
					//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
					//glColor3f(0.5f, 0.5f, 0.5f);
					//drawQuad(i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
					drawQuadTexture(texPath, i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
				
				}else{
					//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
					//glColor3f(0.5f, 0.5f, 0.5f);
					//drawQuad(i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
					drawQuadTexture(texTower, i*scaledWidth, j*scaledHeight, scaledWidth, scaledHeight);
				}
			}
		}
	}
	public static void drawCritter(Critter crit){
		int critterSize = crit.getSize();
		//Set the color
		//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		EntityColor col = new EntityColor(crit.getColor());
		//glColor3f(col.getR(), col.getG(), col.getB());
		drawQuad(crit.getPixelPosition().getX() - critterSize/2, crit.getPixelPosition().getY() - critterSize/2, critterSize,critterSize);

	}
	
	
}
