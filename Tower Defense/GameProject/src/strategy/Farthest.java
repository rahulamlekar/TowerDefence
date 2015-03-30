package strategy;


import java.util.ArrayList;

import entities.Critter;
import entities.Tower;

public class Farthest implements IStrategy {

	@Override
	public ArrayList<Critter> findTargets(Tower tower, ArrayList<Critter> g1) {
		ArrayList<Critter> farthest_enemies = new ArrayList<Critter>();
		Critter farthest_enemy= null;   
        double max_dist = 0;
        
        for (int i = 0; i < g1.size(); i++) {
            Critter enemy = g1.get(i); 			
            double dist = (g1.get(i).getIndexInPixelPath()); // The distance of this enemy along the path
            if (dist > max_dist)
            {
            max_dist = dist;
            farthest_enemy=enemy;
            }
        }
        
        if(farthest_enemy != null){
        	farthest_enemies.add(farthest_enemy);
        }
        //System.out.println(farthest_enemy);
       return(farthest_enemies);
	}

}
