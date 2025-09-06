package domain.food.eatingOutFood;

import java.util.List;

import domain.Allergy;
import domain.EatingOutFood;

public class Udon extends EatingOutFood{
	
	public Udon(){
		super("udon", 
				186, 
				9, 
				List.of(Allergy.WHEAT), 
				10000
				);
	}	

}
