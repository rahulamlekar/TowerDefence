package helpers;
import java.time.*;
public class GameClock {
	//dTime is the amount of time that occurs per tick. E.g. to pause game, dTime = 0
	private int dTime;
	
	//we only want one Clock, so we use singleton method.
	private static GameClock c = new GameClock();
	
	//constructor (private for singleton)
	private GameClock(){
		dTime = 1; //default tick is 1 second
	}
	//returns the instance (OF WHICH THERE IS ONLY 1) of the clock
	public static GameClock getInstance(){
		return c;
	}
	//getter and setter for deltaTime
	public int deltaTime(){
		return dTime;
	}
	public void setDeltaTime(int dt){
		dTime = dt;
	}
	
}
