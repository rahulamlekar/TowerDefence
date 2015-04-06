package models;

import java.util.ArrayList;

public abstract class Subject {
	ArrayList<IObserver> observers = new ArrayList<IObserver>();
	public void addObs(IObserver obsToAdd){
		observers.add(obsToAdd);
	}
	public boolean removeObs(IObserver obsToRemove){
		return observers.remove(obsToRemove);
	}
	protected void notifyObs(){
		for(IObserver o : observers){
			o.observerUpdate();
		}
	}
}
