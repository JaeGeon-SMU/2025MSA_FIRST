package service.template;

import java.util.List;

import domain.Food;
import domain.User;
import domain.enums.Allergy;

public abstract class recommendTemplate {
	
	public void foodRecommend(User user, Food food, int mealCalories, int mealProtein) {
		recommend();
		checkAllergy(user, food);
		scoring(food, mealCalories, mealProtein);
	}
	
	//음식 추천 함수
	protected abstract void recommend(); 
	
	//음식 점수 계산 함수
	protected abstract double scoring(Food food, int mealCalories, int mealProtein); 
	
	/*
	 * 알레르기 검사 함수
	 * 해당 음식과 사용자의 알레르기 항목 중 겹치는 것이 있다면 true 반환
	 */
	public boolean checkAllergy(User user, Food food) {
		List<Allergy> userAllergies = user.getAllergy();
		List<Allergy> foodAllergies = food.getAllergy();
		
		if(userAllergies==null || foodAllergies==null || userAllergies.isEmpty() || foodAllergies.isEmpty()) return false;
		
		for (Allergy allergy : foodAllergies) {
	        if (userAllergies.contains(allergy)) {
	        	//겹치는 알레르기가 있다면 true 반환
	            return true; 
	        }
	    }
		//겹치는 알레르기가 없다면 false 반환
		return false;
	}
	
	
}
