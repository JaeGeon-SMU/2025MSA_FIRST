package domain.food.eatingOutFood;

import java.util.List;

import domain.Allergy;
import domain.EatingOutFood;

public class Kimbap extends EatingOutFood{
	
	public Kimbap(){
		super("kimbap", 
				411, 
				17, 
				List.of(Allergy.EGGS, Allergy.BEEF), 
				9000
				);
	}	

}