import java.util.Scanner;
import service.FridgeService;

public class FridgeMenu {
    private final FridgeService fridgeService;
    private final Scanner sc;
    
    public FridgeMenu(FridgeService fridgeService, Scanner sc) {
        this.fridgeService = fridgeService;
        this.sc = sc;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== 냉장고 관리 메뉴 ===");
            System.out.println("1. 냉장고에 음식 넣기");
            System.out.println("2. 냉장고에 물 넣기");
            System.out.println("3. 냉장고에 음식 빼기");
            System.out.println("4. 냉장고에 음식 꺼내먹기");
            System.out.println("5. 냉장고에 물 꺼내먹기");
            System.out.println("6. 냉장고의 음식 모두 확인하기");
            System.out.println("7. 유톻기한 임박 음식 보기");
            System.out.println("8. 단백질 높은 음식 보기");
            System.out.println("9. 칼로리 높은 음식 보기");
            System.out.println("10. 최소 수량 알림받는 기능 설정하기");
            System.out.println("11. 음식 추천 받기");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                	putfood();
                    break;
                case 2:
                    addWater();
                    break;
                case 3:
                	deleteFood();
                    break;
                case 4:
                	eatFood();
                    break;
                case 5:
                	eatWater();
                    break;
                case 6:
                	fridgeService.foodList();
                    break;
                case 7:
                	fridgeService.sortExpireDateFoodList();
                    break;
                case 8:
                	fridgeService.sortProteinFoodList();
                    break;
                case 9:
                	fridgeService.sortCalorieFoodList();
                    break;
                case 10:
                	setReorderPoint();
                    break;
                case 11:
                	// 미완
                    break;
                case 0:
                	System.out.println("냉장고 문을 닫습니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
            }
        }
        
        sc.close();
    }
    
    // case 1 냉장고에 음식 넣기
    private void putfood() {
    	try {
    		System.out.print("음식명 입력 : ");
            String foodName = sc.nextLine();
            
            System.out.print("개수 입력 : ");
            int foodCount = Integer.parseInt(sc.nextLine());
            
            fridgeService.putFood(foodName, foodCount);
            System.out.print("냉장고에 " + foodName + "을 " + foodCount + " 개 넣었습니다.");
    	
    	} catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // case 2 냉장고에 물 넣기
    private void addWater() {
    	try {
            System.out.print("물 개수 입력 : ");
            int waterCount = Integer.parseInt(sc.nextLine());
            
            fridgeService.addWater(waterCount);
            System.out.print("냉장고에 물을 " + waterCount + " ml 넣었습니다.");
    	
    	} catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // case 3 냉장고에 음식 빼기
    private void deleteFood() {
    	try {
    		System.out.print("꺼낼 음식명 입력 : ");
            String foodName = sc.nextLine();
            
            System.out.print("개수 입력 : ");
            int foodCount = Integer.parseInt(sc.nextLine());
            
            fridgeService.deleteFood(foodName, foodCount);
    	} catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // case 4 냉장고에 음식 꺼내먹기
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
    
    // case 5 냉장고에 물 꺼내먹기
    private void eatWater() {
    	try {
    		System.out.print("꺼낼 물의 양 입력 (ml) : ");
            int waterCount = Integer.parseInt(sc.nextLine());
            
            fridgeService.spendWater(waterCount);
    	} catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // case 10 최소 수량 알림 기능 설정하기
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
