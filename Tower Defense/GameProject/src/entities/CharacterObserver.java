package entities;

public class CharacterObserver implements IObserver {
	
	Tower tower;
	
	CharacterObserver(Tower tower){
		this.tower = tower;
	}

	public void update(){
		
		//for(int i=0;i<tower.getInRangeC().size();i++){
			//this will print the entire list of critters in range every time that a new critter comes into the range.
		//System.out.println(tower.getInRangeC().get(i).getName()+" has come into range!\n");
		
		//}
	}
}
