import java.util.Scanner;

import domain.User;
import service.UserService;

public class MainAfterLoginMenu {
    private final User user;
    private final UserService userService;
    private final Scanner sc;

    public MainAfterLoginMenu(User user, UserService userService, Scanner sc) {
        this.user = user;
        this.userService = userService;
        this.sc = sc;
    }

    public void run() {
        // 로그인 직후 알림 등
        userService.notifyEmergencyDay(user);

        while (true) {
            System.out.println();
            System.out.println("==== 메인 메뉴 ====");
            System.out.println("1. 유저 정보 수정");
            System.out.println("2. 오늘 운동 칼로리 입력");
            System.out.println("3. 냉장고 관리 (예정)");
            System.out.println("4. 통계 보기 (예정)");
            System.out.println("5. 로그아웃 / 종료");
            System.out.print("선택: ");

            String in = sc.nextLine().trim();
            int sel;
            try {
                sel = Integer.parseInt(in);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
                continue;
            }

            switch (sel) {
                case 1: {
                    UserProfileMenu profile = new UserProfileMenu(user, userService, sc);
                    profile.run(); // 돌아오면 메인 메뉴로 복귀
                    break;
                }
                case 2: {
                    inputExerciseCalories();
                    break;
                }
                case 3: {
                    System.out.println("냉장고 관리는 추후 구현 예정입니다.");
                    break;
                }
                case 4: {
                    System.out.println("통계 보기는 추후 구현 예정입니다.");
                    break;
                }
                case 5: {
                    System.out.println("로그아웃합니다. 안녕!");
                    return;
                }
                default: {
                    System.out.println("1~5 중에서 선택하세요.");
                    break;
                }
            }
        }
    }

    private void inputExerciseCalories() {
        // 현재 값 먼저 보여주기
        System.out.println("오늘의 운동 칼로리를 입력해주세요 (0 이상의 정수)");
        System.out.println("현재 운동 칼로리: " + user.getExerciseCarlories());

        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim();
            try {
                int exerciseCalories = Integer.parseInt(input);
                if (exerciseCalories < 0) {
                    System.out.println("0 이상의 정수를 입력하세요.");
                    continue;
                }

                user.setExerciseCarlories(exerciseCalories);
                userService.saveUser(user); // 저장

                System.out.println("운동 칼로리 저장 완료! 현재 값: " + user.getExerciseCarlories());
                return;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
            }
        }
    }
}