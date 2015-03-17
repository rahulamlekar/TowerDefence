package src;

public class IceBeam extends Tower{
	
	public IceBeam(){
	super();
	this.name= "IceBeam Tower";
	this.buyPrice=150;
	this.sellPrice=(buyPrice+upgradePrice)/2;
	this.Range=200;
	this.Damage=12.5;
	this.SlowFactor=0.7;
	this.RateofFire=1.5;
	this.level=1;
	this.special="slowing";
	}
	
	public void upgrade()
	{
	switch(level){
	case 1: level=2;
			upgradePrice=350;
			Range=400;
			RateofFire=1.7;
			Damage=15;
			break;
	case 2: level=3;
			upgradePrice=450;
			Range=420;
			RateofFire=1.9;
			Damage=18;
			break;
	case 3: level=4;
			upgradePrice=600;
			Range=450;
			RateofFire=2;
			Damage=25;
			break;
	default: break;
			
	}
	}
	

}
