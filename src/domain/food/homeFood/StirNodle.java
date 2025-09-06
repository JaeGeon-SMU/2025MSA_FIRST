package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.Allergy;
import domain.HomeFood;

public class StirNodle extends HomeFood{
	
	public StirNodle() {
		super("stirNodle", 
				540, 
				9, 
				List.of(Allergy.EGGS, Allergy.MILK, Allergy.SOYBEAN, 
						Allergy.WHEAT, Allergy.PORK, Allergy.BEEF, 
						Allergy.CHICKEN), 
				LocalDate.now().plusDays(100), 
				5);
	}

}