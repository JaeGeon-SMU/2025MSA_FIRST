package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import domain.Food;
import domain.Fridge;
import domain.HomeFood;
import domain.User;

public class FridgeTestMain {

	public static void main(String[] args) {
		// 1. 사용자 및 냉장고 생성
		User user = new User("테스트유저", "1234", 100); 
		// 냉장고 객체 생성
        Fridge fridge = new Fridge(); 
        user.setFridge(fridge);

        FridgeService fridgeService = new FridgeService(user);

        // 2. 냉장고에 음식 추가
        System.out.println("--- 🍔 냉장고에 음식 추가 ---");
        // HomeFood 객체를 직접 생성하여 큐에 추가
        Queue<Food> milkQueue = new LinkedList<>();
        milkQueue.add(new HomeFood("우유", 120, 8, null, LocalDate.of(2025, 9, 10), 3));
        milkQueue.add(new HomeFood("우유", 120, 8, null, LocalDate.of(2025, 9, 15), 3));
        fridge.getFoodList().put("우유", milkQueue);

        Queue<Food> eggQueue = new LinkedList<>();
        eggQueue.add(new HomeFood("계란", 80, 6, null, LocalDate.of(2025, 9, 8), 5));
        eggQueue.add(new HomeFood("계란", 80, 6, null, LocalDate.of(2025, 9, 12), 5));
        fridge.getFoodList().put("계란", eggQueue);

        Queue<Food> yogurtQueue = new LinkedList<>();
        yogurtQueue.add(new HomeFood("요거트", 150, 5, null, LocalDate.of(2025, 9, 7), 2));
        fridge.getFoodList().put("요거트", yogurtQueue);

//        fridgeService.putFood("사과", 3); // HomeFood 생성자에 유통기한이 없으므로 null로 설정될 것입니다.

        // 3. 현재 냉장고 음식 목록 출력
        System.out.println("\n--- 📝 현재 냉장고 음식 목록 ---");
        fridgeService.foodList();

        // 4. 유통기한 임박 순으로 정렬
        System.out.println("\n--- ⏰ 유통기한 임박 순 정렬 ---");
        fridgeService.sortExpireDateFoodList();

        // 5. 음식 섭취 (가장 오래된 것부터 제거)
        System.out.println("\n--- 😋 요거트 섭취 ---");
        fridgeService.eatFood("요거트");
        System.out.println("\n--- 📝 섭취 후 냉장고 음식 목록 ---");
        fridgeService.foodList();
        
//        // 6. 음식 수량 경고 체크
//        System.out.println("\n--- ⚠️ 계란 수량 체크 ---");
//        fridgeService.checkFood("계란");
//        
//        // 7. 존재하지 않는 음식 섭취 시도
//        System.out.println("\n--- 🚨 없는 음식 섭취 시도 ---");
//        fridgeService.eatFood("피자");

	}

}
