package helpers;
import java.util.ArrayList;

import models.Critter;
import models.Critter_White;
import models.Critter_Cyan;
import models.Critter_Yellow;
import models.Critter_Pink;
import models.Critter_Red;
import models.Critter_Orange;
import models.TDMap;

/**
 *  This class generates a group of critters for a certain wave number.
 */

public class CritterGenerator extends Helper {
	//attributes
	private final static int BASECRITTERS = 10; //this is the base amount of critters
	private final static int MAXWAVE = 50; //THE maximum wave number
	//constructor
	//This is where we create the current WAVE.

    /**
     *
     * @param waveLevel
     * @param exampleMap
     * @return
     */
    	public static ArrayList<Critter> getGeneratedCritterWave(int waveLevel, TDMap exampleMap){
		ArrayList<Critter> critterGroup = new ArrayList<Critter>();
		//what to multiply the amount of critters by (so more critters come each wave)
		double critterMultiplier = 1.0 + 0.5*waveLevel/MAXWAVE;
		int amountOfCritters = (int) (5*waveLevel + critterMultiplier*BASECRITTERS);
		
		//Here is the information for how we generate critters:
		//IF it is a multiple of 5, we do a boss round, with boss (infinity) and grouped (shuriken) critters.
		//We take a base amount, called the StartingCritters. We  multiply this by the critterMultiplier (which 
		//increases as the level increases). Then we multiply this by something specific to how many critters
		//of each type we want to create (0.5 for infinity means we want to create half as many infinity).
		
		//for a level that is a multiple of 5:
		//BOSS level
		if(waveLevel % 5 ==0){
			for(int i = 0; i < 0.5*amountOfCritters; i ++){
				critterGroup.add(new Critter_Yellow(waveLevel, exampleMap));
				critterGroup.add(new Critter_Pink(waveLevel, exampleMap));
			}
		}
		 // We generate fast (Arrow) critters	
		 else if(waveLevel % 5 == 4){
			 for(int i = 0; i < amountOfCritters; i ++){
			critterGroup.add(new Critter_White(waveLevel, exampleMap));
			 }
			 
		// We generate Resistive (X) critters	
		}else if(waveLevel % 5 == 3){
			//We want to create the normal amount of resisitive and fast critters. (and the same amount of each)
			for(int i = 0; i < (amountOfCritters); i ++){
				critterGroup.add(new Critter_Orange(waveLevel, exampleMap));
				
			}
			
			// We generate strong (square) critters
		}else if(waveLevel % 5 == 2){
			for(int i = 0; i < amountOfCritters; i ++){
			critterGroup.add(new Critter_Red(waveLevel, exampleMap));
			}
			
		// We generate average (circle) critters	
		}else if(waveLevel % 5 == 1){
			//We want to create the normal amount of Circle critters
			for(int i = 0; i < amountOfCritters; i ++){
				critterGroup.add(new Critter_Cyan(waveLevel, exampleMap));
				
			}
		}
		//At this point, all of the critters will have been created and put into the list.
		
		//return this group to whatever class called it (for simplicity)
		return critterGroup;
	}

}
