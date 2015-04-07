package strategies;


import java.util.ArrayList;

import models.Critter;
import models.Tower;

/**
 *
 * 
 * 
 */
public class Fastest implements IStrategy {

    /**
     *
     * @param tower
     * @param g1
     * @return
     */
    @Override
	public Critter findTarget(Tower tower, ArrayList<Critter> g1) {
		//finds target that is fastest
		Critter fastest_enemy= null;   
        double max_speed = 0;
        int count = g1.size(); 			 		 // The number of critters currently within range
        for (int i = 0; i < count; i++) {
            Critter enemy = g1.get(i); 			
            double speed = (g1.get(i).getSpeed());		     	 // The speed of critter
            if (speed > max_speed)
            {
            max_speed = speed;
            fastest_enemy=enemy;
            }
        }
        
       return(fastest_enemy);				 	 // Tower attacks this critter
	
	}
	public String toString(){
		return "Fastest";
	}
}
