package controllers;
import helpers.Artist_Swing;
import helpers.CritterGenerator;
import helpers.GameClock;
import helpers.Helper;
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
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.Timer;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import models.*;
import strategies.*;
import views.GameApplicationFrame;
import views.GameControlPanel;
import views.MapPanel;
import views.MenuApplicationFrame;

/**
 *  This is the main Game Controller, as the name suggests.
 *  This class belongs to the Game Logic layer and handles
 *  all the complex interfacing between the map, the 
 *  numerous towers placed on the map and critters that
 *  traverse the shortest path.
 *
 */
public class GameController extends MapPanel implements ActionListener, ChangeListener, ItemListener, IObserver {
		
	//declare game specific variables

    /**
     *  The Map Panel. It deals with the Tiles, Towers and the Critter Path.
     */
    	protected MapPanel gamePanel;

    /**
     *  The Game Control Panel that is displayed at the bottom of the screen.
     */
    protected GameControlPanel controlPanel;
	
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
	private JButton bCritterInfo;
	
	//declare frame specific variables
	private Timer timer;
	private Player gamePlayer;
	private int waveStartMoney, waveStartLives;
	private int waveNumber;
	private int activeCritterIndex;
	ArrayList<DrawableEntity> drawableEntities;
	
	//Next we have our drawable entities. We have the Map, critters on it, and towers.
	private TDMap tdMap;
	ArrayList<Critter> crittersInWave;
	ArrayList<Tower> towersOnMap;
	
	//Some game states (used to determine what buttons we allow the player to click)
	private boolean gamePaused;
	private boolean gameOver;

	//now we have our current selections, like the tower we want to build, preview, tile, etc.
	private String selectedTowerToBuild;
	private Tower towerBeingPreviewed;
	private Tower selectedTower;
	private MapTile selectedTile;
	
	//We have two helpers directly with the game controller, the clock, and the artist.
	private Artist_Swing artist;
	private GameClock clock;
	ArrayList<Helper> helpers; //putting our helpers in an arrayList is mainly for organization.
	
	//and we have a list of subjects that this class (as an IObserver) watches.
	ArrayList<Subject> subjects;
	
	
	
    /**
     * @param map   This takes a TDMap object as the map on 
     *              which to play the game.
     */
    public GameController(TDMap map)
	{
		setPanelAndButtonProperties();
		setInitialValues();	
		this.tdMap = map;

		artist.setGridHeight(map.getGridHeight());
		artist.setGridWidth(map.getGridWidth());
		//add the map back into the drawable entities
		drawableEntities.add(tdMap);
		
		//start the timer
		timer = new Timer(GameApplicationFrame.TIMEOUT,this);
		timer.start();
		
		//initialize arraylists
		updateInfoLabelText();
		MouseAndKeyboardHandler handler = new MouseAndKeyboardHandler(this);
		gamePanel.addMouseListener(handler);
		gamePanel.addMouseMotionListener(handler);

		
	}
	private void setPanelAndButtonProperties(){
		//So we set this to be our game panel (for mouse click purposes)
		gamePanel = this;
		controlPanel = new GameControlPanel();
		//now we have to get all of our Swing objects, and add action listeners (so we know when clicked).
		//buttons:
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
		bCritterInfo = this.getControlPanel().getCritterInfoButton();
		bCritterInfo.addActionListener(this);
		//jsSpeed is the slider for the speed of the game
		jsSpeed = this.getControlPanel().getSpeedSlider();
		jsSpeed.addChangeListener(this);
		//we initialize the strategy box and set its selected index to the first.
		cbStrategies = this.getControlPanel().getCBStrategy();
		cbStrategies.setSelectedIndex(0);
		cbStrategies.setEnabled(false); //disable it (until a tower is selected)
		cbStrategies.addItemListener(this);
		
	}
	/*
	 * sets the initial values of the variables for the game. 
	 * Also initializes arrays and gets the instances of the singleton classes.
	 */
	private void setInitialValues(){
		//get the instances of our singleton helpers
		artist = Artist_Swing.getInstance();
		clock = GameClock.getInstance();
		//also get our singleton player
		gamePlayer = Player.getInstance();
		
		clock.pause(); //start the game off paused
		gamePaused = true; //game is paused
		gameOver = false; //game is not over
		//initialize arraylists
		subjects = new ArrayList<Subject>();
		helpers = new ArrayList<Helper>();
		drawableEntities = new ArrayList<DrawableEntity>();
		towersOnMap = new ArrayList<Tower>();
		//we start on wavenumber 0
		waveNumber = 0;
		
		//we start with the money before any is spent
		waveStartMoney = gamePlayer.getMoney();
		waveStartLives = gamePlayer.getLives();
		//default tower to build
		selectedTowerToBuild = "None";

		//add into a list of helpers (currently for UML diagram)
		helpers.add(artist);
		helpers.add(clock);
		bNone.doClick(); //start by having no tower selected
	}

    /**
     *
     * @param mFrame
     */
	//sets the JFrame that the game is displayed on
    public void setMainFrame(JFrame mFrame){
		mainFrame = mFrame;
	}
    //starts a new wave
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
			crittersInWave.get(i).addObs(this); //makes this an observer of critter
			subjects.add(crittersInWave.get(i));
		}
		//add all of the towers back into the drawable entities
		for(Tower t : towersOnMap){
			drawableEntities.add(t);
			t.setCrittersOnMap(crittersInWave);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
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

    /**
     *  This relates to how fast or slow the wave apparently appears to the player.
     */
    public void setPlaybackSpeed(){
		clock.setDeltaTime(jsSpeed.getValue());
	}
	//Below are all button click methods: They are called when the buttons are clicked
	private void doPause(){
		if(gamePaused == false){
			clock.pause();
			gamePaused = true;
			bPause.setText("Play");
		}else{
			clock.unPause();
			gamePaused =false;
			bPause.setText("Pause");
		}
	}
	//when main menu is clicked
	private void doReturnToMainMenu(){
		mainFrame.dispose();
		new MenuApplicationFrame();
		Player.getInstance().resetStats();
	}
	//when start wave is clicked
	private void doStartWave(){
		clock.unPause();
		gamePaused =false;
		bStartWave.setEnabled(false);
		startNewWave();
	}
	//when one of the tower buttons is clicked
	private void doSelectTower(ActionEvent arg0){
		if(drawableEntities.contains(towerBeingPreviewed)){
			drawableEntities.remove(towerBeingPreviewed);
		}
		selectedTowerToBuild = ((JToggleButton) arg0.getSource()).getName();
	}
	//when upgrade is clicked
	private void doUpgrade(){
		spendMoney(this.selectedTower.getUpPrice());
		this.selectedTower.upgradeTower();
		this.updateSelectedTowerInfoAndButtons();
	}
	//when sell is clicked
	private void doSell(){
		//give the player the money by spending the negative amount.
		this.spendMoney((-1)*selectedTower.getSellPrice());
		//remove this tower from our two lists, and delete it
		towersOnMap.remove(selectedTower);
		drawableEntities.remove(selectedTower);
		selectedTower = null;
		if(selectedTile != null){
			selectedTile.setTowerOnTile(null);
		}
		//update buttons
		this.updateSelectedTowerInfoAndButtons();
		this.Draw(); //redraw
	}
	private void doDisplayCritterInfo(){
		JOptionPane.showMessageDialog(null, Critter.CRITTERMESSAGE, "Critters", JOptionPane.PLAIN_MESSAGE);
	}
	//end of button calls
	
	/*
	 * This method is called every time the timer times out, or when a button is clicked!(non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
			if(arg0.getSource() == bPause){
				doPause();
			}else if(arg0.getSource() == bReturn){
				doReturnToMainMenu();
			}else if(arg0.getSource() == bStartWave ){
				doStartWave();
			}else if(arg0.getSource() == bFire || arg0.getSource() == bLaser || arg0.getSource() == bIceBeam || arg0.getSource() == bSpread || arg0.getSource() ==bNone){
				doSelectTower(arg0);
			}else if(arg0.getSource() == bUpgrade){
				doUpgrade();
			}else if(arg0.getSource() == bSell){
				doSell();
			}else if(arg0.getSource() == bCritterInfo){
				doDisplayCritterInfo();
			}else if(!gameOver){
				if(gamePaused == false){
					if(activeCritterIndex == 0){
						crittersInWave.get(activeCritterIndex).setActive(true);
						activeCritterIndex +=1;
					}else if(activeCritterIndex < crittersInWave.size()){
						Critter curr = crittersInWave.get(activeCritterIndex -1);
						Point currPos = curr.getPixelPosition();
						if(Math.abs(currPos.getX() - curr.getListPixelPath().get(1).getX()) > 50 || Math.abs(currPos.getY() - curr.getListPixelPath().get(1).getY()) > 50 || curr.isAlive()==false || curr.hasReachedEnd()){
							crittersInWave.get(activeCritterIndex).setActive(true);
							activeCritterIndex +=1;
						}
					}
				}
				Draw();
			}
			
		
	}
	//calls the repaint method
	private void Draw(){
		gamePanel.repaint();
	}

    /**
     *  This will update the game whenever one of the subjects (Critters)of the Game Controller is changed,
     *  e.g. if a critter dies or a tower is upgraded.
     */
    	public void observerUpdate(){
		if(gamePaused ==false){
			//we want to reset the wave stats, and then  check each critter to see what happended
			resetPlayerWaveStats();
			boolean anyCrittersLeft = false;
			//go through subjects and see if we have a critter
			for(Subject s : subjects){
				if(s instanceof Critter){
					//it is a critter. Check to see if it is dead or if it has reached the end.
					Critter c = (Critter) s;
					//if it is dead, give the appropriate money to the player
					if(c.isAlive() && c.hasReachedEnd()==false){
						anyCrittersLeft = true;
					}else if(c.isAlive()==false){ //if it is dead, we want to give the loot to user
						gamePlayer.addToMoney(c.getLoot()); 
					}else if(c.hasReachedEnd()==true){//if it has reached the end, take away a life
						gamePlayer.takeAwayALife();
						if(gamePlayer.getLives()==0){ //if no lives, end the game
							endGame();
						}
					}
				}
			}
			//if no critters are left, allow the player to start a new wave
			if(anyCrittersLeft == false){
				bStartWave.setEnabled(true);
			}
			//if it isn't Game Over, then update the information labels.
			if(!gameOver){
			updateInfoLabelText();
			updateSelectedTowerInfoAndButtons();
			}
		}
	}
    	
    /*
     * Ends the game by disabling buttons, and pausing the clock.
     */
	private void endGame(){
		gameOver = true;
		gamePaused =true;
		clock.pause();
		this.getControlPanel().setInfoLabelText("GAME OVER. You reached wave " + waveNumber + " with $" + gamePlayer.getMoney() + ".");
		disableAllGameButtons();
	}
	/*
	 * disables all of the game buttons
	 */
	private void disableAllGameButtons(){
		bStartWave.setEnabled(false);
		bPause.setEnabled(false);
		bUpgrade.setEnabled(false);
		bSell.setEnabled(false);
		cbStrategies.setEnabled(false);
	}
	/*
	 * resets the player's stats (so that a new game can be started with the same instance
	 */
	private void resetPlayerWaveStats() {
		gamePlayer.setLives(waveStartLives);
		gamePlayer.setMoney(waveStartMoney);
	}
	//spends a certain amount of money of the Player
	private void spendMoney(int amount){
		gamePlayer.addToMoney((-1)*amount);
		this.waveStartMoney -=amount;
		updateInfoLabelText();
	}
    /**
     *
     * @return
     */
    public GameControlPanel getControlPanel(){
		return controlPanel;
	}

    /**
     *
     * @return
     */
    public MapPanel getPlayPanel(){
		return gamePanel;
	}
    //updates the info text
	private void updateInfoLabelText(){
		this.getControlPanel().setInfoLabelText("| Lives = " + gamePlayer.getLives()+ ", Money = " + gamePlayer.getMoney()+ ", Wavenumber = " + waveNumber + " |");
	}
	//updates the tower info text
	private void updateTowerInfoText(){
		String text = "";
		if(selectedTower != null){ //we print the tower's tostring, and show the prices
			text += selectedTower.toString();
			bUpgrade.setText("Upgrade (" + selectedTower.getUpPrice() + ")");
			bSell.setText("Sell (" + selectedTower.getSellPrice() + ")");
		}else{
			text += "No tower selected";
		}
		this.getControlPanel().setTowerInfoLabelText(text);
	}
	

	//This method is called from the click handler when we get a click at a point

    /**
     *  This method selects an existing tower to upgrade it, or puts a new tower
     *  on the selected tile, of the desired type. Hence, react to left click
     * @param point
     */
    	public void reactToLeftClick(Point point){
		//first, get the point of the grid where we clicked.
		double XPixels = tdMap.getGridWidth()*tdMap.tileWidth_Pixel;
		double YPixels = tdMap.getGridHeight()*tdMap.tileHeight_Pixel;
		double xRatio = ((double)point.getX())/(XPixels);
		double yRatio = ((double)point.getY())/(YPixels);
		
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
			//check which tower we want to place, and only build it if we have enough money.
			if(selectedTowerToBuild.equalsIgnoreCase("Spread")){
				if(playerMoney >= Tower_SpreadShot.getBuyPrice()){
					towToBuild =new Tower_SpreadShot("Spread Tower", adjustedTowerPoint, crittersInWave);
					moneyToSpend = Tower_SpreadShot.getBuyPrice();
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("Fire")){
				if(playerMoney >= Tower_Fire.getBuyPrice()){
					towToBuild = new Tower_Fire("Fire Tower", adjustedTowerPoint, crittersInWave);
					moneyToSpend = Tower_Fire.getBuyPrice();
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("IceBeam")){
				if(playerMoney >= Tower_IceBeam.getBuyPrice()){
					towToBuild = new Tower_IceBeam("Ice Tower", adjustedTowerPoint, crittersInWave);
					moneyToSpend = Tower_IceBeam.getBuyPrice();
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("Laser")){
				if(playerMoney >= Tower_Laser.getBuyPrice()){
					towToBuild = new Tower_Laser("Laser Tower", adjustedTowerPoint, crittersInWave);
					moneyToSpend = Tower_Laser.getBuyPrice();
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("None")){
				//no tower = don't do anything.
			}else{
				System.out.println("Error: No appropriate tower type (coding error)");
			}
			//if we have a tower to build (that isn't null), build it
			if(towToBuild!= null){
				//spend the money, build the tower, and put it on the tile
				spendMoney(moneyToSpend);
				buildTower(towToBuild);
				tileAtClick.setTowerOnTile(towToBuild);
			}
		}else{ //if there is a tower on this block
			//if our currently selected tower is not nothing, then deselect.
			if(selectedTower != null){ selectedTower.setSelected(false);}
			//now select the new tower
			selectedTower = tileAtClick.getTowerOnTile();
			selectedTower.setSelected(true);
			//update the information
			updateSelectedTowerInfoAndButtons();
			selectedTile = tileAtClick;
		}
	}
	/*
	 * builds a tower t and puts it in the drawable entities to be drawn.
	 */
	private void buildTower(Tower t){
		towersOnMap.add(t);
		drawableEntities.add(t);
		this.updateInfoLabelText();
		Draw();
	}
	
	private void updateSelectedTowerInfoAndButtons(){
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
			bSell.setText("Sell");
			bUpgrade.setText("Upgrade");
		}
		updateTowerInfoText();
	}
	
	
    /**
     *
     * @param point
     */
    public void reactToMouseMove(Point point){
		//first, get the point of the grid where we clicked.
		double XPixels = tdMap.getGridWidth()*tdMap.tileWidth_Pixel;
		double YPixels = tdMap.getGridHeight()*tdMap.tileHeight_Pixel;
		double xRatio = ((double)point.getX())/(XPixels);
		double yRatio = ((double)point.getY())/(YPixels);
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
					towToPreview =new Tower_SpreadShot("Spread", adjustedTowerPoint, crittersInWave);
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("Fire")){
				if(playerMoney >= Tower_Fire.getBuyPrice()){
					towToPreview = new Tower_Fire("Fire", adjustedTowerPoint, crittersInWave);
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("IceBeam")){
				if(playerMoney >= Tower_IceBeam.getBuyPrice()){
					towToPreview = new Tower_IceBeam("IceBeam", adjustedTowerPoint, crittersInWave);
				}
			}else if(selectedTowerToBuild.equalsIgnoreCase("Laser")){
				if(playerMoney >= Tower_Laser.getBuyPrice()){
					towToPreview = new Tower_Laser("Laser", adjustedTowerPoint, crittersInWave);
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
			}
		}else{ //if there is a tower on this block
			//don't do anything if there is a tower on the block
		}
	}


	
    /**
     *
     * @param point
     */
    public void reactToRightClick(Point point) {
		this.bNone.doClick(); //a right click clears the current tower selection
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		//if our strategies combobox changed, we want to change the strategy of the selected tower
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
