package strategies;


import java.util.ArrayList;

import models.Critter;
import models.Tower;

/**
 *
 * @author Yash Gupta
 */
public interface IStrategy {
	
    /**
     *
     * @param tower
     * @param g1
     * @return
     */
    public Critter findTarget(Tower tower, ArrayList<Critter> g1);	
}
	