package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.HomeFood;
import domain.enums.Allergy;

public class MeatDumpling extends HomeFood {
	
	public MeatDumpling(){
		super("meatDumpling", 
				365, 
				17, 
				List.of(Allergy.WHEAT, Allergy.SOYBEAN, Allergy.PORK), 
				LocalDate.now().plusDays(30), 
				1);
	}

}
