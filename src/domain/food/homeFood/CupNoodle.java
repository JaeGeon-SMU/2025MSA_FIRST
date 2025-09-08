package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.HomeFood;
import domain.enums.Allergy;

public class CupNoodle extends HomeFood{
	
	public CupNoodle() {
		super("cupNoodle", 
				375, 
				7, 
				List.of(Allergy.EGGS, Allergy.MILK, Allergy.SOYBEAN, 
						Allergy.WHEAT, Allergy.PORK, Allergy.BEEF, 
						Allergy.CHICKEN), 
				LocalDate.now().plusDays(100), 
				5);
	}

}
