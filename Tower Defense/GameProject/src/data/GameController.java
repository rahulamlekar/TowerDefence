package data;
import helpers.ApplicationFrame;
import helpers.CritterGenerator;
import helpers.Field;
import helpers.GameClock;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import entities.*;

import java.util.ArrayList;


public class GameController extends Field implements ActionListener, IObserver {
		
	//declare game specific variables
	protected Field mField;
	
	
	//declare frame specific variables
	private Timer timer;
	private int lives, money, waveStartLives, waveStartMoney;
	private int waveNumber;
	private int activeCritterIndex;
	ArrayList<DrawableEntity> drawableEntities;
	private TDMap tdMap;
	ArrayList<Critter> crittersInWave;
	ArrayList<Tower> towersOnMap;
	private boolean gamePaused;
	private JButton pauseButton;
	private boolean gameOver;
	
	Tower tf1, tf2, tf3;
	ArrayList<Subject> subjects;
	public GameController()
	{
		//create Field pointer defined in controller
		mField = this;
		pauseButton = this.getField().getPauseButton();
		pauseButton.addActionListener(this);
		gamePaused = false;
		gameOver = false;
		//start the timer
		timer = new Timer(ApplicationFrame.TIMEOUT,this);
		timer.start();
		//initialize arraylists
		subjects = new ArrayList<Subject>();
		drawableEntities = new ArrayList<DrawableEntity>();
		towersOnMap = new ArrayList<Tower>();
		waveNumber = 0;
		lives = 10;
		waveStartLives = 10;
		money = 200;
		waveStartMoney = 200;

		
		updateInfoLabelText();
		
		//get the map
		tdMap= new TDMap("res/DIRTMAP1.TDMap");
		
		//create a couple towers and add them to the drawableEntitites.
		tf1 = new IceBeamTower("tf1", tdMap.getPosOfBlock_pixel(5, 1), tdMap.xBlock, crittersInWave);
		tf2 = new LaserTower("tf2", tdMap.getPosOfBlock_pixel(25, 1), tdMap.xBlock, crittersInWave);
		tf3 = new LaserTower("tf3", tdMap.getPosOfBlock_pixel(15, 1), tdMap.xBlock, crittersInWave);
		towersOnMap.add(tf1);
		towersOnMap.add(tf2);
		towersOnMap.add(tf3);
		startNewWave();
		
	}
	
	private void startNewWave(){
		//increment the wave number
		waveNumber +=1;
		//reset the active critter index
		activeCritterIndex = 0;
		//record the amount of money they have as the start wave money (and same for lives)
		waveStartMoney = money;
		waveStartLives = lives;
		//remove all current entities
		drawableEntities.clear();
		//remove all current subjects
		subjects.clear();
		
		//add the map back into the drawable entities
		drawableEntities.add(tdMap);

		//get the critters for this wave level
		crittersInWave = CritterGenerator.getGeneratedCritterWave(waveNumber, tdMap);
		for(int i = 0; i < crittersInWave.size(); i++){
			//add them to the drawableEntitiesList
			drawableEntities.add(crittersInWave.get(i));
			crittersInWave.get(i).addObserver(this); //makes this an observer of critter
			subjects.add(crittersInWave.get(i));
		}
		//add all of the towers back into the drawable entities
		for(Tower t : towersOnMap){
			drawableEntities.add(t);
			t.setCrittersOnMap(crittersInWave);
		}
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



	//This method is called every time the timer times out
	//The basic Game loop
	@Override
	public void actionPerformed(ActionEvent arg0) {
	if(!gameOver){
			if(arg0.getSource() == pauseButton){
				if(gamePaused == false){
					GameClock.getInstance().pause();
					gamePaused = true;
					pauseButton.setText("Play");
				}else{
					GameClock.getInstance().setDeltaTime(1);
					gamePaused =false;
					pauseButton.setText("Pause");
				}
			}else{
				if(gamePaused == false){
					if(activeCritterIndex == 0){
						crittersInWave.get(activeCritterIndex).setActive(true);
						activeCritterIndex +=1;
					}else if(activeCritterIndex < crittersInWave.size()){
						if(crittersInWave.get(activeCritterIndex-1).getPixelPosition().getX() > 50){
							crittersInWave.get(activeCritterIndex).setActive(true);
							activeCritterIndex +=1;
						}
					}
					Draw();
				}
			}
		}
	}
	public void Draw(){
		//calls the paintComponent function
		mField.repaint();
	}
	//one of my subjects has changed. Go through them all and check stats.
	public void observerUpdate(){
		if(gamePaused ==false){
			resetPlayerStats();
			boolean anyCrittersLeft = false;
			for(Subject s : subjects){
				if(s instanceof Critter){
					//it is a critter. Check to see if it is dead or if it has reached the end.
					Critter c = (Critter) s;
					//if it is dead, give the appropriate money to the player
					if(c.isAlive() && c.hasReachedEnd()==false){
						anyCrittersLeft = true;
					}else if(c.isAlive()==false){
						money += c.getLoot();
					}else if(c.hasReachedEnd()==true){//if it has reached the end, take away a life
						this.lives -=1;
						if(lives==0){
							endGame();
						}
					}
				}
			}
			//if no critters are left, start a new wave
			if(anyCrittersLeft == false){
				startNewWave();
			}
			updateInfoLabelText();
		}
	}
	private void endGame(){
		gameOver = true;
		this.getField().setInfoLabelText("GAME OVER. You reached wave: " + waveNumber + " with $" + money);
		GameClock.getInstance().pause();
	}
	private void resetPlayerStats() {
		lives = waveStartLives;
		money = waveStartMoney;
		
	}

	public Field getField(){
		return mField;
	}
	public void updateInfoLabelText(){
		if(gamePaused ==false){
		this.getField().setInfoLabelText("Lives = " + lives + ", Money = " + money + ", Wavenumber = " + waveNumber);
		}
	}

	
}
