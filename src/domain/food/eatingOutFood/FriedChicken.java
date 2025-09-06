package domain.food.eatingOutFood;

import java.time.LocalDate;
import java.util.List;

import domain.Allergy;
import domain.EatingOutFood;

public class FriedChicken extends EatingOutFood{
	
	public FriedChicken(){
		super("friedChicken", 
				900, 
				54, 
				List.of(Allergy.PEANUT, Allergy.CHICKEN), 
				12000
				);
	}	

}
