import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.User;
import domain.dto.SignUpInfo;
import domain.enums.Allergy;
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
                    userService.notifyEmergencyDay(user);
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
        while (true) {
            try {
                System.out.print("Id 입력 : ");
                String newId = sc.nextLine().trim();

                System.out.print("password 입력 : ");
                String newPw = sc.nextLine();

                double currentWeight = askDouble("현재 체중(kg): ", 1, 300);
                double targetWeight = askDouble("목표 체중(kg): ", 1, 300);
                int targetProtein = askInt("목표 단백질(g): ", 1, 1000);
                int targetCalories = askInt("목표 칼로리(kcal): ", 1, 10000);
                int minMeal = askInt("최소 끼니 수: ", 1, 10);
                int age = askInt("출생년도: ", 1800, 2030);
                double height = askDouble("키(cm): ", 1, 300);
                int targetWater = askInt("목표 수분 섭취량(ml): ", 1, 10000);

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
                System.out.println("회원가입이 완료되었습니다.");
                return; // 성공 시 메서드 종료
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("다시 입력해주세요.");
            }
        }
    }
    private double askDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String in = sc.nextLine().trim();
            try {
                double n = Double.parseDouble(in);
                if (n < min || n > max) {
                    System.out.printf("%.1f~%.1f 중에서 입력하세요.%n", min, max);
                    continue;
                }
                return n;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }

    private User login() {
        System.out.print("Id 입력 : ");
        String userId = sc.nextLine();
        System.out.print("password 입력 : ");
        String password = sc.nextLine();

        User user = auth.login(userId, password);
        if (user == null) {
            if (auth.findById(userId) == null) {
                System.out.println("해당하는 유저가 없습니다.");
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
            }
        }
        return user;
    }
}
