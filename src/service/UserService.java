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
        System.out.printf("\t이번 주의 달력,\t오늘은 %d월 %d일\n",today.getMonth().getValue() , today.getDayOfMonth());
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
        int todayCalories;
        int todayProtein;
        int targetCalories;
        int targetProtein;
        int currentWater;
        int targetWater;
        for(int i = 0 ; i < week ; i++) {
        	if(user.getGoalHistory().get(today.plusDays(i-6)) == null || user.getEatingHistory().get(today.plusDays(i-6)) == null ) {
        		System.out.printf("%02dXXX\t", today.plusDays(i-6).getDayOfMonth());
        		continue;
        	}
        	todayCalories = 0 ;
            todayProtein = 0 ;
            targetCalories = user.getGoalHistory().get(today.plusDays(i-6)).getTargetCalories() + user.getGoalHistory().get(today.plusDays(i-6)).getExerciseCarlories() ;
            targetProtein = user.getGoalHistory().get(today.plusDays(i-6)).getTargetProtein();
            currentWater = user.getGoalHistory().get(today.plusDays(i-6)).getCurrentWater();
            targetWater = user.getGoalHistory().get(today.plusDays(i-6)).getTargetWater() ;
            // .getDayOfMonth LocalDate타입을 일로 int값 반환
            // 세 개 조건 합당할 시 별 찍어주기
            if( (todayCalories - targetCalories) >= 0 && ( todayProtein - targetProtein ) >= 0 && ( currentWater - targetWater ) >= 0) {
            	System.out.print("*");
            }
            for(Food food : user.getEatingHistory().get(today.plusDays(i-6))) {            	
            	todayCalories += food.getCalorie();
            	todayProtein += food.getProtein();
            }
            
            //일 찍어주기
            System.out.printf("%02d", today.plusDays(i-6).getDayOfMonth());
            
            // 조건부 마크 입력
            // 칼로리
            if( ( todayCalories - targetCalories) >= 0) {
            	//O
            	System.out.print("O");
            }else {
            	//X
            	System.out.print("X");
            }
        	// 단백질
            if( ( todayProtein - targetProtein ) >= 0) {
            	//O
            	System.out.print("O");
            }else {
            	//X
            	System.out.print("X");
            }
        	// 물
            if( ( currentWater - targetWater ) >= 0) {
            	//O
            	System.out.print("O");
            }else {
            	//X
            	System.out.print("X");
            }
            System.out.print("\t");
        }
        System.out.println();
        System.out.println();
    }
    public void checkMonthlyGoal(User user) {
        int lastDay = LocalDate.now().lengthOfMonth();
        // 해당 달의 시작 요일 얻기
        // 1 : 일요일 2 : 월요일 3: 화요일
        // 해당 달의 첫 번째 일. 의 요일 정보 얻기();
        int startDay = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1).getDayOfWeek().getValue();
        System.out.println(LocalDate.now().getYear() + "년" + LocalDate.now().getMonthValue() + "월 \n");
        System.out.println("일\t월\t화\t수\t목\t금\t토");
        
        int todayCalories;
        int todayProtein;
        int targetCalories;
        int targetProtein;
        int currentWater;
        int targetWater;
        // 달력 1부터 출력하기위한 요소
        int current = 1;
        // 각 달성량을 위한 localdate 타입
        LocalDate currentday;
        for(int i = 0; i <= 42 ; i++) {
        	todayCalories = 0 ;
            todayProtein = 0 ;

            if(i < startDay+1) {
                System.out.print("\t");
            }else {
                currentday = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(), current);
            	if(user.getGoalHistory().get(currentday) == null || user.getEatingHistory().get(currentday) == null ) {
            		System.out.printf("%02dXXX\t", current);
            	}else {
            		targetCalories = user.getGoalHistory().get(currentday).getTargetCalories() + user.getGoalHistory().get(currentday).getExerciseCarlories();
                    targetProtein = user.getGoalHistory().get(currentday).getTargetProtein();
                    currentWater = user.getGoalHistory().get(currentday).getCurrentWater();
                    targetWater = user.getGoalHistory().get(currentday).getTargetWater() ;
                    
                    // .getDayOfMonth LocalDate타입을 일로 int값 반환
                    // 세 개 조건 합당할 시 별 찍어주기
                    if( (todayCalories - targetCalories) >= 0 && ( todayProtein - targetProtein ) >= 0 && ( currentWater - targetWater ) >= 0) {
                    	System.out.print("*");
                    }
                    for(Food food : user.getEatingHistory().get(currentday)) {            	
                    	todayCalories += food.getCalorie();
                    	todayProtein += food.getProtein();
                    }                
                    //일 찍어주기
                    System.out.printf("%02d", current);
                    
                    // 조건부 마크 입력
                    // 칼로리
                    if( ( todayCalories - targetCalories) >= 0) {
                    	//O
                    	System.out.print("O");
                    }else {
                    	//X
                    	System.out.print("X");
                    }
                	// 단백질
                    if( ( todayProtein - targetProtein ) >= 0) {
                    	//O
                    	System.out.print("O");
                    }else {
                    	//X
                    	System.out.print("X");
                    }
                	// 물
                    if( ( currentWater - targetWater ) >= 0) {
                    	//O
                    	System.out.print("O");
                    }else {
                    	//X
                    	System.out.print("X");
                    }
                    System.out.print("\t");
            	}
                current++;
            }
            if(i%7 == 0 ) {
                System.out.println();
            }
            if(current > lastDay) {
                break;
            }
        }
        System.out.println();
        System.out.println();

    }
    
//    private static final String RESET = "\u001B[0m";
//    private static final String RED = "\u001B[31m";
//    private static final String GREEN = "\u001B[32m";
//    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "";
    private static final String RED = "";
    private static final String GREEN = "";
    private static final String YELLOW = "";

    // 각 섭취량 표시
    public void checkDailyCalories(User user) {
        int todayCalories = 0;
        int targetCalories;

        if (user.getGoalHistory().get(LocalDate.now()) == null) {
            System.out.println("오늘 목표로 해야 할 칼로리 섭취량 : 데이터 부족");
            return;
        }

        targetCalories = user.getGoalHistory().get(LocalDate.now()).getTargetCalories()  + user.getGoalHistory().get(LocalDate.now()).getExerciseCarlories();

        List<Food> foods = user.getEatingHistory().get(LocalDate.now());
        if (foods != null) {
            for (Food food : foods) {
                todayCalories += food.getCalorie();
            }
        }

        printGauge("칼로리", todayCalories, targetCalories);
    }

    // 단백질 체크
    public void checkDailyProtein(User user) {
        int todayProtein = 0;
        int targetProtein;

        if (user.getGoalHistory().get(LocalDate.now()) == null) {
            System.out.println("오늘 목표로 해야 할 단백질 섭취량 : 데이터 부족");
            return;
        }

        targetProtein = user.getTargetProtein();

        List<Food> foods = user.getEatingHistory().get(LocalDate.now());
        if (foods != null) {
            for (Food food : foods) {
                todayProtein += food.getProtein();
            }
        }

        printGauge("단백질", todayProtein, targetProtein);
    }

    // 물 체크
    public void checkDailyWater(User user) {
        if (user.getGoalHistory().get(LocalDate.now()) == null) {
            System.out.println("오늘 목표로 해야 할 물 섭취량 :\t데이터 부족");
            return;
        }

        int currentWater = user.getGoalHistory().get(LocalDate.now()).getCurrentWater();
        int targetWater = user.getGoalHistory().get(LocalDate.now()).getTargetWater();

        printGauge("물", currentWater, targetWater);
    }

    // HP 바 스타일 컬러 게이지
    private void printGauge(String label, int current, int target) {
        int barLength = 20; // 게이지 길이
        int filledLength = (int) ((double) current / target * barLength);
        if (filledLength > barLength) filledLength = barLength;

        StringBuilder bar = new StringBuilder();

        // 현재 섭취량: 노란색
        for (int i = 0; i < filledLength; i++) bar.append(YELLOW).append("#");

        // 목표 달성량 남은 부분: 초록색
        for (int i = filledLength; i < barLength; i++) bar.append(GREEN).append("-");

        bar.append(RESET).append(" | 목표: ").append(target);

        // 초과량: 빨강
        if (current > target) {
            int excess = current - target;
            bar.append(RED).append(" ***초과 ").append(excess).append("***").append(RESET);
        }

        System.out.println("오늘 목표로 해야 할 " + label + " 섭취량\t: " + bar.toString());
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
    
    public void viewDailyEatingFoodList(User user) {
    	LocalDate today = LocalDate.now();

        List<Food> list = (user != null && user.getEatingHistory() != null)
                ? user.getEatingHistory().get(today)
                : null;

        if (list == null || list.isEmpty()) {
            System.out.println("오늘 먹은 음식이 없습니다!");
            return;
        }

        System.out.println();
        System.out.printf("==== %s 오늘 먹은 음식 (%d건) ====%n", today, list.size());
        String line = "---------------------------------------------------------------------";
        System.out.println(line);
        System.out.printf("%-3s %-20s %10s %10s  %s%n", "No", "이름", "칼로리", "단백질", "알레르기");
        System.out.println(line);

        int totalCal = 0;
        int totalProtein = 0;
        int idx = 1;

        for (Food food : list) {
            if (food == null) continue;

            totalCal += food.getCalorie();
            totalProtein += food.getProtein();

            // 알레르기 문자열 만들기 (메서드 없이 바로)
            String allergies;
            if (food.getAllergy() == null || food.getAllergy().isEmpty()) {
                allergies = "없음";
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < food.getAllergy().size(); i++) {
                    sb.append(food.getAllergy().get(i) == null ? "-" : food.getAllergy().get(i).name());
                    if (i < food.getAllergy().size() - 1) sb.append(", ");
                }
                allergies = sb.toString();
            }

            String name = (food.getName() == null || food.getName().trim().isEmpty()) ? "-" : food.getName();

            System.out.printf("%-3d %-20s %8d kcal %8d g  %s%n",
                    idx++, name, food.getCalorie(), food.getProtein(), allergies);
        }

        System.out.println(line);
        System.out.printf("%-3s %-20s %8d kcal %8d g%n", "", "합계", totalCal, totalProtein);
        System.out.println();
    }
}
