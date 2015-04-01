package strategies;


import java.util.ArrayList;

import models.Critter;
import models.Tower;

public class Strongest implements IStrategy {
	@Override
	public ArrayList<Critter> findTargets(Tower tower, ArrayList<Critter> g1) {
		ArrayList<Critter> strongest_enemies = new ArrayList<Critter>();
		Critter strongest_enemy= null;   
        double max_health = 0;
        int count = g1.size(); 			 		 // The number of critters currently within range
        for (int i = 0; i < count; i++) {
            Critter enemy = g1.get(i); 			
            double health = (g1.get(i).getHitPoints());		     	 // The health of critter
            if (health > max_health)
            {
            max_health = health;
            strongest_enemy=enemy;
            }
        }
        
       //System.out.println(strongest_enemy);
        if(strongest_enemy != null){
        	strongest_enemies.add(strongest_enemy);        	
        }
       return(strongest_enemies);				 	 // Tower attacks this critter
		
	}
	public String toString(){
		return "Strongest";
	}
}
