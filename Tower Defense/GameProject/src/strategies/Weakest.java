package strategies;


import java.util.ArrayList;

import models.Critter;
import models.Tower;

/**
 *
 * 
 * 
 */
public class Weakest implements IStrategy {

    /**
     *
     * @param tower
     * @param g1
     * @return
     */
    @Override
	public Critter findTarget(Tower tower, ArrayList<Critter> g1) {
		//finds the weakest enemy
		Critter weakest_enemy= null;   
        double min_health = 10000.0; //arbitrary number
        int count = g1.size(); 			 		 // The number of critters currently within range
        for (int i = 0; i < count; i++) {
            Critter enemy = g1.get(i); 			
            double health = (g1.get(i).getHitPoints());	// The health of critter
            
            if (health < min_health)
            {
            min_health = health;
            weakest_enemy=enemy;
            }
        }
        
       // System.out.println(weakest_enemy);
       return(weakest_enemy);				 	 // Tower attacks this critter
		
	}
	public String toString(){
		return "Weakest";
	}

}