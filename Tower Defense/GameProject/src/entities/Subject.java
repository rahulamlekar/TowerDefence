package entities;

import java.util.ArrayList;

public abstract class Subject {
	ArrayList<IObserver> observers = new ArrayList<IObserver>();
	public void addObserver(IObserver obsToAdd){
		observers.add(obsToAdd);
	}
	public boolean removeObserver(IObserver obsToRemove){
		return observers.remove(obsToRemove);
	}
	protected void notifyObservers(){
		for(IObserver o : observers){
			o.update();
		}
	}
}
