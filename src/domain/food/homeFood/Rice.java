package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.Allergy;
import domain.HomeFood;

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
