package domain.food.eatingOutFood;

import java.util.List;

import domain.Allergy;
import domain.EatingOutFood;

public class ShrimpWapperSet extends EatingOutFood{
	
	public ShrimpWapperSet(){
		super("shrimpWapperSet", 
				1144, 
				37, 
				List.of(Allergy.EGGS, Allergy.MILK, Allergy.SOYBEAN,
						Allergy.WHEAT, Allergy.SHRIMP, Allergy.TOMATO, 
						Allergy.BEEF), 
				11000
				);
	}	

}
