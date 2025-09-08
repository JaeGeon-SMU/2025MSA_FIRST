package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.HomeFood;
import domain.enums.Allergy;

public class Mackerel extends HomeFood{
	
	public Mackerel() {
		super("mackerel", 
				223, 
				13, 
				List.of(Allergy.MACKEREL), 
				LocalDate.now().plusDays(10), 
				3);
	}

}