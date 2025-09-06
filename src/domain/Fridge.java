package domain;

import java.util.HashMap;
import java.util.Queue;

public class Fridge {

	private HashMap<String, Queue<Food>> foodList; //냉장고에 들어간 음식 목록
	private int waterCnt; //냉장고 물 개수
	
	
	public HashMap<String, Queue<Food>> getFoodList() {
		return foodList;
	}
	
	
	
	public void setWaterCnt(int waterCnt) {
		this.waterCnt = waterCnt;
	}

	public int getWaterCnt() {
		return waterCnt;
	}
	
	
}
