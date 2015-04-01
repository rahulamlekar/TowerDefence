package strategies;


import java.util.ArrayList;

import models.Critter;
import models.Tower;

public class Fastest implements IStrategy {
	@Override
	public ArrayList<Critter> findTargets(Tower tower, ArrayList<Critter> g1) {
		ArrayList<Critter> fastest_enemies = new ArrayList<Critter>();
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
        
        if(fastest_enemy != null){
        	fastest_enemies.add(fastest_enemy);
        }
        //System.out.println(fastest_enemy);
       return(fastest_enemies);				 	 // Tower attacks this critter
	
	}
	public String toString(){
		return "Fastest";
	}
}
