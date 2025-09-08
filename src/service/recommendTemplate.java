package service;

import domain.Food;
import domain.User;

public abstract class recommendTemplate {
	
	public void foodRecommend(User user, Food food, int mealCalories, int mealProtein) {
		recommend();
		checkAllergy(user, food);
		scoring(food, mealCalories, mealProtein);
	}
	protected abstract void recommend(); //음식 추천 함수
	protected abstract boolean checkAllergy(User user, Food food); //알레르기 검사 함수
	protected abstract void scoring(Food food, int mealCalories, int mealProtein); //음식 점수 계산 함수
}
