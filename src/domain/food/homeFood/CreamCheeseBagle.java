package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.HomeFood;
import domain.enums.Allergy;

public class CreamCheeseBagle extends HomeFood {
	
	public CreamCheeseBagle(){
		super("creamCheeseBagle", 
				370, 
				11, 
				List.of(Allergy.EGGS, Allergy.MILK, Allergy.SOYBEAN, 
						Allergy.WHEAT, Allergy.PORK, Allergy.BEEF, 
						Allergy.CHICKEN), 
				LocalDate.now().plusDays(30), 
				5);
	}

}