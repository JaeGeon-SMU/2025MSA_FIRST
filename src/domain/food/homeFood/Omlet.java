package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.Allergy;
import domain.HomeFood;

public class Omlet extends HomeFood{
	
	public Omlet() {
		super("omlet", 
				440, 
				16, 
				List.of(Allergy.EGGS, Allergy.MILK), 
				LocalDate.now().plusDays(30), 
				12);
	}

}