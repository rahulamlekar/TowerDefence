package data;
import helpers.ApplicationFrame;
import helpers.Artist_Swing;
import helpers.Field;
import helpers.GameClock;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.*;

import java.util.ArrayList;

/**
 * 
 * @author Shabbir
 * This class glues the model and the view together by updating the model
 * the redrawing the view
 */
public class GameController extends Field implements ActionListener {
		
	//declare game specific variables
	protected Field mField;
	protected boolean gameover=false;
	
	//declare frame specific variables
	private Timer timer;
	private int lives;
	private int activeCrittersIndex;
	ArrayList<DrawableEntity> drawableEntities;
	private TDMap tdMap;
	ArrayList<Critter> crittersToBePlaced;
	Tower tf1, tf2;
	
	public GameController()
	{
		//create Field pointer defined in controller
		mField = this;
		//start the timer
		timer = new Timer(ApplicationFrame.TIMEOUT,this);
		timer.start();
		
		lives = 10;
		drawableEntities = new ArrayList<DrawableEntity>();
		activeCrittersIndex = 0;
		
		//get the map and add it to the drawableEntities (to be updated)
		tdMap= new TDMap("res/DIRTMAP1.TDMap");
		
		drawableEntities.add(tdMap);
		
		//get the critters and add them to the drawableEntities (to be updated)
		//crittersToBePlaced = CritterGenerator.getGeneratedCritterWave(1, tdMap);
		//just one for debugging....
		crittersToBePlaced = new ArrayList<Critter>();
		crittersToBePlaced.add(new Critter_Arrow(1, tdMap));
		crittersToBePlaced.add(new Critter_Arrow(1, tdMap));
		
		for(int i = 0; i < crittersToBePlaced.size(); i++){
			drawableEntities.add(crittersToBePlaced.get(i));
			//crittersToBePlaced.get(i).addObserver(this); //makes this an observer of critter
		}
		
		tf1 = new IceBeamTower("tf1", tdMap.getPosOfBlock_pixel(5, 1), tdMap.xBlock, crittersToBePlaced);
		tf2 = new LaserTower("tf2", tdMap.getPosOfBlock_pixel(25, 1), tdMap.xBlock, crittersToBePlaced);
		drawableEntities.add(tf1);
		drawableEntities.add(tf2);
	}
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//redraw the snake and the food
		//update and draw all drawableEntities.
		for(int i = 0; i < drawableEntities.size(); i++){
			drawableEntities.get(i).updateAndDraw(g);
		}
	
        Toolkit.getDefaultToolkit().sync();
	}



	//This method is called everytime the timer times out
	//The basic Game loop
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(activeCrittersIndex == 0){
			crittersToBePlaced.get(activeCrittersIndex).setActive(true);
			activeCrittersIndex +=1;
		}else if(activeCrittersIndex < crittersToBePlaced.size()){
			if(crittersToBePlaced.get(activeCrittersIndex-1).getPixelPosition().getX() > 50){
				crittersToBePlaced.get(activeCrittersIndex).setActive(true);
				activeCrittersIndex +=1;
			}
		}
		Draw();
	}
	public void Draw(){
		//calls the paintComponent function
		mField.repaint();
	}
	

	
	//Reset's the game
	public void restart(){
		mField.pnl.setVisible(false);
		gameover=false;
		//mSnake=new Snake();
	}
	public static void pause(){
		GameClock.getInstance().setDeltaTime(0);
	}
///////////////////GETTERS AND SETTERS//////////////////
	public Field getField(){
		return mField;
	}

	
}
