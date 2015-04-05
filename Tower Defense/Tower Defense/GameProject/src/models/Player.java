package models;

public class Player {
	private static final int STARTINGLIVES = 10;
	private static final int STARTINGMONEY = 500;
	
	private int lives = STARTINGLIVES;
	private int money = STARTINGMONEY;
	private static Player playerInstance = new Player();
	
	private Player(){
		
	}
	
	public static Player getInstance(){
		return playerInstance;
	}
	public int getLives(){
		return this.lives;
	}
	public int getMoney(){
		return this.money;
	}
	public void setLives(int l){
		lives = l;
	}
	public void setMoney(int m){
		money = m;
	}
	public void addToMoney(int moneyToAdd){
		this.money += moneyToAdd;
	}
	public void takeAwayALife(){
		this.lives -=1; 
	}
	public static int getStartingLives(){
		return STARTINGLIVES;
	}
	public static int getStartingMoney(){
		return STARTINGMONEY;
	}
	public void resetStats(){
		lives = STARTINGLIVES;
		money = STARTINGMONEY;
	}
}
