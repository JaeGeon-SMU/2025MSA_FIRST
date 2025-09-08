package domain.food.eatingOutFood;

import java.util.List;

import domain.EatingOutFood;
import domain.enums.Allergy;

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