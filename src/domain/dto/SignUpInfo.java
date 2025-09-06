package domain.dto;

import java.util.List;

import domain.Allergy;

public class SignUpInfo {
    private double currentWeight;
    private double targetWeight;
    private int targetProtein;
    private int targetCalories;
    private int minMeal;
    private int age;
    private double height;
    private int targetWater;
    private List<Allergy> allergy;
    private String userId; 
    private String password;

    public SignUpInfo(String userId, String password,
                      double currentWeight, double targetWeight,
                      int targetProtein, int targetCalories,
                      int minMeal, int age, double height,
                      int targetWater, List<Allergy> allergy) {
        this.userId = userId;
        this.password = password;
        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
        this.targetProtein = targetProtein;
        this.targetCalories = targetCalories;
        this.minMeal = minMeal;
        this.age = age;
        this.height = height;
        this.targetWater = targetWater;
        this.allergy = allergy;
    }

    public String getUserId() { return userId; }
    public String getPassword() { return password; }
    public double getCurrentWeight() { return currentWeight; }
    public double getTargetWeight() { return targetWeight; }
    public int getTargetProtein() { return targetProtein; }
    public int getTargetCalories() { return targetCalories; }
    public int getMinMeal() { return minMeal; }
    public int getAge() { return age; }
    public double getHeight() { return height; }
    public int getTargetWater() { return targetWater; }
    public List<Allergy> getAllergy() { return allergy; }
}