package strategies;


import java.util.ArrayList;

import models.Critter;
import models.Tower;

public interface IStrategy {
	public ArrayList<Critter> findTargets(Tower tower, ArrayList<Critter> g1);	
}
	