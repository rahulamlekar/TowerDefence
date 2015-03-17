package src;

import java.util.ArrayList;

abstract class Tower {
	// Attributes of each tower
	
	  int ID;
	  String name;
	  int buyPrice;
	  int sellPrice;
	  int Range;
	  double Damage;
	  double SlowFactor;
	  double RateofFire;
	  int level;
	  int upgradePrice;
	  String special;
	  String strategy;
	  int X;
	  int Y;
	 ArrayList<Integer> list = new ArrayList<Integer>(); // Used below for critters in Range
	
	 
		
	 
	public void upgrade()
	{
		// Overridden by each independent Tower type
		}
	
	
	public double Tdist() // Checks distance between critter and tower
	{
		return Math.sqrt(Math.pow(X-a.getX(),2)+Math.pow(Y-a.getY(),2));
	}
	
	public boolean inrange() //Checks if critter is in range
	{
		if(Range >= Tdist())		
		return true;
		else 
			return false;
	}
	
	public int range()   // Stores the ID of all critters in range and returns total number of critters in range
	{
		int i=0;
		if (inrange())
			list.add(a.getID());
		i++;
		//System.out.println(list.size());
		return list.size();
	}
	
	
	 int Closest(){
	
            int closest_enemy = 0;
            double min_dist = 1000;
            int count = range(); 					 // The number of current enemy ships within range
            for (int i = 0; i < count; i++) {
                int enemy = list.get(i); 			 // The id of this enemy
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
	
	int Farthest(){
	
            int farthest_enemy = 0;
            double max_dist = 0;
            int count = range();             		  // The number of current enemy ships within range
            for (int i = 0; i < count; i++) {	
                int enemy = list.get(i);        	  // The id of this enemy
                double dist = Tdist();		         // The distance to finish tile of this enemy
                if (dist > max_dist)
                {
                max_dist = dist;
                farthest_enemy=enemy;
                }
            }
            return(farthest_enemy);      			   // Tower attacks this critter
	}
	
	
	int Fastest(){
            int fastest_enemy = 0;
            int max_speed = 1;
            int count = range(); 					    // The number of current enemy ships within range
            for (int i = 0; i < count; i++) {
            	int enemy = list.get(i);				// The id of this enemy
                int speed = SampleCritter.getSpeed();   // speed of this enemy
                if (speed > max_speed)
                {
                max_speed = speed;
                fastest_enemy=enemy;
                 }
        		}		
            return(fastest_enemy); 					    // Tower attacks this critter
            }
	
	int Strongest(){
            int strongest_enemy = 0;
            int max_health = 1;
            int count = range(); 						// The number of current enemy ships within range
            for (int i = 0; i < count; i++) {
            	int enemy = list.get(i);			    // The id of this enemy
                int health = a.health;  				// health of this enemy  
                if (health > max_health)
                {
                max_health = health;
                strongest_enemy=enemy;
                }
              }
            return(strongest_enemy); 					// Tower attacks this critter
            }
            
    int Weakest(){
            int weakest_enemy = 0;
            int min_health = 1000;
            int count = range(); 						// The number of current enemy ships within range
            for (int i = 0; i < count; i++) {
            	int enemy = list.get(i);				// The id of this enemy
                int health = a.getHealth();             // health of this enemy  
                if (health < min_health)
                {
                min_health = health;
                weakest_enemy=enemy;
                }
              }
            return(weakest_enemy);						 // Tower attacks this critter
            }
}