package models;

import java.util.ArrayList;

/**
 *
 * 
 * 
 */
public abstract class Subject {
	ArrayList<IObserver> observers = new ArrayList<IObserver>();

    /**
     *
     * @param obsToAdd
     */
    public void addObs(IObserver obsToAdd){
		observers.add(obsToAdd);
	}

    /**
     *
     * @param obsToRemove
     * @return
     */
    public boolean removeObs(IObserver obsToRemove){
		return observers.remove(obsToRemove);
	}

    /**
     *
     */
    protected void notifyObs(){
		for(IObserver o : observers){
			o.observerUpdate();
		}
	}
}
