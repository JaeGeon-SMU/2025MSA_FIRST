package service;

import java.util.HashMap;
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
		
		//개수만큼 음식 추가
		for(int i=0; i<count; i++) {
			fridge.getFoodList().get(name).add(new HomeFood());
		}
				
	}
	
	/*
	 * 냉장고의 음식 목록을 보여주는 함수
	 */
	public void foodList() {
		
		System.out.println(fridge.getFoodList().toString());		
		
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
		
		System.out.println("음식을 먹었습니다.");
		checkFood(name);
		fridge.getFoodList().get(name).remove();
		
	}
	
	/*
	 * 음식 삭제 함수
	 * 음식을 삭제하면 해당 음식의 수량을 0으로 바꾼다.
	 */
	public void deleteFood(String name) {
		
		fridge.getFoodList().get(name).clear();
		
	}
	
	

}
