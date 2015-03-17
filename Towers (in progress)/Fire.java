package src;

public class Fire extends Tower {

	public Fire(){
		super();
		this.name= "Fire Tower";
		this.buyPrice=200;
		this.sellPrice=(buyPrice+upgradePrice)/2;
		this.Range=125;
		this.Damage=25;
		this.SlowFactor=0.2;
		this.RateofFire=1.0;
		this.level=1;
		this.special="fire";
		}
		
		public void upgrade()
		{
		switch(level){
		case 1: level=2;
				upgradePrice=400;
				Range=200;
				RateofFire=1.5;
				Damage=40;
				break;
		case 2: level=3;
				upgradePrice=800;
				Range=300;
				RateofFire=1.5;
				Damage=80;
				break;
		case 3: level=4;
				upgradePrice=1000;
				Range=400;
				RateofFire=2;
				Damage=1100;
				break;
		default: break;
				
		}
		}
		

	}
