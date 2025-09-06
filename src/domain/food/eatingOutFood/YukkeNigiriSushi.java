package domain.food.eatingOutFood;

import java.util.List;

import domain.Allergy;
import domain.EatingOutFood;

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