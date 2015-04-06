package strategies;


import java.util.ArrayList;

import models.Critter;
import models.Tower;

/**
 *
 * 
 * 
 */
public class Closest implements IStrategy {

    /**
     *
     * @param tower
     * @param g1
     * @return
     */
    @Override
	public Critter findTarget(Tower tower, ArrayList<Critter> g1) {
		Critter closest_enemy = null;
        double min_dist = 10000.0;
        for (int i = 0; i < g1.size(); i++) {
        	Critter enemy = g1.get(i); 			
            double dist = (tower.distanceToCritter(g1.get(i)));		     	 // The distance of this enemy to the Tower
            if (dist < min_dist)
            {
            min_dist = dist;
            closest_enemy=enemy;
            }
        }   	
       return closest_enemy;				 	 // Tower attacks this critter
	}
	
	public String toString(){
		return "Closest";
	}

}
