package domain.food.homeFood;

import java.time.LocalDate;
import java.util.List;

import domain.Allergy;
import domain.HomeFood;

public class KimchiDumpling extends HomeFood{
	
	public KimchiDumpling() {
		super("kimchiDumpling", 
				325, 
				16, 
				List.of(Allergy.WHEAT, Allergy.SOYBEAN, Allergy.PORK), 
				LocalDate.now().plusDays(30), 
				3);
	}

}
