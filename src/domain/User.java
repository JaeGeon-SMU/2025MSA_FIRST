package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable{
	// Instance Variable
	private double currentWeight;
	private double targetWeight;
	private int targetProtein;
	private int targetCalories;
	private int minMeal;
	private int birthYear;
	private double height;
	private int targetWater; //목표 물 량
	private List<Allergy> allergy;
	private Map<LocalDate, List<Food>> eatingHistory;
	private Map<LocalDate, DailyGoalInfo> goalHistory;
	private int exerciseCarlories;
	private Fridge fridge;
	private String userId; //로그인 용
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
    this.allergy = allergy;

    // 시스템에서 자동 관리
    this.exerciseCarlories = 0;
    this.fridge = new Fridge();
    this.eatingHistory = new HashMap<>();
}
	
	//getter setter
	public double getCurrentWeight() {
		return this.currentWeight;
	}
	public void setCurrentWeight(double currentWeight){
		this.currentWeight=currentWeight;
	}
	public double getTargetWeight() {
		return this.targetWeight;
	}
	public void setTargetWeight(double targetWeight){
		this.targetWeight=targetWeight;
	}
	public int getTargetProtein() {
		return this.targetProtein;
	}
	public void setTargetProtein(int targetProtein){
		this.targetProtein=targetProtein;
	}
	public int getTargetCalories() {
		return this.targetCalories;		
	}
	public void setTargetCalories(int targetCalories) {
		this.targetCalories=targetCalories;
	}
	public int getMinMeal() {
		return this.minMeal;
	}
	public void setMinMeal(int minMeal) {
		this.minMeal=minMeal;
	}
	public int getBirthYear() {
		return this.birthYear;
	}
	public void setBirthYear(int birthYear) {
		this.birthYear=birthYear;
	}
	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height=height;
	}
	public int getTargetWater() {
		return this.targetWater;
	}
	public void setTargetWater(int targetWater) {
		this.targetWater=targetWater;
	}

	public List<Allergy> getAllergy() {
		return this.allergy;
	}
	public void setAllergy(List<Allergy> allergy) {
		this.allergy=allergy;
	}
	public int getExerciseCarlories() {
		return this.exerciseCarlories;
	}
	public void setExerciseCarlories(int excerciseCarlories) {
		this.exerciseCarlories=excerciseCarlories;
	}
	public Fridge getFridge() {
		return this.fridge;
	}
	public void setFridge(Fridge fridge) {
		this.fridge=fridge;
	}
	
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public String getPasswordSalt() {
		return this.passwordSalt;
	}

	public Map<LocalDate, List<Food>> getEatingHistory() {
		return this.eatingHistory;
	}

	public Map<LocalDate, DailyGoalInfo> getGoalHistory() {
		return goalHistory;
	}
	
	
}

