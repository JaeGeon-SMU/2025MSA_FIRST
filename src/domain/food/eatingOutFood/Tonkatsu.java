package domain.food.eatingOutFood;

import java.util.List;

import domain.EatingOutFood;
import domain.enums.Allergy;

public class Tonkatsu extends EatingOutFood{
	
	public Tonkatsu(){
		super("tonkatsu", 
				900, 
				49, 
				List.of(Allergy.PORK, Allergy.WHEAT), 
				14000
				);
	}	

}