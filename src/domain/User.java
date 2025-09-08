package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.enums.Allergy;

public class User implements Serializable {
    // serialVersionUID 제거

    // Instance Variable
    private double currentWeight;
    private double targetWeight;
    private int targetProtein;
    private int targetCalories;
    private int minMeal;
    private int birthYear;
    private double height;
    private int targetWater; // 목표 물 량(ml)
    private List<Allergy> allergy;
    private Map<LocalDate, List<Food>> eatingHistory;
    private Map<LocalDate, DailyGoalInfo> goalHistory;
    private int exerciseCarlories; 
    private Fridge fridge;
    private String userId; // 로그인 용
    private String password;
    private String passwordHash;
    private String passwordSalt;

    public User(String userId,
                String passwordHash,
                String passwordSalt,
                double currentWeight,
                double targetWeight,
                int targetProtein,
                int targetCalories,
                int minMeal,
                int birthYear,
                double height,
                int targetWater,
                List<Allergy> allergy) {

        this.userId = userId;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;

        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
        this.targetProtein = targetProtein;
        this.targetCalories = targetCalories;
        this.minMeal = minMeal;
        this.birthYear = birthYear;
        this.height = height;
        this.targetWater = targetWater;
        this.allergy = (allergy != null) ? new ArrayList<Allergy>(allergy) : new ArrayList<Allergy>();

        this.exerciseCarlories = 0;
        this.fridge = new Fridge();
        this.eatingHistory = new HashMap<LocalDate, List<Food>>();
        this.goalHistory = new HashMap<LocalDate, DailyGoalInfo>();
    }

    // ---- 오늘 스냅샷 유틸 ----
    public DailyGoalInfo ensureTodayInfo() {
        LocalDate today = LocalDate.now();
        DailyGoalInfo info = goalHistory.get(today);
        if (info == null) {
            info = new DailyGoalInfo(this);
            goalHistory.put(today, info);
        }
        return info;
    }

    // ---- getters ----
    public double getCurrentWeight() { return this.currentWeight; }
    public double getTargetWeight()  { return this.targetWeight; }
    public int getTargetProtein()    { return this.targetProtein; }
    public int getTargetCalories()   { return this.targetCalories; }
    public int getMinMeal()          { return this.minMeal; }
    public int getBirthYear()        { return this.birthYear; }
    public double getHeight()        { return this.height; }
    public int getTargetWater()      { return this.targetWater; }
    public List<Allergy> getAllergy(){ return this.allergy; }
    public int getExerciseCarlories(){ return this.exerciseCarlories; }
    public Fridge getFridge()        { return this.fridge; }
    public String getUserId()        { return this.userId; }
    public String getPasswordHash()  { return this.passwordHash; }
    public String getPasswordSalt()  { return this.passwordSalt; }
    public Map<LocalDate, List<Food>> getEatingHistory() { return this.eatingHistory; }
    public Map<LocalDate, DailyGoalInfo> getGoalHistory() { return goalHistory; }

    // ---- setters (해당 필드만 당일 스냅샷 갱신) ----
    public void setCurrentWeight(double currentWeight){
        this.currentWeight = currentWeight;
        ensureTodayInfo().setCurrentWeight(currentWeight);
    }

    public void setTargetWeight(double targetWeight){
        this.targetWeight = targetWeight;
        ensureTodayInfo().setTargetWeight(targetWeight);
    }

    public void setTargetProtein(int targetProtein){
        this.targetProtein = targetProtein;
        ensureTodayInfo().setTargetProtein(targetProtein);
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
        ensureTodayInfo().setTargetCalories(targetCalories);
    }

    public void setMinMeal(int minMeal) {
        this.minMeal = minMeal;
        ensureTodayInfo().setMinMeal(minMeal);
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
        // DailyGoalInfo에는 birthYear가 없으므로 스냅샷 갱신 없음
    }

    public void setHeight(double height) {
        this.height = height;
        ensureTodayInfo().setHeight(height);
    }

    public void setTargetWater(int targetWater) {
        this.targetWater = targetWater;
        ensureTodayInfo().setTargetWater(targetWater);
    }

    public void setAllergy(List<Allergy> allergy) {
        this.allergy = (allergy != null) ? new ArrayList<Allergy>(allergy) : new ArrayList<Allergy>();
        // DailyGoalInfo에 알레르기 필드는 없음
    }

    public void setExerciseCarlories(int excerciseCarlories) {
        this.exerciseCarlories = excerciseCarlories;
        ensureTodayInfo().setExerciseCarlories(excerciseCarlories);
    }

    public void setFridge(Fridge fridge) {
        this.fridge = fridge;
    }
}