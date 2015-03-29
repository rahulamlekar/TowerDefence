package entities;

public class MapTile {
	private int tileValue;
	private Tower towerOnTile;
	
	public MapTile(){
		tileValue = 0;
		
	}
	public int getTileValue(){
		return tileValue;
	}
	public void setTileValue(int tv){
		tileValue = tv;
	}
	public Tower getTowerOnTile(){
		return towerOnTile;
	}
	public void setTowerOnTile(Tower tow){
		towerOnTile = tow;
	}
}
