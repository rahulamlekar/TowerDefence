package helpers;
import java.time.*;

/**
 *
 * 
 */
public class GameClock extends Helper {
	//dTime is the amount of time that occurs per tick. E.g. to pause game, dTime = 0
	private int dTime;
	
	//we only want one Clock, so we use singleton method.
	private static GameClock clock = new GameClock();
	
	//constructor (private for singleton)
	private GameClock(){
		dTime = 1; //default tick is 1 second
	}
	//returns the instance (OF WHICH THERE IS ONLY 1) of the clock

    /**
     *
     * @return
     */
    	public static GameClock getInstance(){
		return clock;
	}
	//getter and setter for deltaTime

    /**
     *
     * @return
     */
    	public int deltaTime(){
		return dTime;
	}
	
    /**
     *
     * @param dt
     */
    public void setDeltaTime(int dt){
		dTime = dt;
	}

    /**
     *
     */
    public void pause(){
		setDeltaTime(0); //we pause by setting deltaTime to 0
	}

    /**
     *
     */
    public void unPause(){
		setDeltaTime(1); //we unpause by setting deltaTime to 1.
	}
	
}
