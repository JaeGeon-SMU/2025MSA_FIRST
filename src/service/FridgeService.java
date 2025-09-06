package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import domain.Food;
import domain.Fridge;
import domain.HomeFood;
import domain.User;

public class FridgeService {
	
	private User user;
	private Fridge fridge;
	
	

	public FridgeService(User user) {
		this.user = user;
		this.fridge = user.getFridge();
	}

	/*
	 * 냉장고에 음식을 넣는 함수
	 */
	public void putFood(String name, int count) {
		
		for(int i=0; i<count; i++) {
			//개수만큼 음식 추가
			fridge.getFoodList().get(name).add(new HomeFood(name));
		}
				
	}
	
	/*
	 * 냉장고의 음식 목록을 보여주는 함수
	 */
	public void foodList() {
		
		if(fridge.getFoodList().isEmpty()) {
			//냉장고에 음식이 없을 시 출력
			System.out.println("냉장고가 비어 있습니다.");
			return;
		}else {
			for(Map.Entry<String, Queue<Food>> foods : fridge.getFoodList().entrySet()) {
				System.out.println("- " + foods.getKey() + "\t 수량: " + foods.getValue().size() + "개 ");
	        }			
		}
		
	}
	
	/*
	 * 음식 수량 체크 함수
	 * 남은 음식 수량이 최소 필요 개수보다 낮을 시 경고를 출력
	 */
	public void checkFood(String name) {
		
		Queue<Food> queue = fridge.getFoodList().get(name);
		if(queue.size() < ((HomeFood)(queue.peek())).getReorderPoint()) {
			System.out.println("최소 수량 이하입니다.");
		}
		
	}
	
	/*
	 * 음식을 먹는 함수
	 * 음식을 먹었을 시 해당 음식의 수량을 감소시킨다.
	 */
	public void eatFood(String name){
		
		Queue<Food> queue = fridge.getFoodList().get(name);
		
		if(queue.isEmpty() || queue==null) {
			//냉장고에 없는 음식을 먹을 시 출력
			System.out.println("냉장고에 없는 음식입니다.");
			return;
		}
		
		System.out.println("음식을 먹었습니다.");
		checkFood(name);
		queue.remove();
		
	}
	
	/*
	 * 음식 삭제 함수
	 * 음식을 삭제하면 해당 음식의 수량을 0으로 바꾼다.
	 */
	public void deleteFood(String name, int count) {
		Queue<Food> queue = fridge.getFoodList().get(name);
		if(queue!=null) {
			for(int i=0; i<count; i++) {
				//개수만큼 음식 삭제
				queue.poll();
			}
		}
		
	}
	
	
	//클래스다이어그램에없는기능
	/*
	 * 물 추가 함수
	 */
	public void addWater(int count) {
        fridge.setWaterCnt(fridge.getWaterCnt() + count);
	}
	
	
	/*
	 * 음식 추천 함수
	 * 알레르기, 칼로리, 단백질 등을 고려하여 해당하는 음식을 출력
	 */
	public void recommend() {
		
		//알레르기 고려
		
		//칼로리 고려
		user.getTargetCalories();
		
		//단백질 고려
		user.getTargetProtein();
		
		//해당하는 음식이 두 개 이상일 시
		
		
		
		
	}
	
	

}
