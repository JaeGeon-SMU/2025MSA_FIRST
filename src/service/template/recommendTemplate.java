package service.template;

import java.util.List;

import domain.Food;
import domain.User;
import domain.enums.Allergy;

public abstract class recommendTemplate {

    /**
     * 템플릿 메서드: 단계 호출만 (로직 없음)
     * 1) checkAllergy(User)  -> 후보 리스트 반환
     * 2) scoring(User, List) -> 점수 계산 + 점수순 정렬 리스트 반환
     * 3) showRecommendations(List) -> 최종 출력
     */
    public final void foodRecommend(User user) {
        List<Food> filtered = checkAllergy(user);
        if (filtered == null || filtered.isEmpty()) {
            System.out.println("추천할 수 있는 음식이 없습니다.");
            return;
        }
        List<Food> sorted = scoring(user, filtered);
        if (sorted == null || sorted.isEmpty()) {
            System.out.println("추천할 수 있는 음식이 없습니다.");
            return;
        }
        showRecommendations(sorted);
    }

    // ===== 템플릿 단계용 추상 메서드 =====
    protected abstract List<Food> checkAllergy(User user);
    protected abstract List<Food> scoring(User user, List<Food> foods);
    protected abstract void showRecommendations(List<Food> sortedFoods);


    /*
     * 공통 알레르기 검사 함수
     * 해당 음식과 사용자의 알레르기 항목 중 겹치는 것이 있다면 true 반환
     */
    public boolean checkAllergy(User user, Food food) {
        List<Allergy> userAllergies = user.getAllergy();
        List<Allergy> foodAllergies = food.getAllergy();

        if (userAllergies == null || foodAllergies == null
            || userAllergies.isEmpty() || foodAllergies.isEmpty()) return false;

        for (Allergy allergy : foodAllergies) {
            if (userAllergies.contains(allergy)) {
                return true;
            }
        }
        return false;
    }
}
