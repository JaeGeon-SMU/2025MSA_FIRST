import java.util.Scanner;

import domain.User;
import service.ChatGptSummaryService;
import service.FridgeService;
import service.UserService;

public class MainAfterLoginMenu {
    private final User user;
    private final UserService userService;
    private final ChatGptSummaryService gptService;
    private final Scanner sc;
    private final FridgeService fridgeService;

    public MainAfterLoginMenu(User user, Scanner sc) {
        this.user = user;
        this.userService = new UserService();
        this.fridgeService = new FridgeService(user);
        this.gptService = new ChatGptSummaryService();
        this.sc = sc;
    }

    public void run() {
        // 오늘 기록 보장 (원하면 유지)
        user.ensureTodayInfo();
        userService.saveUser(user);

        while (true) {
            System.out.println();
            userService.checkWeeklyGoal(user);
            
            //시연할때만 추가
//            System.out.println(gptService.chatGptAsk("[ {명언 내용} - {인물} ] 형태로 실제 인물이 말한 명언 하나만 한국어로 적어, 난 지금 살 찌우려고 노력하는 중이야."));
            System.out.println("사용자 : " + user.getUserId());
            userService.checkDailyCalories(user);
            userService.checkDailyProtein(user);
            userService.checkDailyWater(user);
            System.out.println();
            System.out.println("==== 메인 메뉴 ====");
            System.out.println("1. 유저 정보 수정");
            System.out.println("2. 오늘 운동 칼로리 입력");
            System.out.println("3. 회원 정보 보기");
            System.out.println("4. 오늘 먹은 음식 보기");
            System.out.println("5. 냉장고 관리");
            System.out.println("6. 이번 달 통계 보기");
            System.out.println("7. AI 요약");
            System.out.println("8. 로그아웃 / 종료");       // ★ 번호 한 칸 뒤로
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
                    profile.run();
                    break;
                }
                case 2: {
                    inputExerciseCalories(); 
                    break;
                }
                case 3: {
                    userService.viewMemberInfo(user);
                    break;
                }
                case 4: { 
                    userService.viewDailyEatingFoodList(user);
                    break;
                }
                case 5: {
                	  FridgeMenu fridgeMenu = new FridgeMenu(fridgeService, userService, user, sc);
                    fridgeMenu.start();
                    break;
                }
                case 6: {
                    userService.checkMonthlyGoal(user);
                    break;
                }
                case 7: {
                	System.out.println("\t...요약중...");
                	System.out.println(gptService.askGptWithUserPrompt(user));
                	break;
                }
                case 8: {
                    System.out.println("로그아웃합니다. 안녕!");
                    return;
                }
                default: {
                    System.out.println("1~8 중에서 선택하세요.");
                    break;
                }
            }
        }
    }

    private void inputExerciseCalories() {
    	System.out.println("오늘의 운동 칼로리를 입력해주세요 (예: 300, 500, 1000)");
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

                // 직접 업데이트 + 저장 (Service 래퍼 안 씀)
                user.setExerciseCarlories(exerciseCalories);
                userService.saveUser(user);

                System.out.println("운동 칼로리 저장 완료! 현재 값: " + user.getExerciseCarlories());
                return;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
            }
        }
    }
}
