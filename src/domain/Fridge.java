package domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Queue;

public class Fridge implements Serializable{
	
	private HashMap<String, Queue<Food>> foodList; //냉장고에 들어간 음식 목록
	private int waterCnt; //냉장고 물 개수
	
	
	public HashMap<String, Queue<Food>> getFoodList() {
		return this.foodList;
	}
	
	// 생성자 추가 (소희)
	public Fridge() {
		this.foodList = new HashMap<>();
		this.waterCnt = 0; // 초기 물 개수는 0으로 설정
	}
	
	public void setWaterCnt(int waterCnt) {
		this.waterCnt = waterCnt;
	}

	public int getWaterCnt() {
		return this.waterCnt;
	}
	
	
}
