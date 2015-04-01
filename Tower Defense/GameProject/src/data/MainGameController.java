package data;
import helpers.GameActivity;
import helpers.CritterGenerator;
import helpers.GameControlPanelGeneral;
import helpers.GamePlayPanel;
import helpers.GameClock;
import helpers.MainMenuActivity;
import helpers.MouseAndKeyboardHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import entities.*;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import strategy.*;

public class MainGameController extends GamePlayPanel implements ActionListener, ChangeListener, ItemListener, IObserver {
		
	//declare game specific variables
	protected GamePlayPanel gamePanel;
	protected GameControlPanelGeneral controlPanel;
	protected GameActivity activity;
	
	//Below are all of our Swing elements
	JFrame mainFrame;
	private JButton bPause;
	private JButton bReturn;
	private JButton bStartWave;
	private JToggleButton bSpread;
	private JToggleButton bFire;
	private JToggleButton bIceBeam;
	private JToggleButton bLaser;
	private JToggleButton bNone;
	private JButton bUpgrade;
	private JButton bSell;
	private JSlider jsSpeed;
	private JComboBox<String> cbStrategies;
	
	//declare frame specific variables
	private Timer timer;
	private Player gamePlayer;
	private int waveStartMoney, waveStartLives;
	private int waveNumber;
	private int activeCritterIndex;
	ArrayList<DrawableEntity> drawableEntities;
	private TDMap tdMap;
	ArrayList<Critter> crittersInWave;
	ArrayList<Tower> towersOnMap;
	private boolean gamePaused;

	private boolean gameOver;

	private String selectedTowerToBuild;
	private Tower towerBeingPreviewed;
	private Tower selectedTower;
	
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
		MouseAndKeyboardHandler handler = new MouseAndKeyboardHandler(this);
		gamePanel.addMouseListener(handler);
		gamePanel.addMouseMotionListener(handler);
		gamePanel.addKeyListener(handler);

		
	}
	public void setPanelAndButtonProperties(){
		//create Field pointer defined in controller
		gamePanel = this;
		controlPanel = new GameControlPanelGeneral();
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
		bNone = this.getControlPanel().getNoneButton();
		bNone.addActionListener(this);
		bUpgrade = this.getControlPanel().getUpgradeButton();
		bUpgrade.addActionListener(this);
		bUpgrade.setEnabled(false);
		bSell = this.getControlPanel().getSellButton();
		bSell.addActionListener(this);
		bSell.setEnabled(false);
		jsSpeed = this.getControlPanel().getSpeedSlider();
		jsSpeed.addChangeListener(this);
		cbStrategies = this.getControlPanel().getCBStrategy();
		cbStrategies.addItem("Closest");
		cbStrategies.addItem("Farthest");
		cbStrategies.addItem("Fastest");
		cbStrategies.addItem("Weakest");
		cbStrategies.addItem("Strongest");
		cbStrategies.setSelectedIndex(0);
		cbStrategies.setEnabled(false);
		cbStrategies.addItemListener(this);
	}
	public void setInitialValues(){
		GameClock.getInstance().pause();
		gamePaused = true;
		gameOver = false;
		subjects = new ArrayList<Subject>();
		drawableEntities = new ArrayList<DrawableEntity>();
		towersOnMap = new ArrayList<Tower>();
		waveNumber = 0;
		gamePlayer = Player.getInstance();
		waveStartMoney = gamePlayer.getMoney();
		waveStartLives = gamePlayer.getLives();
		//default tower to build
		selectedTowerToBuild = "None";
		bNone.doClick();
	}
	public void setMainFrame(JFrame mFrame){
		mainFrame = mFrame;
//		mainFrame.setFocusable(true);
//		mainFrame.requestFocus();
//		mainFrame.addKeyListener(this);
	}
	private void startNewWave(){
		this.setPlaybackSpeed();
		//increment the wave number
		waveNumber +=1;
		bStartWave.setText("Start Wave " + (waveNumber+1));
		this.updateInfoLabelText();
		//reset the active critter index
		activeCritterIndex = 0;
		//record the amount of money they have as the start wave money (and same for lives)
		waveStartMoney = gamePlayer.getMoney();
		waveStartLives = gamePlayer.getLives();
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

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 *This method is called everytime the slider changes.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jsSpeed){
			setPlaybackSpeed();
		}
	}
	public void setPlaybackSpeed(){
		GameClock.getInstance().setDeltaTime(jsSpeed.getValue());
	}
	//Below are all button click methods: They are called when the buttons are clicked
	public void doPause(){
		if(gamePaused == false){
			GameClock.getInstance().pause();
			gamePaused = true;
			bPause.setText("Play");
		}else{
			GameClock.getInstance().unPause();
			gamePaused =false;
			bPause.setText("Pause");
		}
	}
	public void doReturnToMainMenu(){
		mainFrame.dispose();
		new MainMenuActivity();
		Player.getInstance().resetStats();
	}
	public void doStartWave(){
		GameClock.getInstance().unPause();
		gamePaused =false;
		bStartWave.setEnabled(false);
		startNewWave();
	}
	
	public void doSelectTower(ActionEvent arg0){
		if(drawableEntities.contains(towerBeingPreviewed)){
			drawableEntities.remove(towerBeingPreviewed);
		}
		selectedTowerToBuild = ((JToggleButton) arg0.getSource()).getName();
	}
	public void doUpgrade(){
		spendMoney(this.selectedTower.getUpPrice());
		this.selectedTower.upgradeTower();
		this.updateSelectedTowerInfoAndButtons();
	}
	public void doSell(){
		//give the player the money by spending the negative amount.
		this.spendMoney((-1)*selectedTower.getSellPrice());
		//remove this tower from our two lists, and delete it
		towersOnMap.remove(selectedTower);
		drawableEntities.remove(selectedTower);
		selectedTower = null;
		this.updateSelectedTowerInfoAndButtons();
		this.Draw();
	}
	//end of button calls
	
	//This method is called every time the timer times out, or when a button is clicked!
	//The basic Game loop
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
			if(arg0.getSource() == bPause && !gameOver){
				doPause();
			}else if(arg0.getSource() == bReturn){
				doReturnToMainMenu();
			}else if(arg0.getSource() == bStartWave && !gameOver){
				doStartWave();
			}else if(arg0.getSource() == bFire || arg0.getSource() == bLaser || arg0.getSource() == bIceBeam || arg0.getSource() == bSpread || arg0.getSource() ==bNone){
				doSelectTower(arg0);
			}else if(arg0.getSource() == bUpgrade){
				doUpgrade();
			}else if(arg0.getSource() == bSell){
				doSell();
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
						gamePlayer.addToMoney(c.getLoot()); 
					}else if(c.hasReachedEnd()==true){//if it has reached the end, take away a life
						gamePlayer.takeAwayALife();
						if(gamePlayer.getLives()==0){
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
		this.getControlPanel().setInfoLabelText("GAME OVER. You reached wave " + waveNumber + " with $" + gamePlayer.getMoney() + ".");
		disableAllGameButtons();
	}
	private void disableAllGameButtons(){
		bPause.setEnabled(false);
		bUpgrade.setEnabled(false);
		bSell.setEnabled(false);
		cbStrategies.setEnabled(false);
		
	}
	private void resetPlayerStats() {
		gamePlayer.setLives(waveStartLives);
		gamePlayer.setMoney(waveStartMoney);
		
	}

	public GameControlPanelGeneral getControlPanel(){
		return controlPanel;
	}
	public GamePlayPanel getPlayPanel(){
		return gamePanel;
	}
	public void updateInfoLabelText(){
		this.getControlPanel().setInfoLabelText("| Lives = " + gamePlayer.getLives()+ ", Money = " + gamePlayer.getMoney()+ ", Wavenumber = " + waveNumber + " |");
	}
	public void updateTowerInfoText(){
		
		String text = "";
		if(selectedTower != null){
			text += selectedTower.toString();
		}else{
			text += "No tower selected";
		}
		this.getControlPanel().setTowerInfoLabelText(text);
	}
	
	public void spendMoney(int amount){
		gamePlayer.addToMoney((-1)*amount);
		updateInfoLabelText();
	}
	//This method is called from the click handler when we get a click at a point
	public void reactToLeftClick(Point point){
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
			int playerMoney = gamePlayer.getMoney();
			//check which tower we want to place --This could be nicer (if we can somehow get the classtype?)
			if(selectedTowerToBuild.equalsIgnoreCase("Spread")){
				if(playerMoney >= Tower_SpreadShot.getBuyPrice()){
					towToBuild =new Tower_SpreadShot("Spread Tower", adjustedTowerPoint, crittersInWave, tdMap);
					moneyToSpend = Tower_SpreadShot.getBuyPrice();
				}else{
					alertUserInsufficientFundsForBuying();
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("Fire")){
				if(playerMoney >= Tower_Fire.getBuyPrice()){
					towToBuild = new Tower_Fire("Fire Tower", adjustedTowerPoint, crittersInWave, tdMap);
					moneyToSpend = Tower_Fire.getBuyPrice();
				}else{
					alertUserInsufficientFundsForBuying();
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("IceBeam")){
				if(playerMoney >= Tower_IceBeam.getBuyPrice()){
					towToBuild = new Tower_IceBeam("Ice Tower", adjustedTowerPoint, crittersInWave, tdMap);
					moneyToSpend = Tower_IceBeam.getBuyPrice();
				}else{
					alertUserInsufficientFundsForBuying();
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("Laser")){
				if(playerMoney >= Tower_Laser.getBuyPrice()){
					towToBuild = new Tower_Laser("Laser Tower", adjustedTowerPoint, crittersInWave, tdMap);
					moneyToSpend = Tower_Laser.getBuyPrice();
				}else{
					alertUserInsufficientFundsForBuying();
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("None")){
				//no tower = don't do anything.
			}else{
				System.out.println("Error: No appropriate tower type (coding error)");
			}
			if(towToBuild!= null){
				spendMoney(moneyToSpend);
				buildTower(towToBuild);
				tileAtClick.setTowerOnTile(towToBuild);
			}
		}else{ //if there is a tower on this block
			if(selectedTower != null){ selectedTower.setSelected(false);}
			selectedTower = tileAtClick.getTowerOnTile();
			selectedTower.setSelected(true);
			updateSelectedTowerInfoAndButtons();
		}
	}
	/*
	 * builds a tower t and puts it in the drawable entities to be drawn.
	 */
	public void buildTower(Tower t){
		towersOnMap.add(t);
		drawableEntities.add(t);
		this.updateInfoLabelText();
		Draw();
	}
	
	public void updateSelectedTowerInfoAndButtons(){
		if(selectedTower != null){
			//we only want to enable upgrades if they have enough money and if the tower isn't at max level
			if(selectedTower.getLevel() < Tower.getMaxTowerLevel() && gamePlayer.getMoney() >= selectedTower.getUpPrice()){
				bUpgrade.setEnabled(true);
			}else{
				bUpgrade.setEnabled(false);
			}
			//we always want to enable the sell button.
			bSell.setEnabled(true);
			cbStrategies.setEnabled(true);
			cbStrategies.setSelectedItem(selectedTower.getStrategy().toString());
		}else{
			bUpgrade.setEnabled(false);
			bSell.setEnabled(false); //if there is no tower, don't allow them to sell it.
			cbStrategies.setEnabled(false);
		}
		updateTowerInfoText();
	}
	
	
	public void alertUserInsufficientFundsForBuying(){
		System.out.println("The " + gamePlayer.getMoney() + " dollars that you have is not enough for the " + selectedTowerToBuild + " tower.");
	}
	
	public void towerToPreview(Point point){
	
		//first, get the point of the grid where we clicked.
		double xRatio = ((double)point.getX())/((double)tdMap.getPixelWidth());
		double yRatio = ((double)point.getY())/((double)tdMap.getPixelHeight());
		
		int xGridPos = (int) Math.floor(xRatio * tdMap.getGridWidth());
		int yGridPos = (int) Math.floor(yRatio * tdMap.getGridHeight());
		//**TODO tile at movement
		MapTile tileAtClick = tdMap.getTile(xGridPos, yGridPos);
		int tileType = tileAtClick.getTileValue();
		//make sure it is not a path position, 
		if(tileType == TDMap.PATH){
			//do nothing if it is a path position. (maybe we will add a feature here?)
		}else if(tileAtClick.getTowerOnTile() == null){ //If there is no TOWER already at this position, build one
			Point adjustedTowerPoint = tdMap.getPosOfBlock_pixel(xGridPos, yGridPos);
			Tower towToPreview= null;
			//int moneyToSpend = 0;
			int playerMoney = gamePlayer.getMoney();
			//check which tower we want to place --This could be nicer (if we can somehow get the classtype?)
			if(selectedTowerToBuild.equalsIgnoreCase("Spread")){
				if(playerMoney >= Tower_SpreadShot.getBuyPrice()){
					towToPreview =new Tower_SpreadShot("Spread", adjustedTowerPoint, crittersInWave, tdMap);
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("Fire")){
				if(playerMoney >= Tower_Fire.getBuyPrice()){
					towToPreview = new Tower_Fire("Fire", adjustedTowerPoint, crittersInWave, tdMap);
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("IceBeam")){
				if(playerMoney >= Tower_IceBeam.getBuyPrice()){
					towToPreview = new Tower_IceBeam("IceBeam", adjustedTowerPoint, crittersInWave, tdMap);
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("Laser")){
				if(playerMoney >= Tower_Laser.getBuyPrice()){
					towToPreview = new Tower_Laser("Laser", adjustedTowerPoint, crittersInWave, tdMap);
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("None")){
				//no tower = don't do anything.
			}else{
				System.out.println("Error: No appropriate tower type (coding error)");
			}
			if(towToPreview!= null){
				if(drawableEntities.contains(towerBeingPreviewed)){
					drawableEntities.remove(towerBeingPreviewed);
				}
				towerBeingPreviewed = towToPreview;
				Color oldColor = towerBeingPreviewed.getColor();
				//System.out.println("Color is " + oldColor.getRed() + ", " + oldColor.getBlue() + ", " + oldColor.getGreen());
				Color newColor = new Color(Math.max(oldColor.getRed()-100,0),Math.max(oldColor.getGreen()-100,0),Math.max(oldColor.getBlue()-100,0));
				
				towerBeingPreviewed.setColor(newColor);  
				towerBeingPreviewed.setEnabled(false);
				drawableEntities.add(towerBeingPreviewed);
				//Draw();
			}
		}else{ //if there is a tower on this block
			//don't do anything if there is a tower on the block
		}
	}
	public void reactToKeypress(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			this.bNone.doClick();
		}
	}
	
	public void reactToRightClick(Point point) {
		this.bNone.doClick();
		//this.strategyMenu.show(this, point.getX(), point.getY());
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == this.cbStrategies){
			String item = (String) e.getItem();
			if(item.equalsIgnoreCase("Closest")){
				//should we be creating a new strat? Or should we make the strats singleton?
				selectedTower.setStrategy(new Closest()); 
			}else if(item.equalsIgnoreCase("Farthest")){
				selectedTower.setStrategy(new Farthest());
			}else if(item.equalsIgnoreCase("Strongest")){
				selectedTower.setStrategy(new Strongest());
			}else if(item.equalsIgnoreCase("Weakest")){
				selectedTower.setStrategy(new Weakest());
			}else if(item.equalsIgnoreCase("Fastest")){
				selectedTower.setStrategy(new Fastest());
			}else{
				System.out.println("Error with selection of strategies: Correct name not found");
			}
		}
	}



	
}
