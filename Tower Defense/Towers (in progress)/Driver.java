package src;

public class Driver {

	public static void main(String[] args){
		
		SampleCritter a = new SampleCritter(1, 100, 1, 1, 15);
		SampleCritter b = new SampleCritter(2, 100, 1, 2, 30);
		SampleCritter c = new SampleCritter(3, 200, 2, 3, 5);
		SampleCritter d = new SampleCritter(4, 50,  2, 1, 10);
		
		Laser t = new Laser();
		IceBeam i = new IceBeam();
		Fire f = new Fire();
		
		//Upgrade Tower
		//You may remove one or more of the comments below to display attribute of the tower at different upgrade levels
		
		while(t.level<4){
			System.out.println("Attributes of "+t.name+ " of level " + t.level+ ":");
			System.out.println("Damage: "+ t.Damage);
			System.out.println("Range: "+ t.Range);
			//System.out.println("Upgrade Price: "+ t.upgradePrice);
			//System.out.println("Rate of Fire: "+ t.RateofFire);
			//System.out.println("Slow factor :"+ t.SlowFactor);
			System.out.println("");
		t.upgrade(); //tower has been upgraded
		}
		while(i.level<4){
			System.out.println("Attributes of "+i.name+ " of level " + i.level+ ":");
			System.out.println("Damage: "+ i.Damage);
			//System.out.println("Upgrade Price: "+ i.upgradePrice);
			//System.out.println("Range: "+ i.Range);
			//System.out.println("Rate of Fire: "+ i.RateofFire);
			//System.out.println("Slow factor :"+ i.SlowFactor);
			System.out.println("");
		i.upgrade(); //tower has been upgraded
		//System.out.println("Damage for level " + t.level+ " is " +t.Damage);
	}
		
		Laser strategy = new Laser();
		//strategy.Closest();
		
		
	}
}
