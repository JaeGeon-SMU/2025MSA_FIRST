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
import util.SHA256PasswordSecurity;
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
            targetCalories = user.getGoalHistory().get(today.plusDays(i-6)).getTargetCalories() ;
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
            		targetCalories = user.getGoalHistory().get(currentday).getTargetCalories() ;
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
    // ===== 칼로리 섭취량 표시 =====
    //목표 칼로리 - 현재 섭취량(for문 합산 , List<Food>) 메인화면 사용 
    public void checkDailyCalories(User user) {
        //List<Food> 형태 필요
    	//운동한 만큼 더 먹어야한다!
        int todayCalories = 0;
        if(user.getEatingHistory().get(LocalDate.now()) == null || user.getGoalHistory().get(LocalDate.now()) == null) {
        	of.print(Labels.CHECKCALORIES.getValue(), "금일 식단 데이터 부족");
        	return;
        }
        for(Food food : user.getEatingHistory().get(LocalDate.now())) {            	
        	todayCalories += food.getCalorie();
        }      
        //운동 칼로리 추가 이후 주석 해제
//        todayCalories += user.getGoalHistory().get(LocalDate.now()).getExerciseCarlories();
        of.print(Labels.CHECKCALORIES.getValue() , (user.getTargetCalories()-todayCalories) );
    }
    //목표 단백질 - 현재 섭취량(for문 합산 , List<Food>) 메인화면 사용
    public void checkDailyProtein(User user){
        int todayProtein = 0;
        if(user.getEatingHistory().get(LocalDate.now()) == null || user.getGoalHistory().get(LocalDate.now()) == null) {
        	of.print(Labels.CHECKPROTEIN.getValue(), "금일 식단 데이터 부족");
        	return;
        }
        for(Food food : user.getEatingHistory().get(LocalDate.now())) {            	
        	todayProtein += food.getProtein();
        }      
        of.print(Labels.CHECKPROTEIN.getValue() , (user.getTargetProtein()-todayProtein) );       
    }
    public void checkDailyWater(User user) {
    	int todayWater = 0;
        if( user.getGoalHistory().get(LocalDate.now()) == null) {
        	of.print(Labels.CHECKWATER.getValue(), "금일 물 데이터 부족");
        	return;
        }
    	of.print(Labels.CHECKWATER.getValue(), 
    	user.getGoalHistory().get(LocalDate.now()).getTargetWater() 
    	- user.getGoalHistory().get(LocalDate.now()).getCurrentWater()  );
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
    
 // UserService 안에 추가

 // 1) 한 달치 달력 테스트까지 되는 더미 유저 생성 & 저장
 public User createDummyUserWithFridgeAndMonthData() {
     String userId = "gihyeon";
     String rawPassword = "1234";

     // 비밀번호 해시 & 솔트
     String salt = SHA256PasswordSecurity.generateSalt();
     String hash = SHA256PasswordSecurity.hashPassword(rawPassword, salt);

     User user = new User(
             userId,
             hash,
             salt,
             47.0,    // currentWeight
             50.0,    // targetWeight
             50,      // targetProtein
             1200,    // targetCalories
             3,       // minMeal
             2003,    // birthYear
             162.0,   // height
             1500,    // targetWater
             List.of(Allergy.SHRIMP)
     );

     // 냉장고 시딩 (putFood 사용)
     seedFridge(user);

     // 이번 달 기록 시딩 (목표/섭취/물)
     seedCurrentMonth(user);

     // 저장
     userRepo.save(user);
     return user;
 }

 // 2) 냉장고에 더미 음식 채우기 (FridgeService.putFood 사용)
 private void seedFridge(User user) {
     FridgeService fs = new FridgeService(user);

     fs.putFood("chickenBreast",    8);
     fs.putFood("rice",             6);
     fs.putFood("cupNoodle",        4);
     fs.putFood("stirNodle",        2);
     fs.putFood("basilBagle",       3);
     fs.putFood("creamCheeseBagle", 2);
     fs.putFood("kimchiDumpling",   5);
     fs.putFood("meatDumpling",     5);
     fs.putFood("mackerel",         3);
     fs.putFood("omlet",            6);
     fs.putFood("proteinBar",       10);

     // 선택) 리오더 포인트
     try {
         fs.setReorderPoint("chickenBreast", 5);
         fs.setReorderPoint("rice",          3);
         fs.setReorderPoint("proteinBar",    4);
     } catch (IllegalArgumentException ignored) {}
 }

 // 3) 이번 달 달력용 데이터 한 번에 채우기
//     - 매일 DailyGoalInfo 생성 (User 기반)
//     - 3일마다 목표 달성하도록 음식/물 충족시킴 → 달력에서 OOO 찍히도록
 private void seedCurrentMonth(User user) {
	    // 오늘 날짜
	    LocalDate today = LocalDate.now();
	    // 이번 달의 1일
	    LocalDate first = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
	    // 이번 달 마지막 날짜
	    int lastDay = first.lengthOfMonth();

	    for (int d = 1; d <= lastDay; d++) {
	        LocalDate day = first.withDayOfMonth(d);

	        // 목표 생성
	        DailyGoalInfo goal = new DailyGoalInfo(user);
	        user.getGoalHistory().put(day, goal);

	        // 섭취 음식 리스트
	        List<Food> foods = new java.util.ArrayList<>();

	        // 규칙: 3일마다 달성 (칼로리/단백질/물 충족)
	        boolean achieve = (d % 3 == 0);

	        if (achieve) {
	            foods.add(new domain.food.homeFood.Rice());
	            foods.add(new domain.food.homeFood.ChickenBreast());
	            foods.add(new domain.food.homeFood.Omlet());
	            foods.add(new domain.food.homeFood.ProteinBar());
	            // 물 달성
	            goal.setCurrentWater(goal.getTargetWater());
	        } else {
	            foods.add(new domain.food.homeFood.Rice());
	            foods.add(new domain.food.homeFood.ChickenBreast());
	            // 물 절반만
	            goal.setCurrentWater(goal.getTargetWater() / 2);
	        }

	        user.getEatingHistory().put(day, foods);
	    }
	}
}
