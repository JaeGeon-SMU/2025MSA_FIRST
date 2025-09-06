package domain.food.eatingOutFood;

import java.util.List;

import domain.Allergy;
import domain.EatingOutFood;

public class Pizza extends EatingOutFood{
	
	public Pizza(){
		super("pizza", 
				770, 
				14, 
				List.of(Allergy.PORK, Allergy.BEEF), 
				12000
				);
	}	

}