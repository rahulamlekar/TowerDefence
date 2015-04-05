package src;

public class Laser extends Tower{

	public Laser(){
	super();
	this.name= "Laser Tower";
	this.buyPrice=100;
	this.sellPrice=(buyPrice+upgradePrice)/2;
	this.Range=150;
	this.Damage=25;
	this.SlowFactor=0.0;
	this.RateofFire=2.0;
	this.level=1;
	this.special="None";
	this.X=0;
	this.Y=0;
	}
	
	public void upgrade()
	{
	switch(level){
	case 1: level=2;
			upgradePrice=300;
			Range=200;
			RateofFire=2.5;
			Damage=40;
			break;
	case 2: level=3;
			upgradePrice=400;
			Range=225;
			RateofFire=2.5;
			Damage=50;
			break;
	case 3: level=4;
			upgradePrice=500;
			Range=250;
			RateofFire=2.7;
			Damage=70;
			break;
	default: break;
			
	}
	}
		 int Closest(){
				
	            int closest_enemy = 0;
	            double min_dist = 1000;
	            int count = range(); 					 // The number of current enemy ships within range
	            for (int i = 0; i < count; i++) {
	                int enemy = list.get(i); 				 // The id of this enemy
	                double dist = Tdist();		     	 // The distance of this enemy to the Tower
	               // System.out.println(Tdist());
	                if (dist < min_dist)
	                {
	                min_dist = dist;
	                closest_enemy=enemy;
	                }
	            }
	            System.out.println(closest_enemy);
	           return(closest_enemy);				 	 // Tower attacks this critter
		}
}