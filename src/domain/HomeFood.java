package domain;

import java.time.LocalDate;

public class HomeFood extends Food {
	private LocalDate expireDate; //유통기한
	private int reorderPoint; //최소수량
	
	
	public int getReorderPoint() {
		return reorderPoint;
	}
	
	
	
}
