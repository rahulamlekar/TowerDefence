package models;

import java.util.ArrayList;

public class Strategy {

	private Tower tower;
	private Strategy selected;
	
	Strategy(Tower tower){
		
		this.tower = tower;
	}
	
	//choose a strategy
	public void choose(Strategy Strat){
		
		selected = Strat;
	}
	/*
	public void applyStrategy(){
		
		int i = 0;
		ArrayList<Critter> critters = tower.getInRangeC();
		
		
		while(i<critters.size()){
			
			tower.setTarget(critters.get(i));
		
			if(critters.get(i).getHitPoints()>0){
			
				tower.shootTarget();
				//System.out.println(critters.get(i).getName()+ " " +critters.get(i).getHealth());
			
			}
			
			if(critters.get(i).getHitPoints()<=0){
				
				i++;
			}
			
		}
	}*/
}

