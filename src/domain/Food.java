package domain;

import java.util.List;

public abstract class Food {
	int id; //음식 id
	String name; //음식 이름
	int calorie; //칼로리
	int protein; //단백질
	private List<Allergy> allergy; //알레르기 정보
	
	protected Food(String name) {
		this(name, 0, 0, null);		
	}
	
	protected Food(String name, int calorie, int protein) {
		this(name, calorie, protein, null);		
	}
	
	protected Food(String name, int calorie, int protein, List<Allergy> allergy) {
		this.name = name;
		this.calorie = calorie;
		this.protein = protein;
		this.allergy = allergy;		
	}
	
	public int getCalorie() {
		return this.calorie;
	}
	
	
	 @Override
	 public String toString() {
		 return "name: " + name + ", calorie: " + calorie + ", protein: " + protein;
	 }
	
	
}
