package service;

import domain.User;

//formatting
enum labels{
	AGE("나이 : "),
	HEIGHT("현재 키 : "),
	CURRENTWETIGHT("현재 체중 : "),
	CURRENTWATTERINTAKE("현재 물 섭취량 : "),
	TARGETWATER("목표 물 섭취량 : "),
	TARGETWEIGHT("목표 체중 : "),
	TARGETPROTEIN("목표 단백질 섭취량 : "),
	TARGETCALORIES("목표 칼로리 섭취량 : "),
	CHECKCALORIES("오늘 목표로 해야 할 칼로리 섭취량 : "),
	CHECKPROTEIN("오늘 목표로 해야 할 단백질 섭취량 : ");
	
	
	private final String value;
   
	labels(String value){
		this.value = value;
	}
    public String getValue(){
        return value;
    }
	
}
class OutFormat{
	public void print(String labels, String context) {
		System.out.printf("%s %s\n",labels,context);
	}
	public void print(String labels, int context) {
		System.out.printf("%s %d\n",labels,context);
	}
	public void print(String labels, double context) {
		System.out.printf("%s %f\n",labels,context);
	}
}

public class UserService {
	OutFormat of = new OutFormat();
	
	//user 현재 물양 필요
	void checkWaterIntake(User user){
//		of.print(labels.CURRENTWATTERINTAKE, user.getTargetWater()); 
	}
	//유저 객체 받아서 유저의 water만 꺼내 씀
	void viewMemberInfo(User user) {
		of.print(labels.AGE.getValue() , user.getAge());
		of.print(labels.HEIGHT.getValue() , user.getHeight());
		of.print(labels.CURRENTWETIGHT.getValue() , user.getTargetWeight());
//		of.print(labels.CURRENTWATTERINTAKE.getValue() , user.getCurrentWater());
		of.print(labels.TARGETWATER.getValue(), user.getTargetWater());
		of.print(labels.TARGETPROTEIN.getValue(), user.getTargetProtein());
		of.print(labels.TARGETCALORIES.getValue(), user.getTargetCalories());
	}
	//전날 푸드 list 갯수 체크해서 알람, user에 객체 없음
	void notifyEmergencyDay(User user) {
	}
	
	//목표 칼로리 - 현재 섭취량(for문 합산 , List<Food>) 
	void checkDailyDiet(User user) {
		int eatedTotalCalories = 0;
		//List<Food> 형태 필요
		for(Food food : user.getEatList()) {
			eatedTotalCalories += food.calorie;
		}
		of.print(labels.CHECKCALORIES.getValue() , (user.getTargetCalories()-eatedTotalCalories) );
	}
	//목표 단백질 - 현재 섭취량(for문 합산 , List<Food>)
	void checkDailyProtein(User user){
		int eatedTotalProtein = 0;
		for(Food food : user.getEatList()) {
			eatedTotalProtein += food.calorie;
		}
		of.print(labels.TARGETPROTEIN.getValue() , (user.getTargetProtein()-eatedToalProtein );		
	}
	
	//통계쪽, 후개발
	void checkWeeklyGoal(User user) {
		
	}
	void checkMonthlyGoal(User user) {
		
	}
}
