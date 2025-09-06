package domain;

import java.time.LocalDate;
import java.util.List;

public class HomeFood extends Food {
	private LocalDate expireDate; //유통기한
	private int reorderPoint; //최소수량
	
	public HomeFood(String name) {
		this(name, 0, 0, null, null, 0);		
	}
	
	public HomeFood(String name, int calorie, int protein, List<Allergy> allergy, LocalDate expireDate, int reorderPoint) {
		super(name, calorie, protein, allergy);
		this.expireDate = expireDate;
		this.reorderPoint = reorderPoint;
	}
	
	
	public int getReorderPoint() {
		return reorderPoint;
	}
	
	
	@Override
	public String toString() {
		return super.toString() + ", exp: " + expireDate;
	}
	
}
