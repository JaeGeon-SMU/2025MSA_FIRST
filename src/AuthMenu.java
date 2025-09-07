import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.Allergy;
import domain.User;
import domain.dto.SignUpInfo;
import service.AuthenticationService;
import service.UserService;

public class AuthMenu {
    private final AuthenticationService auth;
    private final UserService userService; // 로그인 성공 후 후처리용(알림 등)
    private final Scanner sc;

    public AuthMenu(AuthenticationService auth, UserService userService, Scanner sc) {
        this.auth = auth;
        this.userService = userService;
        this.sc = sc;
    }

    /** 로그인/회원가입 메뉴만 제공. 로그인 성공 시 User 반환, 종료 시 null 반환 */
    public User run() {
        while (true) {
            System.out.println("1. 회원가입  2. 로그인  3. 종료");
            int sel = askInt("선택: ", 1, 3);

            if (sel == 1) signUp();
            else if (sel == 2) {
                User user = login();
                if (user != null) {
                    System.out.println("로그인 성공");
                    userService.notifyEmergencyDay(user); // 필요 시 제거 가능
                    return user;
                } else {
                    System.out.println("로그인 실패");
                }
            } else { // 3. 종료
                return null;
            }
        }
    }

    private int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String in = sc.nextLine().trim();
            try {
                int n = Integer.parseInt(in);
                if (n < min || n > max) {
                    System.out.printf("%d~%d 중에서 선택하세요.%n", min, max);
                    continue;
                }
                return n;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }

    private void signUp() {
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

        System.out.print("알레르기(쉼표, 예: EGGS,MILK / 없으면 엔터): ");
        String allergyInput = sc.nextLine().trim();

        List<Allergy> allergy = new ArrayList<>();
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
                newId, newPw, currentWeight, targetWeight,
                targetProtein, targetCalories, minMeal, age, height,
                targetWater, allergy
        );
        auth.signUp(info);
    }

    private User login() {
        System.out.print("Id 입력 : ");
        String userId = sc.nextLine();
        System.out.print("password 입력 : ");
        String password = sc.nextLine();

        // 로그인 로직: AuthenticationService에서 null 반환 시 직접 구분
        User user = auth.login(userId, password);

        if (user == null) {
            // 여기서 왜 null인지 구체적으로 구분
            if (auth.findById(userId) == null) {
                // UserRepo 같은 데서 확인하는 findById 사용
                System.out.println("해당하는 유저가 없습니다.");
            } else {
                System.out.println("유저 정보가 일치하지 않습니다.");
            }
        }
        return user;
    }
}