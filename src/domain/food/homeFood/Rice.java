package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.HomeFood;
import domain.enums.Allergy;

public class Rice extends HomeFood{
	
	public Rice() {
		super("rice", 
				315, 
				5,
				null, 
				LocalDate.now().plusDays(30), 
				3);
	}

}
