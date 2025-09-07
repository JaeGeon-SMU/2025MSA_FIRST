import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.User;
import domain.enums.Allergy;
import service.UserService;

public class UserProfileMenu {
    private final User user;
    private final Scanner sc;
    private final UserService userService; // null 가능

    public UserProfileMenu(User user, Scanner sc) {
        this(user, null, sc);
    }

    public UserProfileMenu(User user, UserService userService, Scanner sc) {
        this.user = user;
        this.userService = userService;
        this.sc = sc;
    }

    public void run() {
        while (true) {
            printMenu();
            int sel = readMenuSelection(1, 10);
            switch (sel) {
                case 1:
                    setCurrentWeight();
                    break;
                case 2:
                    setTargetWeight();
                    break;
                case 3:
                    setTargetProtein();
                    break;
                case 4:
                    setTargetCalories();
                    break;
                case 5:
                    setMinMeal();
                    break;
                case 6:
                    setHeight();
                    break;
                case 7:
                    setTargetWater();
                    break;
                case 8:
                    setAllergy();
                    break;
                case 9:
                    setBirthYear();
                    break;
                case 10:
                    if (userService != null) {
                        userService.saveUser(user);
                        System.out.println("변경사항을 저장했습니다.");
                    }
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("1~10 중에서 선택하세요.");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("==== 유저 프로필 수정 ====");
        System.out.printf("1. 현재 체중(kg)           [현재: %.2f]%n", user.getCurrentWeight());
        System.out.printf("2. 목표 체중(kg)           [현재: %.2f]%n", user.getTargetWeight());
        System.out.printf("3. 목표 단백질(g)          [현재: %d]%n", user.getTargetProtein());
        System.out.printf("4. 목표 칼로리(kcal)       [현재: %d]%n", user.getTargetCalories());
        System.out.printf("5. 최소 끼니 수            [현재: %d]%n", user.getMinMeal());
        System.out.printf("6. 키(cm)                  [현재: %.2f]%n", user.getHeight());
        System.out.printf("7. 목표 수분 섭취량(ml)    [현재: %d]%n", user.getTargetWater());
        System.out.printf("8. 알레르기                [현재: %s]%n",
                (user.getAllergy() == null || user.getAllergy().isEmpty()) ? "없음" : user.getAllergy().toString());
        System.out.printf("9. 출생년도                [현재: %d]%n", user.getBirthYear());
        System.out.println("10. 뒤로가기");
        System.out.print("선택: ");
    }

    private void setCurrentWeight() {
        double v = readDoubleLoop("현재 체중을 입력하세요 (예: 72.5): ");
        user.setCurrentWeight(v);
        System.out.println("현재 체중이 저장되었습니다.");
    }

    private void setTargetWeight() {
        double v = readDoubleLoop("목표 체중을 입력하세요 (예: 68.0): ");
        user.setTargetWeight(v);
        System.out.println("목표 체중이 저장되었습니다.");
    }

    private void setTargetProtein() {
        int v = readIntLoop("목표 단백질(g, 정수)을 입력하세요: ");
        user.setTargetProtein(v);
        System.out.println("목표 단백질이 저장되었습니다.");
    }

    private void setTargetCalories() {
        int v = readIntLoop("목표 칼로리(kcal, 정수)를 입력하세요: ");
        user.setTargetCalories(v);
        System.out.println("목표 칼로리가 저장되었습니다.");
    }

    private void setMinMeal() {
        int v = readIntLoop("최소 끼니 수(정수)를 입력하세요: ");
        user.setMinMeal(v);
        System.out.println("최소 끼니 수가 저장되었습니다.");
    }

    private void setHeight() {
        double v = readDoubleLoop("키(cm)를 입력하세요 (예: 175.3): ");
        user.setHeight(v);
        System.out.println("키가 저장되었습니다.");
    }

    private void setTargetWater() {
        int v = readIntLoop("목표 수분 섭취량(ml, 정수)을 입력하세요: ");
        user.setTargetWater(v);
        System.out.println("목표 수분 섭취량이 저장되었습니다.");
    }

    private void setAllergy() {
        while (true) {
            System.out.print("알레르기(쉼표로 구분, 예: EGGS,MILK / 없으면 엔터): ");
            String s = sc.nextLine().trim();

            List<Allergy> list = new ArrayList<Allergy>();
            if (!s.isEmpty()) {
                String[] parts = s.split(",");
                for (String p : parts) {
                    String key = p.trim().toUpperCase();
                    try {
                        list.add(Allergy.valueOf(key));
                    } catch (IllegalArgumentException e) {
                        System.out.println("알 수 없는 알레르기 무시: " + key);
                    }
                }
                if (list.isEmpty()) {
                    System.out.println("인식된 알레르기가 없습니다. 다시 입력해 주세요(또는 엔터로 없음).");
                    continue;
                }
            }
            user.setAllergy(list);
            System.out.println("알레르기 정보가 저장되었습니다.");
            return;
        }
    }

    private void setBirthYear() {
        int now = Year.now().getValue();
        while (true) {
            int y = readIntLoop("출생년도(예: 1995)를 입력하세요: ");
            if (y < 1900 || y > now) {
                System.out.printf("1900~%d 사이의 연도를 입력하세요.%n", now);
                continue;
            }
            user.setBirthYear(y);
            System.out.println("출생년도가 저장되었습니다.");
            return;
        }
    }

    // ---------- 입력 유틸 ----------

    private int readMenuSelection(int min, int max) {
        while (true) {
            String in = sc.nextLine().trim();
            try {
                int sel = Integer.parseInt(in);
                if (sel < min || sel > max) {
                    System.out.printf("%d~%d 중에서 선택하세요.%n", min, max);
                    System.out.print("선택: ");
                    continue;
                }
                return sel;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
                System.out.print("선택: ");
            }
        }
    }

    private int readIntLoop(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("정수를 입력하세요.");
            }
        }
    }

    private double readDoubleLoop(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("소수 또는 정수를 입력하세요.");
            }
        }
    }
}