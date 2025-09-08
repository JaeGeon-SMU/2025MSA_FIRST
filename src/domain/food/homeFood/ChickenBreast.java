package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.HomeFood;
import domain.enums.Allergy;

public class ChickenBreast extends HomeFood {
	
	public ChickenBreast(){
		super("chickenBreast", 
				115, 
				18, 
				List.of(Allergy.CHICKEN), 
				LocalDate.now().plusDays(30), 
				5);
	}

}