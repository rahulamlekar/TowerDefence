package data;
import helpers.GameActivity;
import helpers.CritterGenerator;
import helpers.GameControlPanel;
import helpers.GamePlayPanel;
import helpers.GameClock;
import helpers.MainMenuActivity;
import helpers.MouseHandler;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import entities.*;

import java.util.ArrayList;

import javax.swing.Icon;

public class MainGameController extends GamePlayPanel implements ActionListener, IObserver {
		
	//declare game specific variables
	protected GamePlayPanel gamePanel;
	protected GameControlPanel controlPanel;
	protected GameActivity activity;
	
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

	private boolean gameOver;
	JFrame mainFrame;
	private JButton bPause;
	private JButton bReturn;
	private JButton bStartWave;
	private JToggleButton bSpread;
	private JToggleButton bFire;
	private JToggleButton bIceBeam;
	private JToggleButton bLaser;
	private String selectedTower;
	
	Tower tf1, tf2, tf3;
	ArrayList<Subject> subjects;
	public MainGameController(TDMap map)
	{
		setPanelAndButtonProperties();
		setInitialValues();	
		this.tdMap = map;
		//add the map back into the drawable entities
		drawableEntities.add(tdMap);
		
		//start the timer
		timer = new Timer(GameActivity.TIMEOUT,this);
		timer.start();
		
		//initialize arraylists
		updateInfoLabelText();
		MouseHandler handler = new MouseHandler(this);
		gamePanel.addMouseListener(handler);
	}
	public void setPanelAndButtonProperties(){
		//create Field pointer defined in controller
		gamePanel = this;
		controlPanel = new GameControlPanel();
		bPause = this.getControlPanel().getPauseButton();
		bPause.addActionListener(this);
		bReturn = this.getControlPanel().getReturnButton();
		bReturn.addActionListener(this);
		bStartWave = this.getControlPanel().getStartWaveButton();
		bStartWave.addActionListener(this);
		bSpread = this.getControlPanel().getSpreadButton();
		bSpread.addActionListener(this);
		bFire = this.getControlPanel().getFireButton();
		bFire.addActionListener(this);
		bLaser = this.getControlPanel().getLaserButton();
		bLaser.addActionListener(this);
		bIceBeam = this.getControlPanel().getIceButton();
		bIceBeam.addActionListener(this);
	}
	public void setInitialValues(){
		GameClock.getInstance().pause();
		gamePaused = true;
		gameOver = false;
		subjects = new ArrayList<Subject>();
		drawableEntities = new ArrayList<DrawableEntity>();
		towersOnMap = new ArrayList<Tower>();
		waveNumber = 0;
		lives = 10;
		waveStartLives = lives;
		money = 2000;
		waveStartMoney = money;
		//default tower to build
		selectedTower = "Spread";
		bSpread.doClick();
	}
	public void setMainFrame(JFrame mFrame){
		mainFrame = mFrame;
	}
	private void startNewWave(){
		//increment the wave number
		waveNumber +=1;
		bStartWave.setText("Start Wave " + (waveNumber+1));
		this.updateInfoLabelText();
		//reset the active critter index
		activeCritterIndex = 0;
		//record the amount of money they have as the start wave money (and same for lives)
		waveStartMoney = money;
		waveStartLives = lives;
		//remove all current entities
		drawableEntities.clear();
		//remove all current subjects
		subjects.clear();

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
		
			if(arg0.getSource() == bPause && !gameOver){
				if(gamePaused == false){
					GameClock.getInstance().pause();
					gamePaused = true;
					bPause.setText("Play");
				}else{
					GameClock.getInstance().unPause();
					gamePaused =false;
					bPause.setText("Pause");
				}
			}else if(arg0.getSource() == bReturn){
				mainFrame.dispose();
				new MainMenuActivity();
			}else if(arg0.getSource() == bStartWave && !gameOver){
				GameClock.getInstance().unPause();
				gamePaused =false;
				bStartWave.setEnabled(false);
				startNewWave();
			}else if(arg0.getSource() == bFire || arg0.getSource() == bLaser || arg0.getSource() == bIceBeam || arg0.getSource() == bSpread){
				selectedTower = ((JToggleButton) arg0.getSource()).getName();
			}else if(!gameOver){
				if(gamePaused == false){
					if(activeCritterIndex == 0){
						crittersInWave.get(activeCritterIndex).setActive(true);
						activeCritterIndex +=1;
					}else if(activeCritterIndex < crittersInWave.size()){
						if(crittersInWave.get(activeCritterIndex-1).getPixelPosition().getX() > 50 || crittersInWave.get(activeCritterIndex-1).isAlive()==false){
							crittersInWave.get(activeCritterIndex).setActive(true);
							activeCritterIndex +=1;
						}
					}
				}
				Draw();
			}
			
		
	}
	
	public void Draw(){
		//calls the paintComponent function
		gamePanel.repaint();
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
				bStartWave.setEnabled(true);

				//startNewWave();
			}
			if(!gameOver){
			updateInfoLabelText();
			}
		}
	}
	private void endGame(){
		gameOver = true;
		gamePaused =true;
		GameClock.getInstance().pause();
		//this.getControlPanel().setInfoLabelText("GAME OVER. You reached wave " + waveNumber + " with $" + money + ".");
		bPause.setEnabled(false);
	}
	private void resetPlayerStats() {
		lives = waveStartLives;
		money = waveStartMoney;
		
	}

	public GameControlPanel getControlPanel(){
		return controlPanel;
	}
	public GamePlayPanel getPlayPanel(){
		return gamePanel;
	}
	public void updateInfoLabelText(){

		this.getControlPanel().setInfoLabelText("| Lives = " + lives + ", Money = " + money + ", Wavenumber = " + waveNumber + " |");
		
	}
	
	public void spendMoney(int amount){
		money = money - amount;
		updateInfoLabelText();
	}
	//This method is called from the click handler when we get a click at a point
	public void tryToBuildTower(Point point){
		//first, get the point of the grid where we clicked.
		double xRatio = ((double)point.getX())/((double)tdMap.getPixelWidth());
		double yRatio = ((double)point.getY())/((double)tdMap.getPixelHeight());
		
		int xGridPos = (int) Math.floor(xRatio * tdMap.getGridWidth());
		int yGridPos = (int) Math.floor(yRatio * tdMap.getGridHeight());
		MapTile tileAtClick = tdMap.getTile(xGridPos, yGridPos);
		int tileType = tileAtClick.getTileValue();
		//make sure it is not a path position, 
		if(tileType == TDMap.PATH){
			//do nothing if it is a path position. (maybe we will add a feature here?)
		}else if(tileAtClick.getTowerOnTile() == null){ //If there is no TOWER already at this position, build one
			Point adjustedTowerPoint = tdMap.getPosOfBlock_pixel(xGridPos, yGridPos);
			Tower towToBuild = null;
			int moneyToSpend = 0;
			
			//check which tower we want to place --This could be nicer (if we can somehow get the classtype?)
			if(selectedTower.equalsIgnoreCase("Spread")){
				if(this.money >= Tower_SpreadShot.getBuyPrice()){
					towToBuild =new Tower_SpreadShot("Spread", adjustedTowerPoint, crittersInWave, tdMap);
					moneyToSpend = Tower_SpreadShot.getBuyPrice();
				}else{
					alertUserInsufficientFundsForBuying();
				}
			}else if(selectedTower.equalsIgnoreCase("Fire")){
				if(this.money >= Tower_Fire.getBuyPrice()){
					towToBuild = new Tower_Fire("Fire", adjustedTowerPoint, crittersInWave, tdMap);
					moneyToSpend = Tower_Fire.getBuyPrice();
				}else{
					alertUserInsufficientFundsForBuying();
				}
			}else if(selectedTower.equalsIgnoreCase("IceBeam")){
				if(this.money >= Tower_IceBeam.getBuyPrice()){
					towToBuild = new Tower_IceBeam("IceBeam", adjustedTowerPoint, crittersInWave, tdMap);
					moneyToSpend = Tower_IceBeam.getBuyPrice();
				}else{
					alertUserInsufficientFundsForBuying();
				}
			}else if(selectedTower.equalsIgnoreCase("Laser")){
				if(this.money >= Tower_Laser.getBuyPrice()){
					towToBuild = new Tower_Laser("Laser", adjustedTowerPoint, crittersInWave, tdMap);
					moneyToSpend = Tower_Laser.getBuyPrice();
				}else{
					alertUserInsufficientFundsForBuying();
				}
			}else{
				System.out.println("Error: No appropriate tower type (coding error)");
			}
			if(towToBuild!= null){
				spendMoney(moneyToSpend);
				buildTower(towToBuild);
				tileAtClick.setTowerOnTile(towToBuild);
			}
		}else{ //if there is a tower on this block
			Tower currTower = tileAtClick.getTowerOnTile();
			int priceToUpgrade = currTower.getUpPrice();
			if(this.money >= priceToUpgrade){
				spendMoney(priceToUpgrade);
				currTower.upgradeTower();
			}else{
				//TODO: Alert user insufficient funds
			}
		}
	}
	public void buildTower(Tower t){
		towersOnMap.add(t);
		drawableEntities.add(t);
		this.updateInfoLabelText();
		Draw();
	}
	public void alertUserInsufficientFundsForBuying(){
		System.out.println("The " + money + " dollars that you have is not enough for the " + selectedTower + " tower.");
	}
	
}
