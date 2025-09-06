package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

import domain.Food;
import domain.FoodFactory;
import domain.Fridge;
import domain.HomeFood;
import domain.User;

public class FridgeService {
	
	private User user;
	private Fridge fridge;
	private FoodFactory foodFactory;
	
	

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
			fridge.getFoodList().get(name).add(foodFactory.createHomeFood(name));
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
	 * 냉장고 음식의 목록을 유통기한이 임박한 순으로 정렬해서 리턴하는 함수
	 */
	public void sortExpireDateFoodList() {
	    // 냉장고에 음식이 없으면 메시지 출력
	    if (fridge.getFoodList().isEmpty()) {
	        System.out.println("냉장고가 비어 있습니다.");
	        return;
	    }

	    // 모든 Queue<Food> 꺼내서 List<HomeFood> 로 변환
	    List<HomeFood> allFoods = fridge.getFoodList().values().stream()
	            .flatMap(queue -> queue.stream().map(food -> (HomeFood) food))
	            .collect(java.util.stream.Collectors.toList());

	    // 퀵정렬로 expireDate 기준 정렬
	    List<HomeFood> sortedFoods = quickSort(allFoods);
	    
	    // 출력할 음식 개수 결정 (기본값 5)
	    int displayCount = sortedFoods.isEmpty() ? 5 : sortedFoods.get(0).getSortreorderPoint();

	    // 콘솔 출력
	    System.out.println("=== 유통기한 임박 순 정렬 결과 (상위 " + displayCount + "개) ===");
	    for (int i = 0; i < sortedFoods.size() && i < displayCount; i++) {
	        System.out.println(sortedFoods.get(i));
	    }
	}

	// 퀵정렬 구현
	private List<HomeFood> quickSort(List<HomeFood> foods) {
	    if (foods.size() <= 1) return foods;

	    HomeFood pivot = foods.get(foods.size() / 2);
	    LocalDate pivotDate = pivot.getExpireDate();

	    List<HomeFood> left = new ArrayList<>();
	    List<HomeFood> right = new ArrayList<>();
	    List<HomeFood> equal = new ArrayList<>();
	    List<HomeFood> noExpireDate = new ArrayList<>();

	    for (HomeFood f : foods) {
	        if (f.getExpireDate() == null) {
	            noExpireDate.add(f);
	        } else if (pivotDate == null) {
	            right.add(f);
	        } else if (f.getExpireDate().isBefore(pivotDate)) {
	            left.add(f);
	        } else if (f.getExpireDate().isAfter(pivotDate)) {
	            right.add(f);
	        } else {
	            equal.add(f);
	        }
	    }

	    List<HomeFood> result = new ArrayList<>();
	    result.addAll(quickSort(left));
	    result.addAll(equal);
	    result.addAll(quickSort(right));
	    result.addAll(noExpireDate);

	    return result;
	}
	
	/*
	 * 냉장고 음식의 목록을 칼로리가 높은순으로 정렬해서 리턴하는 함수
	 */
	public void sortProteinFoodList() {
	    // 냉장고에 음식이 없으면 메시지 출력
	    if (fridge.getFoodList().isEmpty()) {
	        System.out.println("냉장고가 비어 있습니다.");
	        return;
	    }

	    // 모든 Queue<Food> 꺼내서 List<HomeFood> 로 변환
	    List<HomeFood> allFoods = fridge.getFoodList().values().stream()
	            .flatMap(queue -> queue.stream().map(food -> (HomeFood) food))
	            .collect(java.util.stream.Collectors.toList());

	    // 퀵정렬로 expireDate 기준 정렬
	    List<HomeFood> sortedFoods = mergeSort(allFoods);
	    
	    // 출력할 음식 개수 결정 (기본값은 5이므로 5보다 작은 경우에는 그 값만큼 출력)
	    int displayCount = sortedFoods.isEmpty() ? 5 : sortedFoods.get(0).getSortreorderPoint();

	    // 콘솔 출력
	    System.out.println("=== 칼로리 높은 순 정렬 결과 (상위 " + displayCount + "개) ===");
	    for (int i = 0; i < sortedFoods.size() && i < displayCount; i++) {
	        System.out.println(sortedFoods.get(i));
	    }
	}
	
	// 병합 정렬 구현
	private List<HomeFood> mergeSort(List<HomeFood> foods) {
		if (foods.size() <= 1) return foods;
		
		int mid = foods.size() / 2;
		List<HomeFood> leftHalf = foods.subList(0, mid);
		List<HomeFood> rightHalf = foods.subList(mid, foods.size());
		

	    // 쪼갠 두 부분을 재귀적으로 정렬
	    List<HomeFood> sortedLeft = mergeSort(leftHalf);
	    List<HomeFood> sortedRight = mergeSort(rightHalf);

	    // 정렬된 두 부분을 합치기
	    return merge(sortedLeft, sortedRight);
	}

	// 병합을 담당하는 함수
	private List<HomeFood> merge(List<HomeFood> left, List<HomeFood> right) {
		List<HomeFood> mergedList = new ArrayList<>();
		// 두 리스트를 비교하기 위한 인덱스 변수
	    int leftIndex = 0;
	    int rightIndex = 0;

	    while (leftIndex < left.size() && rightIndex < right.size()) {
	        // 왼쪽과 오른쪽의 칼로리 값을 비교
	        if (left.get(leftIndex).getCalorie() >= right.get(rightIndex).getCalorie()) {
	            mergedList.add(left.get(leftIndex));
	            leftIndex++;
	        } else {
	            mergedList.add(right.get(rightIndex));
	            rightIndex++;
	        }
	    }

	    // 리스트에 남은 원소들을 모두 추가
	    while (leftIndex < left.size()) {
	        mergedList.add(left.get(leftIndex));
	        leftIndex++;
	    }
	    
	    while (rightIndex < right.size()) {
	        mergedList.add(right.get(rightIndex));
	        rightIndex++;
	    }

	    return mergedList;
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