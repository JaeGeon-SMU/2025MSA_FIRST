package domain;

import java.util.ArrayList;
import java.util.List;
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
			System.out.println();
			switch(num) {
				case 1:
					// 현제 체중
				    System.out.println("현재 체중(XX.XX형식)을 입력해 주세요: ");
				    while (true) {
				    	// 키보드 입력받기
				    	String inputLine = sc.nextLine();
				        try {
				            double weight = Double.parseDouble(inputLine); // 더블로 형변환
				            user.setCurrentWeight(weight); //현제 몸무게로 셋터를 통해 윗줄에서 변환한 값 넣는다
				            System.out.println("현제 체중이 성공적으로 저장되었습니다.");
				            break; 
				        } catch (NumberFormatException e) { //더블로 받지 못했을경우(숫자형식예외) 캐치를 통해 다시 입력받기
				            System.out.println("알맞은 형식이 아닙니다. 현재 체중(XX.XX형식)을 다시 입력해 주세요: ");
				        }
				    }
				    break;
				case 2: 
					System.out.println("현재 목표 체중(XX.XX형식)으로 입력해 주세요: ");
					while (true) {
						String inputLine = sc.nextLine();
						try {
							double weight = Double.parseDouble(inputLine);
							user.setTargetWeight(weight);
							System.out.println("목표 체중이 성공적으로 저장되었습니다.");
							break;
						} catch (NumberFormatException e) {
							System.out.println("알맞은 형식이 아닙니다. 목표 체중(XX.XX형식)을 다시 입력해주세요.");
						}
					}
					break;
				case 3: 
					System.out.println("목표 단백질 섭취량(소숫점 제외)을 입력해 주세요: ");
					while (true) {
						String inputLine = sc.nextLine();
						try {
							int protein = Integer.parseInt(inputLine);
							user.setTargetProtein(sc.nextInt(protein));
							System.out.println("목표 단백질 섭취량을 성공적으로 저장되었습니다.");
							break;
						} catch (NumberFormatException e) {
							System.out.println("알맞은 형식이 아닙니다 소숫접을 제외하고 입력을 다시 해주세요.");
						}
					}
					break;
				case 4: 
					System.out.println("목표 칼로리 섭취량(소숫점 제외)을 입력해 주세요: ");
					while (true) {
						String inputLine = sc.nextLine();
						try {
							int calories = Integer.parseInt(inputLine);
							user.setTargetCalories(calories);
							System.out.println("목표 칼로리가 성공적으로 저장되었습니다.");
							break;
						} catch (NumberFormatException e) {
							System.out.println("알맞은 형식이 아닙니다. 소숫점을 제외하고 다시 입력해 주세요");
						}
					}
					break;
				case 5: 
					System.out.println("최저 식사량(소숫점 제외) 입력해 주세요: ");
					while (true) {
						String inputLine =sc.nextLine();
						try {
							int minmeal = Integer.parseInt(inputLine);
							user.setMinMeal(minmeal);
							System.out.println("최저 식사량이 성공적으로 저장되었습니다.");
							break;
						} catch (NumberFormatException e) {
							System.out.println("알맞은 형식이 아닙니다. 소숫점을 제외하고 다시 입력해 주세요");
						}
					}
					break;
				case 6: 
					System.out.println("현제 신장(소숫점 제외)을 입력해 주세요: ");
					while (true) {
						String inputLine =sc.nextLine();
						try {
							int height = Integer.parseInt(inputLine);
							user.setHeight(height);
							System.out.println("현제 신장이 성공적으로 저장되었습니다.");	
							break;
						} catch (NumberFormatException e) {
							System.out.println("알맞은 형식이 아닙니다. 소숫점을 제외하고 다시 입력해 주세요");
						}
					}
					break;
				case 7: 
					System.out.println("마실 물 섭취량(소숫점 제외)을 입력해주세요: ");
					while (true) {
						String inputLine = sc.nextLine();
						try {
							int targetwater = Integer.parseInt(inputLine);
							user.setTargetWater(targetwater);
							System.out.println("마실 물 섭취량이 성공적으로 저장되었습니다.");		
							break;
						} catch (NumberFormatException e) {
							System.out.println("알맞은 형식이 아닙니다. 소숫점을 제외하고 다시 입력해 주세요");
						}
					}
					break;
				case 8: 
					while (true) {
					System.out.print("알레르기(쉼표로 구분, 예: EGGS,MILK / 없으면 엔터): ");
                    String allergyInput = sc.nextLine().trim();

                    List<Allergy> allergy = new ArrayList<>();
                    try {
                    	if (!allergyInput.isEmpty()) {
                            for (String t : allergyInput.split(",")) {
                                String key = t.trim().toUpperCase();
                                try {
                                    allergy.add(Allergy.valueOf(key));
                                } catch (IllegalArgumentException e) {
                                    System.out.println("알 수 없는 알레르기 무시: " + key);
                                }
                            }
                        }	
						break;
					} catch (NumberFormatException e) {
						System.out.println("알맞은 형식이 아닙니다. 소숫점을 제외하고 다시 입력해 주세요");
					}
                    
					break;	
					}
				case 9:
					System.out.println("출생년도를 입력해주세요 ex)2025 : ");
					while (true) {
						String inputLine = sc.nextLine();
						try {
							int birthyear = Integer.parseInt(inputLine);
							user.setBirthYear(birthyear);
							System.out.println("출생년도가 성공적으로 저장되었습니다.");
							break;
						} catch (NumberFormatException e) {
							System.out.println("알맞은 형식이 아닙니다. 소숫점 문자를 제외하고 다시 입력해 주세요");
						}
					}
					break;
				case 10:
					flag=false;
					System.out.println("이전 메뉴로 도아가기");
			}
		}
		while(flag);
		
	}
	
}
