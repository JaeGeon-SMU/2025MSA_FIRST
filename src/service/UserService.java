package service;


import domain.Food;
import domain.Labels;
import domain.OutFormat;
import domain.User;



public class UserService {
	OutFormat of = new OutFormat();
	
	//user 현재 물양 필요
	void checkWaterIntake(User user){
//		of.print(Labels.CURRENTWATTERINTAKE, user.getTargetWater()); 
	}
	//유저 객체 받아서 유저의 water만 꺼내 씀
	void viewMemberInfo(User user) {
		of.print(Labels.AGE.getValue() , user.getAge());
		of.print(Labels.HEIGHT.getValue() , user.getHeight());
		of.print(Labels.CURRENTWETIGHT.getValue() , user.getTargetWeight());
//		of.print(Labels.CURRENTWATTERINTAKE.getValue() , user.getCurrentWater());
		of.print(Labels.TARGETWATER.getValue(), user.getTargetWater());
		of.print(Labels.TARGETPROTEIN.getValue(), user.getTargetProtein());
		of.print(Labels.TARGETCALORIES.getValue(), user.getTargetCalories());
	}
	//전날 푸드 list 갯수 체크해서 알람, user에 객체 없음
	void notifyEmergencyDay(User user) {
		
	}
	/*
	//목표 칼로리 - 현재 섭취량(for문 합산 , List<Food>) 
	void checkDailyDiet(User user) {
		int eatedTotalCalories = 0;
		//List<Food> 형태 필요
		for(Food food : user.getEatList()) {
			eatedTotalCalories += food.calorie;
		}
		of.print(Labels.CHECKCALORIES.getValue() , (user.getTargetCalories()-eatedTotalCalories) );
	}
	//목표 단백질 - 현재 섭취량(for문 합산 , List<Food>)
	void checkDailyProtein(User user){
		int eatedTotalProtein = 0;
		for(Food food : user.getEatList()) {
			eatedTotalProtein += food.calorie;
		}
		of.print(Labels.TARGETPROTEIN.getValue() , (user.getTargetProtein()-eatedToalProtein );		
	}
	*/
	
	
	
	
	//통계쪽, 후 개발 필요, 파일 입출력 고민
	void checkWeeklyGoal(User user) {
		
	}
	void checkMonthlyGoal(User user) {
		
	}
}
