package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.HomeFood;
import domain.enums.Allergy;

public class BasilBagle extends HomeFood {
	
	public BasilBagle(){
		super("creamCheeseBagle", 
				395, 
				12, 
				List.of(Allergy.EGGS, Allergy.MILK, Allergy.SOYBEAN, 
						Allergy.WHEAT, Allergy.TOMATO), 
				LocalDate.now().plusDays(30), 
				5);
	}

}