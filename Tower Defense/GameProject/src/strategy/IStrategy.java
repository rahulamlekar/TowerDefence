package strategy;

import java.util.ArrayList;

import entities.Critter;
import entities.Tower;

public interface IStrategy {
	public Critter findTarget(Tower tower, ArrayList<Critter> g1);	
}
	