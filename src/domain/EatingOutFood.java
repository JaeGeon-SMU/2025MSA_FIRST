package domain;

import java.time.LocalDate;
import java.util.List;

import domain.Food;

public class EatingOutFood extends Food {
	
	private int price;
	
	public EatingOutFood(String name) {
		this(name, 0, 0, null, 0);		
	}
	
	public EatingOutFood(String name, int calorie, int protein, List<Allergy> allergy, int price) {
		super(name, calorie, protein, allergy);
		this.price = price;
	}

	
	
}
