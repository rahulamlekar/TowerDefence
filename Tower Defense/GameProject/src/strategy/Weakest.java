package strategy;


import java.util.ArrayList;

import entities.Critter;
import entities.Tower;

public class Weakest implements IStrategy {

	@Override
	public ArrayList<Critter> findTargets(Tower tower, ArrayList<Critter> g1) {
		ArrayList<Critter> weakest_enemies = new ArrayList<Critter>();
		
		Critter weakest_enemy= null;   
        double min_health = 10000.0;
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
        if(weakest_enemy!= null){
        	weakest_enemies.add(weakest_enemy);        	
        }
       // System.out.println(weakest_enemy);
       return(weakest_enemies);				 	 // Tower attacks this critter
		
	}
	public String toString(){
		return "Weakest";
	}

}