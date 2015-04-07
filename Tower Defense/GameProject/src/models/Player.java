package models;

/**
 *
 * 
 * 
 */
public class Player {
	//Final constants (changed for balancing game)
	private static final int STARTINGLIVES = 10;
	private static final int STARTINGMONEY = 400;
	//default the lives and money to the constants above
	private int lives = STARTINGLIVES;
	private int money = STARTINGMONEY;
	//and we apply singleton pattern to ensure only one player
	private static Player playerInstance = new Player();
	
	//private constructor (singleton)
	private Player(){
		
	}
	
    /**
     *
     * @return
     */
    public static Player getInstance(){
		return playerInstance;
	}

    /**
     *
     * @return
     */
    public int getLives(){
		return this.lives;
	}

    /**
     *
     * @return
     */
    public int getMoney(){
		return this.money;
	}

    /**
     *
     * @param l
     */
    public void setLives(int l){
		lives = l;
	}

    /**
     *
     * @param m
     */
    public void setMoney(int m){
		money = m;
	}

    /**
     *
     * @param moneyToAdd
     */
    public void addToMoney(int moneyToAdd){
		this.money += moneyToAdd;
	}

    /**
     *
     */
    public void takeAwayALife(){
		this.lives -=1; 
	}

    /**
     *
     * @return
     */
    public static int getStartingLives(){
		return STARTINGLIVES;
	}

    /**
     *
     * @return
     */
    public static int getStartingMoney(){
		return STARTINGMONEY;
	}

    /**
     *
     */
    public void resetStats(){
    	//reset the stats of the player
		lives = STARTINGLIVES;
		money = STARTINGMONEY;
	}
}
