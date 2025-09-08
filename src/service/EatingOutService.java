package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import domain.EatingOutFood;
import domain.Food;
import domain.FoodFactory;
import domain.HomeFood;
import domain.User;

public class EatingOutService extends recommendTemplate{
	
	private User user;
	private FoodFactory foodFactory;
	private List<EatingOutFood> eatingOutFoodList = new ArrayList<>(); //외식 음식 리스트
	
	public EatingOutService(User user) {
		this.user = user;
		this.foodFactory = new FoodFactory();

		eatingOutFoodList.add(foodFactory.createEatingOutFood("flatfishNigiriSushi"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("friedChicken"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("kimbap"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("kimchistew"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("pizza"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("salmonNigiriSushi"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("shrimpWapperSet"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("tonkatsu"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("udon"));
		eatingOutFoodList.add(foodFactory.createEatingOutFood("yukkeNigiriSushi"));
	}
	
	/*
	 * 음식을 먹는 함수
	 * 음식 리스트에 있는 음식 중 하나를 먹을 수 있다.
	 */
	public void eatFood(String name){
		
		for(EatingOutFood eatingOutFood : eatingOutFoodList) {
			if(eatingOutFood.getName().equals(name)) {
				List<Food> list = user.getEatingHistory().get(LocalDate.now());
				if(list == null) {
					list = new ArrayList<Food>();
					user.getEatingHistory().put(LocalDate.now(), list);
				}
				list.add(foodFactory.createEatingOutFood(name));
				System.out.println(name+"을 먹었습니다.");
				return;
			} else continue;
		}
		//외식 음식 리스트에 없는 음식을 먹을 시 출력
		System.out.println("주문할 수 없는 음식입니다.");
	}
	
	
	/*
	 * 음식 추천 함수
	 * 알레르기, 칼로리, 단백질 등을 고려하여 해당하는 음식을 출력
	 * 단백질 높음, 칼로리 높음을 기준으로 상위 3개 추천
	 */
	@Override
	public void recommend() {
		int mealsPerDay = user.getMinMeal()>0 ? user.getMinMeal() : 3; //하루 끼니 수
		int mealCalories = user.getTargetCalories()/mealsPerDay; //한 끼 칼로리
		int mealProtein = user.getTargetProtein()/mealsPerDay; //한 끼 단백질
		
		List<EatingOutFood> foodCandidates = new ArrayList<>(); //음식 후보 리스트
		
		/*
		//알레르기 해당하는 음식 제외
		Iterator<EatingOutFood> it = eatingOutFoodList.iterator();
		while(it.hasNext()) {
			EatingOutFood eatingOutFood = it.next();
			if(checkAllergy(user, eatingOutFood)) {
				it.remove();
				continue;
			}			
		}
		*/
		
		for(EatingOutFood eatingOutFood : eatingOutFoodList) {
			//알레르기 해당하는 음식 제외
			if(checkAllergy(user, eatingOutFood)) continue;
			else if(eatingOutFood!=null) foodCandidates.add(eatingOutFood);
		}
		
		//후보가 없다면 종료
		if(foodCandidates.isEmpty()) {
			System.out.println("추천할 수 있는 음식이 없습니다.");
			return;
		}
		
		//점수 계산
		Map<EatingOutFood, Double> scoreMap = new HashMap<>();
		for(EatingOutFood eatingOutFood : foodCandidates) {
			double score = scoring(eatingOutFood, mealCalories, mealProtein);
			scoreMap.put(eatingOutFood, score);
			}
		
		//정렬
		//점수 내림차순
		Collections.sort(foodCandidates, new EatingOutFoodScoreComparator(scoreMap));
		
		//상위 후보 출력
		int limit = 3; //출력할 음식 후보 개수
		for(int i=0; i<Math.min(limit, foodCandidates.size()); i++) {
			EatingOutFood eatingOutFood = foodCandidates.get(i);
			System.out.println((i+1) + " " + eatingOutFood.toString());
		}
		
	}
	
	/*
	 * 음식 점수 계산 함수
	 * 단백질과 칼로리는 높을수록 가점
	 * 목표의 상한을 정하여 그 이상은 가점을 주지 않음
	 */
	@Override
	protected double scoring(Food food, int mealCalories, int mealProtein) {
		
		double score = 0.0;
		EatingOutFood eatingOutFood = (EatingOutFood)food;
		
		//칼로리 점수 계산
		int calTarget = (mealCalories>0) ? mealCalories : 1;
		double calRatio = eatingOutFood.getCalorie() / (double) calTarget;
		double calCapped = Math.min(calRatio, 2.0); //상한
		double calweight = 10.0; //가중치
		score += calweight*calCapped;
		
		//단백질 점수 계산
		int proTarget = (mealProtein>0) ? mealProtein : 1;
		double proRatio = eatingOutFood.getProtein() / (double) proTarget;
		double proCapped = Math.min(proRatio, 2.0); //상한
		double proweight = 20.0; //가중치
		score += proweight*proCapped;
		
		//최종 점수 반환
		return score;		
	}
	
	

}
