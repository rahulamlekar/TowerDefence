package entities;
import java.util.ArrayList;
/*
 * generates a group of critters for a certain wave number.
 */
public class CritterGenerator {
	//attributes
	private final static int BASECRITTERS = 10; //this is the base amount of critters
	private final static int MAXWAVE = 50; //THE maximum wave number
	//constructor
	//This is where we create the current WAVE.
	public static ArrayList<Critter> getGeneratedCritterWave(int waveLevel, TDMap exampleMap){
		ArrayList<Critter> critterGroup = new ArrayList<Critter>();
		//what to multiply the amount of critters by (so more critters come each wave)
		double critterMultiplier = 1.0 + 1.0*waveLevel/MAXWAVE;
		int amountOfCritters = (int) (waveLevel + critterMultiplier*BASECRITTERS);
		
		//Here is the information for how we generate critters:
		//IF it is a multiple of 5, we do a boss round, with boss (infinity) and grouped (shuriken) critters.
		//We take a base amount, called the StartingCritters. We  multiply this by the critterMultiplier (which 
		//increases as the level increases). Then we multiply this by something specific to how many critters
		//of each type we want to create (0.5 for infinity means we want to create half as many infinity).
		
		//for a level that is a multiple of 5:
		if(waveLevel % 5 ==0){
			//We want to create half the amount of boss critters as regular critters, and 2 times the grouped.
			for(int i = 0; i < 0.5*amountOfCritters; i ++){
				critterGroup.add(new Critter_Infinity(waveLevel, exampleMap));
			}
			//Two times the grouped critters as normal
			for(int i = 0; i < 2*amountOfCritters; i ++){
				critterGroup.add(new Critter_Shuriken(waveLevel, exampleMap));
			}
		//If it is an EVEN wave level (not multiple of 5), we generate Resistive (X) and fast (Arrow) critters	
		}else if(waveLevel % 2 == 0){
			//We want to create the normal amount of resisitive and fast critters. (and the same amoount of each)
			for(int i = 0; i < (amountOfCritters); i ++){
				critterGroup.add(new Critter_X(waveLevel, exampleMap));
				critterGroup.add(new Critter_Arrow(waveLevel, exampleMap));
			}
		//If it is an ODD wave level (not multiple of 5), we generate average (circle) and strong (square) critters	
		}else if(waveLevel % 2 == 1){
			//We want to create the normal amount of Circle critters
			for(int i = 0; i < amountOfCritters; i ++){
				critterGroup.add(new Critter_Circle(waveLevel, exampleMap));
				critterGroup.add(new Critter_Square(waveLevel, exampleMap));
			}
		}
		//At this point, all of the critters will have been created and put into the list.
		
		//return this group to whatever class called it (for simplicity)
		return critterGroup;
	}
}
