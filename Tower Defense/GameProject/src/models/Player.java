package models;

/**
 *
 * 
 */
public class Player {
	private static final int STARTINGLIVES = 10;
	private static final int STARTINGMONEY = 500;
	
	private int lives = STARTINGLIVES;
	private int money = STARTINGMONEY;
	private static Player playerInstance = new Player();
	
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
		lives = STARTINGLIVES;
		money = STARTINGMONEY;
	}
}
