package strategy;


import java.util.ArrayList;

import entities.Critter;
import entities.Tower;

public interface IStrategy {
	public ArrayList<Critter> findTargets(Tower tower, ArrayList<Critter> g1);	
}
	