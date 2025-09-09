import java.util.Scanner;

import domain.User;
import service.FridgeService;
import service.UserService;

public class FridgeMenu {
    private final FridgeService fridgeService;
    private final UserService userService;
    private final User user;
    private final Scanner sc;

    public FridgeMenu(FridgeService fridgeService, UserService userService, User user, Scanner sc) {
        this.fridgeService = fridgeService;
        this.userService = userService;
        this.user = user;
        this.sc = sc;
    }

    // 냉장고 메뉴 실행
    public void start() {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = -1;
            String input = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
                continue;
            }

            switch (choice) {
                case 1:
                    putFood();       // 냉장고에 음식 넣기
                    saveUser();
                    break;
                case 2:
                    addWater();      // 냉장고에 물 넣기
                    saveUser();
                    break;
                case 3:
                    deleteFood();    // 냉장고에서 음식 빼기
                    saveUser();
                    break;
                case 4:
                    eatFood();       // 냉장고에서 음식 꺼내먹기
                    saveUser();
                    break;
                case 5:
                    eatWater();      // 냉장고에서 물 꺼내먹기
                    saveUser();
                    break;
                case 6:
                    fridgeService.foodList(); // 냉장고 음식 전체 확인
                    saveUser();
                    break;
                case 7:
                    fridgeService.sortExpireDateFoodList(); // 유통기한 임박 음식 확인
                    saveUser();
                    break;
                case 8:
                    fridgeService.sortProteinFoodList();    // 단백질 높은 음식 보기
                    saveUser();
                    break;
                case 9:
                    fridgeService.sortCalorieFoodList();    // 칼로리 높은 음식 보기
                    saveUser();
                    break;
                case 10:
                    setReorderPoint();  // 최소 수량 알림 설정
                    saveUser();
                    break;
                case 11:
                    fridgeService.foodRecommend(user);
                    saveUser();
                    break;
                case 12:
                		//냉장고 물 수량 확인하기
                		System.out.printf("냉장고 물 수량: %d\n", fridgeService.getWaterCnt());
                		break;
                case 0:
                    System.out.println("냉장고 문을 닫습니다.");
                    running = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }
        }
    }

    // 메뉴 화면 출력
    private void printMenu() {
        System.out.println("\n=== 냉장고 관리 메뉴 ===");
        System.out.println("1. 냉장고에 음식 넣기");
        System.out.println("2. 냉장고에 물 넣기");
        System.out.println("3. 냉장고에 음식 빼기");
        System.out.println("4. 냉장고에 음식 꺼내먹기");
        System.out.println("5. 냉장고에 물 꺼내먹기");
        System.out.println("6. 냉장고의 음식 모두 확인하기");
        System.out.println("7. 유통기한 임박 음식 보기");
        System.out.println("8. 단백질 높은 음식 보기");
        System.out.println("9. 칼로리 높은 음식 보기");
        System.out.println("10. 최소 수량 알림받는 기능 설정하기");
        System.out.println("11. 음식 추천 받기");
        System.out.println("12. 냉장고 물 수량 확인");
        System.out.println("0. 종료");
        System.out.print("선택: ");
    }

    // 사용자 상태 저장
    private void saveUser() {
        userService.saveUser(user);
    }

    // 음식 넣기
    private void putFood() {
        try {
            System.out.print("음식명 입력 : ");
            String foodName = sc.nextLine();
            System.out.print("개수 입력 : ");
            int foodCount = Integer.parseInt(sc.nextLine());
            fridgeService.putFood(foodName, foodCount);
        } catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // 물 넣기

    private void addWater() {
        while (true) {
            try {
                System.out.print("물 개수 입력 : ");
                String input = sc.nextLine();
                int waterCount = Integer.parseInt(input);
                if (waterCount <= 0) {
                    System.out.println("물 개수는 양수로 입력해야 합니다. 다시 시도하세요.");
                    continue; // 음수 또는 0을 입력하면 다시 루프의 시작으로
                }

                fridgeService.addWater(waterCount);
                System.out.println("냉장고에 물을 " + waterCount + "병 넣었습니다.");
                break; // 성공적으로 물을 추가하면 루프를 종료
            } catch (NumberFormatException e) {
                System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 음식 빼기
    private void deleteFood() {
        System.out.print("꺼낼 음식명 입력 : ");
        String foodName = sc.nextLine();
        System.out.print("개수 입력 : ");
        String countInput = sc.nextLine();

        try {
            int foodCount = Integer.parseInt(countInput);
            fridgeService.deleteFood(foodName, foodCount);
        } catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); 
        }
    }

    // 음식 꺼내먹기
    private void eatFood() {
        try {
            System.out.print("먹을 음식명 입력 : ");
            String foodName = sc.nextLine();
            fridgeService.eatFood(foodName);
        } catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // 물 꺼내먹기

    private void eatWater() {
        int waterCnt = fridgeService.getWaterCnt();

        if (waterCnt == 0) {
            System.out.println("냉장고에 물이 없습니다.");
            return; // 물이 없으므로 메서드를 즉시 종료
        }
        
        while (true) {
            try {
                System.out.print("꺼낼 물의 양 입력 (병) : ");
                int waterCount = Integer.parseInt(sc.nextLine());

                if (waterCount <= 0) {
                    System.out.println("0보다 큰 숫자를 입력하세요.");
                    continue;
                }

                if (waterCount > waterCnt) {
                    System.out.println("냉장고에 물이 부족합니다. 현재 " + waterCnt + "병 있습니다.");
                    continue;
                }

                fridgeService.spendWater(waterCount);
                System.out.println(waterCount + "병의 물을 꺼냈습니다.");
                break;

            } catch (NumberFormatException e) {
                System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 최소 수량 알림 설정
    private void setReorderPoint() {
        try {
            System.out.print("알림을 설정할 음식명 입력: ");
            String foodName = sc.nextLine();
            System.out.print("기준 수량 입력 (0 = 알림 해제): ");
            int reorderPoint = Integer.parseInt(sc.nextLine());
            fridgeService.setReorderPoint(foodName, reorderPoint);
        } catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 입력하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
