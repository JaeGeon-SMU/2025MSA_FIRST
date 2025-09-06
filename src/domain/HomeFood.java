package domain;

import java.time.LocalDate;
import java.util.List;

public class HomeFood extends Food {
	private LocalDate expireDate; //유통기한
	private int reorderPoint; //최소 재고수량
	private int sortreorderPoint = 5; // 정렬된 음식을 오름차순 몇개까지 출력할지 정하는 변수
	
	public HomeFood(String name, int calorie, int protein, List<Allergy> allergy, LocalDate expireDate, int reorderPoint) {
		super(name, calorie, protein, allergy);
		this.expireDate = expireDate;
		this.reorderPoint = reorderPoint;
	}
	
	
	public int getReorderPoint() {
		return this.reorderPoint;
	}
	
	public LocalDate getExpireDate() {
		return this.expireDate;
	}
	
	public int getSortreorderPoint() {
		return this.sortreorderPoint;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", exp: " + this.expireDate;
	}

	
}
