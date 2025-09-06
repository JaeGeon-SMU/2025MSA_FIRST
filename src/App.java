import java.util.List;
import java.util.Scanner;

import domain.Allergy;
import domain.User;
import domain.dto.SignUpInfo;
import service.AuthenticationService;
import service.UserService;

public class App {

	public static void main(String[] args) {
		 AuthenticationService auth = new AuthenticationService();
		    UserService userService = new UserService();
		    Scanner sc = new Scanner(System.in);
		    User user = null;

		    while (user == null) {
		        System.out.println("1. 회원가입 2. 로그인 3. 종료");
		        System.out.print("선택: ");
		        int num = sc.nextInt();
		        sc.nextLine(); // 개행 제거

		        switch (num) {
		        case 1:  // 회원가입
		            System.out.print("Id 입력 : ");
		            String newId = sc.nextLine().trim();

		            System.out.print("password 입력 : ");
		            String newPw = sc.nextLine();

		            System.out.print("현재 체중(kg): ");
		            double currentWeight = Double.parseDouble(sc.nextLine());

		            System.out.print("목표 체중(kg): ");
		            double targetWeight = Double.parseDouble(sc.nextLine());

		            System.out.print("목표 단백질(g): ");
		            int targetProtein = Integer.parseInt(sc.nextLine());

		            System.out.print("목표 칼로리(kcal): ");
		            int targetCalories = Integer.parseInt(sc.nextLine());

		            System.out.print("최소 끼니 수: ");
		            int minMeal = Integer.parseInt(sc.nextLine());

		            System.out.print("나이: ");
		            int age = Integer.parseInt(sc.nextLine());

		            System.out.print("키(cm): ");
		            double height = Double.parseDouble(sc.nextLine());

		            System.out.print("목표 수분 섭취량(ml): ");
		            int targetWater = Integer.parseInt(sc.nextLine());

		            System.out.print("알레르기(쉼표로 구분, 예: EGGS,MILK / 없으면 엔터): ");
		            String allergyInput = sc.nextLine().trim();

		            List<Allergy> allergy = new java.util.ArrayList<>();
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

		            SignUpInfo info = new SignUpInfo(
		                    newId, newPw,
		                    currentWeight, targetWeight,
		                    targetProtein, targetCalories,
		                    minMeal, age, height,
		                    targetWater, allergy
		            );
		            auth.signUp(info);
		            break;
		            case 2: // 로그인
		                System.out.print("Id 입력 : ");
		                String userId = sc.nextLine();
		                System.out.print("password 입력 : ");
		                String password = sc.nextLine();
		                user = auth.login(userId, password);
		                if (user == null) System.out.println("로그인 실패");
		                break;
		            case 3:
		            	System.exit(0);
		            default:
		                System.out.println("다시 선택");
		        }
		    }

		    System.out.println("로그인 성공");
		    sc.close();
		
	}

}
