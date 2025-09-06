package domain;

import java.util.Scanner;

public class UserSwitch {
	User user;
	boolean flag=true;
	
	Scanner sc = new Scanner(System.in);
	
	UserSwitch(User user){
		this.user=user;
	}

	public void repeatSwitch(User user) {

		do {
			
			int num=Integer.parseInt(sc.nextLine());
			switch(num) {
				case 1:
					System.out.println("현제 체중을 입력해 주세요: ");
					user.setCurrentWeight(sc.nextDouble());
					break;
				case 2: 
					System.out.println("목표 체중을 입력해 주세요: ");
					user.setTargetWeight(sc.nextDouble());
					break;
				case 3: 
					System.out.println("목표 단백질 섭취량을 입력해 주세요: ");
					user.setTargetProtein(sc.nextInt());
					break;
				case 4: 
					System.out.println("목표 칼로리 섭취량을 입력해 주세요: ");
					user.setTargetCalories(sc.nextInt());
					break;
				case 5: 
					System.out.println("최저 식사량 입력해 주세요: ");
					user.setMinMeal(sc.nextInt());
					break;
				case 6: 
					System.out.println("현제 신장을 입력해 주세요: ");
					user.setHeight(sc.nextInt());
					break;
				case 7: 
					System.out.println("마실 물 섭취량을 입력해주세요: ");
					user.setTargetWater(sc.nextInt());
					break;
				case 8: 
					System.out.println("알러지 종류를 입력해주세요: ");
//					user.setAllergy(sc. );
					break;
			}
		}
		while(flag);
		
	}
	
}
