package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.HomeFood;
import domain.enums.Allergy;

public class ProteinBar extends HomeFood {
	
	public ProteinBar(){
		super("proteinBar", 
				249, 
				12, 
				List.of(Allergy.EGGS, Allergy.MILK, Allergy.SOYBEAN, 
						Allergy.WHEAT, Allergy.PORK, Allergy.BEEF, 
						Allergy.CHICKEN), 
				LocalDate.now().plusDays(80), 
				2);
	}

}