package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import domain.EatingOutFood;
import domain.Food;
import domain.User;
import domain.comparator.EatingOutFoodScoreComparator;
import domain.factory.FoodFactory;
import service.template.recommendTemplate;

public class EatingOutService extends recommendTemplate {

    private User user;
    private FoodFactory foodFactory;
    private List<EatingOutFood> eatingOutFoodList = new ArrayList<>(); // 외식 음식 리스트

    public EatingOutService(User user) {
        this.user = user;
        this.foodFactory = new FoodFactory();

        eatingOutFoodList.add(foodFactory.createEatingOutFood("flatfishNigiriSushi"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("friedChicken"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("kimbap"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("kimchistew"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("pizza"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("salmonNigiriSushi"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("shrimpWapperSet"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("tonkatsu"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("udon"));
        eatingOutFoodList.add(foodFactory.createEatingOutFood("yukkeNigiriSushi"));
    }

    /*
     * 음식을 먹는 함수
     * 음식 리스트에 있는 음식 중 하나를 먹을 수 있다.
     */
    public void eatFood(String name) {

        for (EatingOutFood eatingOutFood : eatingOutFoodList) {
            if (eatingOutFood.getName().equals(name)) {
                List<Food> list = user.getEatingHistory().get(LocalDate.now());
                if (list == null) {
                    list = new ArrayList<Food>();
                    user.getEatingHistory().put(LocalDate.now(), list);
                }
                list.add(foodFactory.createEatingOutFood(name));
                System.out.println(name + "을 먹었습니다.");
                return;
            } else continue;
        }
        // 외식 음식 리스트에 없는 음식을 먹을 시 출력
        System.out.println("주문할 수 없는 음식입니다.");
    }

    // ===== 템플릿 3단계 구현 =====

    // 1) 알레르기 필터링 리스트 반환 (외식은 만료 개념 없음)
    @Override
    protected List<Food> checkAllergy(User user) {
        List<Food> result = new ArrayList<Food>();
        for (int i = 0; i < eatingOutFoodList.size(); i++) {
            EatingOutFood f = eatingOutFoodList.get(i);
            if (f == null) continue;
            if (!checkAllergy(user, f)) {
                result.add(f);
            }
        }
        return result;
    }

    // 2) 스코어 계산 + 점수순 정렬 리스트 반환
    @Override
    protected List<Food> scoring(User user, List<Food> foods) {
        int mealsPerDay = user.getMinMeal() > 0 ? user.getMinMeal() : 3; // 하루 끼니 수
        int mealCalories = (user.getTargetCalories() + user.getExerciseCarlories()) / mealsPerDay; // 한 끼 칼로리
        int mealProtein = user.getTargetProtein() / mealsPerDay; // 한 끼 단백질

        Map<EatingOutFood, Double> scoreMap = new HashMap<EatingOutFood, Double>();
        List<EatingOutFood> list = new ArrayList<EatingOutFood>();

        for (int i = 0; i < foods.size(); i++) {
            EatingOutFood f = (EatingOutFood) foods.get(i);
            list.add(f);
            double score = scoring(f, mealCalories, mealProtein);
            scoreMap.put(f, score);
        }

        // 점수 내림차순
        Collections.sort(list, new EatingOutFoodScoreComparator(scoreMap));

        // List<Food>로 반환
        List<Food> out = new ArrayList<Food>();
        for (int i = 0; i < list.size(); i++) out.add(list.get(i));
        return out;
    }

    // 3) 추천 출력 (랜덤 3개 그대로)
    @Override
    protected void showRecommendations(List<Food> sortedFoods) {
        List<EatingOutFood> foodCandidates = new ArrayList<EatingOutFood>();
        for (int i = 0; i < sortedFoods.size(); i++) {
            foodCandidates.add((EatingOutFood) sortedFoods.get(i));
        }

        if (foodCandidates.isEmpty()) {
            System.out.println("추천할 수 있는 음식이 없습니다.");
            return;
        }

        int limit = 3; // 출력할 음식 후보 개수
        HashSet<Integer> foodNumList = new HashSet<Integer>();
        while (foodNumList.size() < Math.min(limit, foodCandidates.size())) {
            foodNumList.add((int) (Math.random() * foodCandidates.size()));
        }
        int cnt = 1;
        for (int foodNum : foodNumList) {
            EatingOutFood eatingOutFood = foodCandidates.get(foodNum);
            System.out.println((cnt++) + " " + eatingOutFood.toString());
        }
    }

    /*
     * 음식 점수 계산 함수
     * 단백질과 칼로리는 높을수록 가점
     * 목표의 상한을 정하여 그 이상은 가점을 주지 않음
     */
    @Override
    protected double scoring(Food food, int mealCalories, int mealProtein) {

        double score = 0.0;
        EatingOutFood eatingOutFood = (EatingOutFood) food;

        // 칼로리 점수 계산
        int calTarget = (mealCalories > 0) ? mealCalories : 1;
        double calRatio = eatingOutFood.getCalorie() / (double) calTarget;
        double calCapped = Math.min(calRatio, 2.0); // 상한
        double calweight = 10.0; // 가중치
        score += calweight * calCapped;

        // 단백질 점수 계산
        int proTarget = (mealProtein > 0) ? mealProtein : 1;
        double proRatio = eatingOutFood.getProtein() / (double) proTarget;
        double proCapped = Math.min(proRatio, 2.0); // 상한
        double proweight = 20.0; // 가중치
        score += proweight * proCapped;

        // 최종 점수 반환
        return score;
    }
}
