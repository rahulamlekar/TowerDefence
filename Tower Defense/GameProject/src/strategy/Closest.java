package strategy;

import java.util.ArrayList;

import entities.Critter;
import entities.Tower;

public class Closest implements IStrategy {

	@Override
	public Critter findTarget(Tower tower, ArrayList<Critter> g1) {
        Critter closest_enemy= null;   
        double min_dist = 10000.0;
        int count = g1.size(); 			 		 // The number of critters currently within range
        for (int i = 0; i < count; i++) {
            Critter enemy = g1.get(i); 			
            double dist = (tower.distanceToCritter(g1.get(i)));		     	 // The distance of this enemy to the Tower
            if (dist < min_dist)
            {
            min_dist = dist;
            closest_enemy=enemy;
            }
        }
       // System.out.println(closest_enemy);
       return(closest_enemy);				 	 // Tower attacks this critter

	}

}
