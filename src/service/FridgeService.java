package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

import domain.Food;
import domain.FoodFactory;
import domain.Fridge;
import domain.HomeFood;
import domain.User;
import domain.enums.Allergy;

public class FridgeService extends recommendTemplate{
	
	private User user;
	private Fridge fridge;
	private FoodFactory foodFactory;
	
	

	public FridgeService(User user) {
		this.user = user;
		this.fridge = user.getFridge();
		this.foodFactory = new FoodFactory();
	}


	/*
	 * 냉장고에 음식을 넣는 함수
	 */
	public void putFood(String name, int count) {
		Queue<Food> queue = fridge.getFoodList().get(name);
		
		if(queue==null) {
			queue = new LinkedList<>();
			fridge.getFoodList().put(name, queue);			
		}
		
		for(int i=0; i<count; i++) {
			HomeFood homeFood = foodFactory.createHomeFood(name);
			
			//개수만큼 음식 추가
			if(homeFood != null) queue.add(homeFood);
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
				System.out.println("- " + foods.getKey() + "  수량: " + foods.getValue().size() + "개 ");
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
	    
	 // 출력할 음식 개수 결정 (기본값은 5이므로 5보다 작은 경우에는 그 값만큼 출력)
	    int displayCount = sortedFoods.size() < 5 ? sortedFoods.size() : sortedFoods.get(0).getSortreorderPoint();

	    // 콘솔 출력
	    System.out.println("=== 유통기한 임박 순 정렬 결과 (상위 " + displayCount + "개) ===");
	    for (int i = 0; i < displayCount; i++) {
	        System.out.println(sortedFoods.get(i));
	    }
	}

	// 퀵정렬 구현
	private List<HomeFood> quickSort(List<HomeFood> foods) {
	    if (foods.size() <= 1) return foods;

	    // 리스트 중간 위치의 음식을 피벗으로 지정
	    HomeFood pivot = foods.get(foods.size() / 2);
	    LocalDate pivotDate = pivot.getExpireDate();

	    List<HomeFood> left = new ArrayList<>();         // 피벗보다 유통기한이 빠른
	    List<HomeFood> right = new ArrayList<>();        // 피벗보다 유통기한이 늦은
	    List<HomeFood> equal = new ArrayList<>();        // 피벗과 유통기한이 같은
	    List<HomeFood> noExpireDate = new ArrayList<>(); // 유통기한이 없는

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
	 * 냉장고 음식의 목록을 단백질이 높은순으로 정렬해서 리턴하는 함수
	 */
	public void sortProteinFoodList() {
	    // 냉장고에 음식이 없으면 메시지 출력
	    if (fridge.getFoodList().isEmpty()) {
	        System.out.println("냉장고가 비어 있습니다.");
	        return;
	    }

	    // 각 Queue의 맨 앞 음식 1개만 리스트에 담기
	    List<HomeFood> allFoods = fridge.getFoodList().values().stream()
	            .map(queue -> (HomeFood) queue.peek())  // 맨 앞 1개만
	            .filter(Objects::nonNull)               // 혹시 null 방지
	            .collect(Collectors.toList());

	    // 병합 정렬로 Protein 기준 정렬
	    List<HomeFood> sortedFoods = mergeSort(allFoods);
	    
	    // 출력할 음식 개수 결정 (기본값은 5이므로 5보다 작은 경우에는 그 값만큼 출력)
	    int displayCount = sortedFoods.size() < 5 ? sortedFoods.size() : sortedFoods.get(0).getSortreorderPoint();

	    // 콘솔 출력
	    System.out.println("=== 단백질 높은 순 정렬 결과 (상위 " + displayCount + "개) ===");
	    for (int i = 0; i < displayCount; i++) {
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
	        // 왼쪽과 오른쪽의 단백질 값을 비교
	        if (left.get(leftIndex).getProtein() >= right.get(rightIndex).getProtein()) {
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
	 * 냉장고 음식의 목록을 칼로리가 높은순으로 힙 정렬해서 리턴하는 함수
	 */
	public void sortCalorieFoodList() {
	    // 냉장고에 음식이 없으면 메시지 출력
	    if (fridge.getFoodList().isEmpty()) {
	        System.out.println("냉장고가 비어 있습니다.");
	        return;
	    }

	    // 각 Queue의 맨 앞 음식 1개만 리스트에 담기
	    List<HomeFood> allFoods = fridge.getFoodList().values().stream()
	            .map(queue -> (HomeFood) queue.peek())  // 맨 앞 1개만
	            .filter(Objects::nonNull)               // 혹시 null 방지
	            .collect(Collectors.toList());
	            
	    // 힙 정렬로 Calorie 기준 정렬
	    List<HomeFood> sortedFoods = heapSort(allFoods);
	    
	    // 출력할 음식 개수 결정 (기본값은 5이므로 5보다 작은 경우에는 그 값만큼 출력)
	    int displayCount = sortedFoods.size() < 5 ? sortedFoods.size() : sortedFoods.get(0).getSortreorderPoint();

	    // 콘솔 출력
	    System.out.println("=== 칼로리 높은 순 정렬 결과 (상위 " + displayCount + "개) ===");
	    for (int i = 0; i < displayCount; i++) {
	        System.out.println(sortedFoods.get(i));
	    }
	}
	
	// 힙 정렬 구현 (칼로리 기준, 오름차순)
	private List<HomeFood> heapSort(List<HomeFood> foods) {
	    int n = foods.size();

	    // 1. 최대 힙 만들기
	    for (int i = n / 2 - 1; i >= 0; i--) {
	        heapify(foods, n, i);
	    }

	    // 2. 루트(가장 큰 값)와 마지막 원소 교환 + 힙 크기 줄이기
	    for (int i = n - 1; i >= 1; i--) {
	        // 루트와 마지막 원소 교환
	        HomeFood temp = foods.get(0);
	        foods.set(0, foods.get(i));
	        foods.set(i, temp);

	        // 힙 속성 유지
	        heapify(foods, i, 0); // i는 힙 크기
	    }

	    return foods;
	}

	// 힙 속성을 유지하는 함수
	private void heapify(List<HomeFood> foods, int n, int i) {
	    int largest = i; // 루트를 가장 큰 값으로 가정
	    int left = 2 * i + 1; // 왼쪽 자식
	    int right = 2 * i + 2; // 오른쪽 자식

	    // 왼쪽 자식이 존재하고 루트보다 작으면
	    if (left < n && foods.get(left).getCalorie() < foods.get(largest).getCalorie()) {
	        largest = left;
	    }

	    // 오른쪽 자식이 존재하고 largest보다 작으면
	    if (right < n && foods.get(right).getCalorie() < foods.get(largest).getCalorie()) {
	        largest = right;
	    }

	    // largest가 루트와 다르면 swap 후 재귀적으로 heapify
	    if (largest != i) {
	        HomeFood swap = foods.get(i);
	        foods.set(i, foods.get(largest));
	        foods.set(largest, swap);

	        heapify(foods, n, largest);
	    }
	}
	
	
	/*
	 * 음식 수량 체크 함수
	 * 남은 음식 수량이 최소 필요 개수보다 낮을 시 경고를 출력
	 */
	public void checkFood(String name) {
	    Queue<Food> queue = fridge.getFoodList().get(name);

	    if (queue != null) {
	        HomeFood food = (HomeFood) queue.peek();
	        int currentCount = queue.size();
	        int reorderPoint = food.getReorderPoint();

	        if (currentCount <= reorderPoint) {
	            System.out.println(name + "의 남은 수량: " + currentCount 
	                + "개, 최소 수량: " + reorderPoint 
	                + "개 이하입니다. 추가 주문 잊지말고 해주세요 ~~!");
	        }
	    }
	}
	
	/*
	 * 음식을 먹는 함수
	 * 음식을 먹었을 시 해당 음식의 수량을 감소시킨다.
	 */
	public void eatFood(String name){
		
		Queue<Food> queue = fridge.getFoodList().get(name);
		
		if(queue==null || queue.isEmpty()) {
			//냉장고에 없는 음식을 먹을 시 출력
			System.out.println("냉장고에 없는 음식입니다.");
			return;
		}
		List<Food> list = user.getEatingHistory().get(LocalDate.now());
		if(list == null) {
			list = new ArrayList<Food>();
			user.getEatingHistory().put(LocalDate.now(), list);
		}
	
		System.out.println(name+"을 먹었습니다.");
		checkFood(name);
		Food remove = queue.remove();
		list.add(remove);
	}
	
	
	
	/*
	 * 음식 삭제 함수
	 * 음식을 삭제하면 해당 음식의 수량을 줄인다.
	 */
	public void deleteFood(String name, int count) {
		Queue<Food> queue = fridge.getFoodList().get(name);
		if(queue!=null && fridge.getFoodList().get(name).size() >= count) {
			for(int i=0; i<count; i++) {
				//개수만큼 음식 삭제
				queue.poll();
				System.out.println("냉장고에서 " + name + "을 " + count + "개 꺼냈습니다.");
			}
		}else if(queue==null) {
			System.out.println("냉장고에 " + name + "가 존재하지 않습니다.");
		}else {
			System.out.println(name + "의 수량이 " + count + "보다 적습니다.");
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
	 * 물 개수 확인 함수
	 */
	public int getWaterCnt() {
		//System.out.printf("냉장고 물 수량: %d\n", fridge.getWaterCnt());
		return fridge.getWaterCnt();
	}

	
	
	/*
	 * 음식 추천 함수
	 * 알레르기, 칼로리, 단백질 등을 고려하여 해당하는 음식을 출력
	 * 유통기한 임박, 단백질 높음, 칼로리 높음을 기준으로 상위 3개 추천
	 */
	@Override
	public void recommend() {
		
		LocalDate today = LocalDate.now(); //오늘 날짜
		int mealsPerDay = user.getMinMeal()>0 ? user.getMinMeal() : 3; //하루 끼니 수
		int mealCalories = user.getTargetCalories()/mealsPerDay; //한 끼 칼로리
		int mealProtein = user.getTargetProtein()/mealsPerDay; //한 끼 단백질
		
		List<HomeFood> foodCandidates = new ArrayList<>(); //음식 후보 리스트
		
		for(Map.Entry<String, Queue<Food>> foodEntry : fridge.getFoodList().entrySet()) {
			Queue<Food> foodQueue = foodEntry.getValue();
			if(foodQueue==null || foodQueue.isEmpty()) continue;
			
			HomeFood repFood = null; //대표 음식
			for(Food food : foodQueue) {
				HomeFood homeFood = (HomeFood)food; 
				
				//유통기한 지난 음식 제외
				LocalDate exp = homeFood.getExpireDate();
				if(exp!=null && exp.isBefore(LocalDate.now())) continue;
				
				//알레르기 해당하는 음식 제외
				if(checkAllergy(user, homeFood)) continue;
				
				repFood = homeFood;
				break;
			}
			if(repFood!=null) foodCandidates.add(repFood);			
			
		}		
		
		//후보가 없다면 종료
		if(foodCandidates.isEmpty()) {
			System.out.println("추천할 수 있는 음식이 없습니다.");
			return;
		}

		
		//점수 계산
		Map<HomeFood, Double> scoreMap = new HashMap<>();
		for(HomeFood homeFood : foodCandidates) {
			//double score = scoring(homeFood, today, mealCalories, mealProtein);
			double score = scoring(homeFood, mealCalories, mealProtein);
			scoreMap.put(homeFood, score);
		}		
		
		//정렬
		//점수 내림차순, 유통기한 오름차순
		Collections.sort(foodCandidates, new HomeFoodScoreComparator(scoreMap));
		
		//상위 후보 출력
		int limit = 3; //출력할 음식 후보 개수
		for(int i=0; i<Math.min(limit, foodCandidates.size()); i++) {
			HomeFood homeFood = foodCandidates.get(i);
			System.out.println((i+1) + " " + homeFood.toString());
		}
	}
	
	/*
	 * 알레르기 검사 함수
	 * 해당 음식과 사용자의 알레르기 항목 중 겹치는 것이 있다면 true 반환
	private boolean checkAllergy(User user, Food food) {
		List<Allergy> userAllergies = user.getAllergy();
		List<Allergy> foodAllergies = food.getAllergy();
		
		if(userAllergies==null || foodAllergies==null || userAllergies.isEmpty() || foodAllergies.isEmpty()) return false;
		
		for (Allergy allergy : foodAllergies) {
	        if (userAllergies.contains(allergy)) {
	        	//겹치는 알레르기가 있다면 true 반환
	            return true; 
	        }
	    }
		//겹치는 알레르기가 없다면 false 반환
		return false; 
	}
	 */
	
	/*
	 * 음식 점수 계산 함수
	 * 유통기한: 임박할수록 가점
	 * 단백질과 칼로리는 높을수록 가점
	 * 목표의 상한을 정하여 그 이상은 가점을 주지 않음
	 */
	@Override
	protected double scoring(Food food, int mealCalories, int mealProtein) {
		
		double score = 0.0;
		HomeFood homeFood = (HomeFood)food;		
		
		//유통기한 점수 계산
		long daysLeft = 1000;
		LocalDate exp = homeFood.getExpireDate();
		if(exp!=null) {
			daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), exp);
			if(daysLeft<0) daysLeft = 0;
		}
		score += 100.0/(daysLeft+1.0);
		
		//칼로리 점수 계산
		int calTarget = (mealCalories>0) ? mealCalories : 1;
		double calRatio = homeFood.getCalorie() / (double) calTarget;
		double calCapped = Math.min(calRatio, 2.0); //상한
		double calweight = 10.0; //가중치
		score += calweight*calCapped;
		
		//단백질 점수 계산
		int proTarget = (mealProtein>0) ? mealProtein : 1;
		double proRatio = homeFood.getProtein() / (double) proTarget;
		double proCapped = Math.min(proRatio, 2.0); //상한
		double proweight = 20.0; //가중치
		score += proweight*proCapped;
		
		//최종 점수 반환
		return score;		
	}
	
	/*
	 * 음식 점수 계산 함수
	 * 유통기한: 임박할수록 가점
	 * 단백질과 칼로리는 높을수록 가점
	 * 목표의 상한을 정하여 그 이상은 가점을 주지 않음
	 
	private double scoring(HomeFood homeFood, LocalDate today, int mealCalories, int mealProtein) {
		
		double score = 0.0;
		
		//유통기한 점수 계산
		long daysLeft = 1000;
		LocalDate exp = homeFood.getExpireDate();
		if(exp!=null) {
			daysLeft = ChronoUnit.DAYS.between(today, exp);
			if(daysLeft<0) daysLeft = 0;
		}
		score += 100.0/(daysLeft+1.0);
		
		//칼로리 점수 계산
		int calTarget = (mealCalories>0) ? mealCalories : 1;
		double calRatio = homeFood.getCalorie() / (double) calTarget;
		double calCapped = Math.min(calRatio, 2.0); //상한
		double calweight = 10.0; //가중치
		score += calweight*calCapped;
		
		//단백질 점수 계산
		int proTarget = (mealProtein>0) ? mealProtein : 1;
		double proRatio = homeFood.getProtein() / (double) proTarget;
		double proCapped = Math.min(proRatio, 2.0); //상한
		double proweight = 20.0; //가중치
		score += proweight*proCapped;
		
		//최종 점수 반환
		return score;		
	}
	*/
	
	/*
	 * 물 먹는 함수
	 */
	public void spendWater(int drinkWater) {
		int waterCnt = fridge.getWaterCnt();
		if(waterCnt >= drinkWater) {
			fridge.setWaterCnt(waterCnt-drinkWater);
			user.getGoalHistory().get(LocalDate.now()).addCurrentWater(500*drinkWater);
    			System.out.println("냉장고에서 물을 " + drinkWater + "병 꺼내 먹었습니다.");
		}else System.out.println("냉장고에 충분한 물이 없습니다.\n남은 물의 수량: " + waterCnt + "병");
	}
	
	/*
	 * 최소수량을 설정하는 함수
	 */
	public void setReorderPoint(String foodName, int reorderPoint) {
	    HomeFood food = findHomeFood(foodName);
	    if (food == null) {
	        throw new IllegalArgumentException(foodName + "을(를) 찾을 수 없습니다.");
	    }
	    food.setReorderPoint(reorderPoint);
	    if (reorderPoint > 0) {
	        System.out.println(foodName + "의 최소 수량 알림이 " + reorderPoint + "개로 설정되었습니다.");
	    } else {
	        System.out.println(foodName + "의 최소 수량 알림이 해제되었습니다.");
	    }
	}

	/*
	 *  최소수량 찾을 때 HomeFood 객체를 찾는 내부 헬퍼 메서드
	 */
	private HomeFood findHomeFood(String foodName) {
	    Queue<Food> foods = fridge.getFoodList().get(foodName);
	    if (foods == null || foods.isEmpty()) return null;

	    Food sample = foods.peek();
	    if (sample instanceof HomeFood) {
	        return (HomeFood) sample;
	    }
	    return null;
	}


}