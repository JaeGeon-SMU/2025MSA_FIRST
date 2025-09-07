package domain.food.eatingOutFood;

import java.util.List;

import domain.EatingOutFood;
import domain.enums.Allergy;

public class YukkeNigiriSushi extends EatingOutFood{
	
	public YukkeNigiriSushi(){
		super("yukkeNigiriSushi", 
				1200, 
				24, 
				List.of(Allergy.BEEF), 
				17000
				);
	}	

}