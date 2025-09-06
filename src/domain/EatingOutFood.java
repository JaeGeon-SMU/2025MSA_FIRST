package domain;

import java.time.LocalDate;
import java.util.List;

public class EatingOutFood extends Food {

	private int price; //가격

	
	public EatingOutFood(String name, int calorie, int protein, List<Allergy> allergy, LocalDate expireDate, int reorderPoint) {
		super(name, calorie, protein, allergy);
		this.price = price;
	}

}
