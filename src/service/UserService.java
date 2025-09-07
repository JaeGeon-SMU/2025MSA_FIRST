package service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import domain.DailyGoalInfo;
import domain.Food;
import domain.Labels;
import domain.OutFormat;
import domain.User;



public class UserService {
	OutFormat of = new OutFormat();
	
	//유저 객체 받아서 유저의 water만 꺼내 씀
	public void viewMemberInfo(User user) {
		of.print(Labels.BIRTHYEAR.getValue() , user.getBirthYear());
		of.print(Labels.HEIGHT.getValue() , user.getHeight());
		of.print(Labels.CURRENTWETIGHT.getValue() , user.getTargetWeight());
		of.print(Labels.CURRENTWATTERINTAKE.getValue(), user.getGoalHistory().get(LocalDate.now()).getCurrentWater());
		of.print(Labels.TARGETWATER.getValue(), user.getTargetWater());
		of.print(Labels.TARGETPROTEIN.getValue(), user.getTargetProtein());
		of.print(Labels.TARGETCALORIES.getValue(), user.getTargetCalories());
	}
	//전날 푸드 list 갯수 체크해서 알람, user에 객체 없음
	public void notifyEmergencyDay(User user) {
		List<Food> yesterdayFoodList = user.getEatingHistory().getOrDefault(LocalDate.now().minusDays(1), null);
		DailyGoalInfo yesterdayGoal = user.getGoalHistory().getOrDefault(LocalDate.now().minusDays(1), null);
		if(yesterdayFoodList != null && yesterdayFoodList.size() <yesterdayGoal.getMinMeal()) {
			System.out.println("====오늘은 비상데이 입니다=====");
		}
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

	public void checkWeeklyGoal(User user) {
		// 오늘 기준으로 뒤 3일 앞 3일
		int startDay = LocalDate.now().getDayOfWeek().getValue();
//		일주일 목표
		LocalDate today = LocalDate.now();
		final int week = 7;
		System.out.printf("이번 주의 달력, 오늘은 %d월 %d일\n",today.getMonth().getValue() , today.getDayOfMonth());
		for(int i = 0 ; i < week ; i++) {
			//6일 전으로 해야지 하루 전 요일 시작, 그 다음부터 하나하나 더해가기
			switch(today.plusDays(i-6).getDayOfWeek().getValue()) {
				case 1:
					System.out.print("월");
					break;
				case 2:
					System.out.print("화");
					break;
				case 3:
					System.out.print("수");
					break;
				case 4:
					System.out.print("목");
					break;
				case 5:
					System.out.print("금");
					break;
				case 6:
					System.out.print("토");
					break;
				case 7:
					System.out.print("일");
					break;
			}
			System.out.print("\t");
		}
		System.out.println();
		//추가적으로 정보 읽어서 입력
		for(int i = 0 ; i < week ; i++) {
			// .getDayOfMonth LocalDate타입을 일로 int값 반환
			// 세 개 조건 합당할 시 별 찍어주기
			System.out.printf("%02d\t", today.plusDays(i-6).getDayOfMonth());
			// 조건부 마크 입력
			//if()
		}
	}
	public void checkMonthlyGoal(User user) {
		int lastDay = LocalDate.now().lengthOfMonth();
		// 해당 달의 시작 요일 얻기
		// 1 : 일요일 2 : 월요일 3: 화요일
		// 해당 달의 첫 번째 일. 의 요일 정보 얻기();
		int startDay = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1).getDayOfWeek().getValue();
		System.out.println(LocalDate.now().getYear() + "년" + LocalDate.now().getMonthValue() + "월 \n");
		System.out.println("일\t월\t화\t수\t목\t금\t토");
		int currentDay = 1;
		for(int i = 0; i <= 42 ; i++) {
			if(i < startDay) {
				System.out.print("\t");
			}else {
				System.out.printf("%02d\t", currentDay);
				currentDay++;
			}
			if(i%7 == 0 ) {
				System.out.println();
			}
			if(currentDay > lastDay) {
				break;
			}
		}
	}
}
