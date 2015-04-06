package strategies;


import java.util.ArrayList;

import models.Critter;
import models.Tower;

public interface IStrategy {
	public Critter findTarget(Tower tower, ArrayList<Critter> g1);	
}
	