package service;

import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.List;

import domain.DailyGoalInfo;
import domain.Food;
import domain.User;
import domain.enums.Allergy;
import repo.UserRepo;
import util.OutFormat;
import util.enums.Labels;

public class UserService {
    OutFormat of = new OutFormat();
    private final UserRepo userRepo = new UserRepo();

    //유저 객체 받아서 유저의 정보 출력
    public void viewMemberInfo(User user) {
        of.print(Labels.BIRTHYEAR.getValue(),               user.getBirthYear());
        of.print(Labels.HEIGHT.getValue(),                  String.format("%.1f cm", user.getHeight()));
        of.print(Labels.CURRENTWETIGHT.getValue(),          String.format("%.1f kg", user.getCurrentWeight()));
        of.print(Labels.TARGETWEIGHT.getValue(),            String.format("%.1f kg", user.getTargetWeight()));
        of.print(Labels.CURRENTWATTERINTAKE.getValue(),     user.ensureTodayInfo().getCurrentWater() + " ml");   // ← 오늘 물 섭취량
        of.print(Labels.TARGETWATER.getValue(),             user.getTargetWater() + " ml");
        of.print(Labels.TARGETPROTEIN.getValue(),           user.getTargetProtein() + " g");
        of.print(Labels.TARGETCALORIES.getValue(),          user.getTargetCalories() + " kcal");
        of.print(Labels.MINMEAL.getValue(),                 user.getMinMeal());
        of.print(Labels.CHECKEXERCISECALORIES.getValue(),   user.getExerciseCarlories() + " kcal");
        String allergies = (user.getAllergy() == null || user.getAllergy().isEmpty()) ? "없음" : user.getAllergy().toString();
        of.print(Labels.ALLERGIES.getValue(),               allergies);
    }

    //전날 최소 먹어야 하는 끼니보다 적게 먹은 경우 비상데이 알림
    public void notifyEmergencyDay(User user) {
        List<Food> yesterdayFoodList = user.getEatingHistory().getOrDefault(LocalDate.now().minusDays(1), null);
        DailyGoalInfo yesterdayGoal = user.getGoalHistory().getOrDefault(LocalDate.now().minusDays(1), null);
        if(yesterdayFoodList != null && yesterdayFoodList.size() < yesterdayGoal.getMinMeal()) {
            System.out.println("====오늘은 비상데이 입니다=====");
        }
    }
    /*
    //목표 칼로리 - 현재 섭취량(for문 합산 , List<Food>) 
    void checkDailyDiet(User user) {
        int eatedTotalCalories = 0;
        //List<Food> 형태 필요
        for(Food food : user.getEatList()) {
            eatedTotalCalories += food.calorie;
        }
        of.print(Labels.CHECKCALORIES.getValue() , (user.getTargetCalories()-eatedTotalCalories) );
    }
    //목표 단백질 - 현재 섭취량(for문 합산 , List<Food>)
    void checkDailyProtein(User user){
        int eatedTotalProtein = 0;
        for(Food food : user.getEatList()) {
            eatedTotalProtein += food.calorie;
        }
        of.print(Labels.TARGETPROTEIN.getValue() , (user.getTargetProtein()-eatedToalProtein );       
    }
    */

    // ====== 업데이트 메서드들 ======
    public void updateCurrentWeight(User user, double kg) {
        if (kg <= 0 || kg > 500) throw new IllegalArgumentException("체중 범위가 올바르지 않습니다.");
        user.setCurrentWeight(kg);
    }

    public void updateTargetWeight(User user, double kg) {
        if (kg <= 0 || kg > 500) throw new IllegalArgumentException("목표 체중 범위가 올바르지 않습니다.");
        user.setTargetWeight(kg);
    }

    public void updateTargetProtein(User user, int g) {
        if (g < 0 || g > 1000) throw new IllegalArgumentException("단백질(g) 범위가 올바르지 않습니다.");
        user.setTargetProtein(g);
    }

    public void updateTargetCalories(User user, int kcal) {
        if (kcal < 0 || kcal > 20000) throw new IllegalArgumentException("칼로리(kcal) 범위가 올바르지 않습니다.");
        user.setTargetCalories(kcal);
    }

    public void updateMinMeal(User user, int cnt) {
        if (cnt < 1 || cnt > 12) throw new IllegalArgumentException("최소 끼니 수는 1~12 사이여야 합니다.");
        user.setMinMeal(cnt);
    }

    public void updateHeight(User user, double cm) {
        if (cm < 50 || cm > 300) throw new IllegalArgumentException("키(cm) 범위가 올바르지 않습니다.");
        user.setHeight(cm);
    }

    public void updateTargetWater(User user, int ml) {
        if (ml < 0 || ml > 20000) throw new IllegalArgumentException("목표 수분 섭취량(ml) 범위가 올바르지 않습니다.");
        user.setTargetWater(ml);
        user.ensureTodayInfo().setTargetWater(ml);
    }

    public void updateAllergy(User user, List<Allergy> list) {
        user.setAllergy(list == null ? List.of() : List.copyOf(list));
    }

    public void updateBirthYear(User user, int year) {
        int now = Year.now().getValue();
        if (year < 1900 || year > now)
            throw new IllegalArgumentException("출생년도는 1900~" + now + " 사이여야 합니다.");
        user.setBirthYear(year);
    }

    // ====== 달력 부분 (원본 그대로) ======
    public void checkWeeklyGoal(User user) {
        // 오늘 기준으로 뒤 3일 앞 3일
        int startDay = LocalDate.now().getDayOfWeek().getValue();
//      일주일 목표
        LocalDate today = LocalDate.now();
        final int week = 7;
        System.out.printf("이번 주의 달력, 오늘은 %d월 %d일\n",today.getMonth().getValue() , today.getDayOfMonth());
        for(int i = 0 ; i < week ; i++) {
            //6일 전으로 해야지 하루 전 요일 시작, 그 다음부터 하나하나 더해가기
            switch(today.plusDays(i-6).getDayOfWeek().getValue()) {
                case 1:
                    System.out.print("월");
                    break;
                case 2:
                    System.out.print("화");
                    break;
                case 3:
                    System.out.print("수");
                    break;
                case 4:
                    System.out.print("목");
                    break;
                case 5:
                    System.out.print("금");
                    break;
                case 6:
                    System.out.print("토");
                    break;
                case 7:
                    System.out.print("일");
                    break;
            }
            System.out.print("\t");
        }
        System.out.println();
        //추가적으로 정보 읽어서 입력
        for(int i = 0 ; i < week ; i++) {
            // .getDayOfMonth LocalDate타입을 일로 int값 반환
            // 세 개 조건 합당할 시 별 찍어주기
            System.out.printf("%02d\t", today.plusDays(i-6).getDayOfMonth());
            // 조건부 마크 입력
            //if()
        }
    }

    public void checkMonthlyGoal(User user) {
        int lastDay = LocalDate.now().lengthOfMonth();
        // 해당 달의 시작 요일 얻기
        // 1 : 일요일 2 : 월요일 3: 화요일
        // 해당 달의 첫 번째 일. 의 요일 정보 얻기();
        int startDay = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1).getDayOfWeek().getValue();
        System.out.println(LocalDate.now().getYear() + "년" + LocalDate.now().getMonthValue() + "월 \n");
        System.out.println("일\t월\t화\t수\t목\t금\t토");
        int currentDay = 1;
        for(int i = 0; i <= 42 ; i++) {
            if(i < startDay) {
                System.out.print("\t");
            }else {
                System.out.printf("%02d\t", currentDay);
                currentDay++;
            }
            if(i%7 == 0 ) {
                System.out.println();
            }
            if(currentDay > lastDay) {
                break;
            }
        }
    }

    // ====== 저장 ======
    public void saveUser(User user) {
        userRepo.save(user);
    }
    
    // 운동 칼로리 업데이트
    public void updateExerciseCalories(User user, int kcal) {
        if (kcal < 0 || kcal > 100_000) { // 상한은 필요시 조정
            throw new IllegalArgumentException("운동 칼로리는 0 이상이어야 합니다.");
        }
        user.setExerciseCarlories(kcal);
        userRepo.save(user);
    }
}
