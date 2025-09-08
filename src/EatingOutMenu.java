import java.util.Scanner;

import domain.User;
import service.EatingOutService;
import service.UserService;

public class EatingOutMenu {
	private final EatingOutService eatingOutService;
    private final UserService userService;
    private final User user;
    private final Scanner sc;

    public EatingOutMenu(EatingOutService eatingOutService, UserService userService, User user, Scanner sc) {
        this.eatingOutService = eatingOutService;
        this.userService = userService;
        this.user = user;
        this.sc = sc;
    }
    
    
    //외식 메뉴 실행
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
            		//음식 추천 받기
    				eatingOutService.recommend();
                break;
            case 2:
            		//외식 하기
            		eatFood();
            		saveUser();
            		break;
            case 0:
                System.out.println("외식 메뉴를 닫습니다.");
                running = false;
                break;
            default:
                System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
    			}
    			
    		}	
    		
    }
    
    // 사용자 상태 저장
    private void saveUser() {
        userService.saveUser(user);
    }
    
    // 메뉴 화면 출력
    private void printMenu() {
        System.out.println("\n=== 외식 메뉴 ===");
        System.out.println("1. 음식 추천 받기");
        System.out.println("2. 외식 하기");
        System.out.println("0. 종료");
        System.out.print("선택: ");
    }
    
    // 외식 하기
    private void eatFood() {
        try {
            System.out.print("먹을 음식명 입력 : ");
            String foodName = sc.nextLine();
            eatingOutService.eatFood(foodName);
        } catch (NumberFormatException e) {
            System.out.println("숫자 형식이 잘못되었습니다. 다시 시도하세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
	

}
