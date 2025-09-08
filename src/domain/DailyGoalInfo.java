package domain;

import java.io.Serializable;

public class DailyGoalInfo implements Serializable {

    private double currentWeight;
    private double targetWeight;
    private int targetProtein;
    private int targetCalories;
    private int minMeal;
    private double height;
    //물 량(ml)
    private int targetWater;
    //물 량(ml)
    private int currentWater;

    public DailyGoalInfo() {}

    public DailyGoalInfo(User u) {
        this.currentWeight = u.getCurrentWeight();
        this.targetWeight  = u.getTargetWeight();
        this.targetProtein = u.getTargetProtein();
        this.targetCalories= u.getTargetCalories();
        this.minMeal       = u.getMinMeal();
        this.height        = u.getHeight();
        this.targetWater   = u.getTargetWater();
        this.currentWater  = 0;
    }

    // getters/setters …
    public double getCurrentWeight() { return currentWeight; }
    public void setCurrentWeight(double v) { this.currentWeight = v; }

    public double getTargetWeight() { return targetWeight; }
    public void setTargetWeight(double v) { this.targetWeight = v; }

    public int getTargetProtein() { return targetProtein; }
    public void setTargetProtein(int v) { this.targetProtein = v; }

    public int getTargetCalories() { return targetCalories; }
    public void setTargetCalories(int v) { this.targetCalories = v; }

    public int getMinMeal() { return minMeal; }
    public void setMinMeal(int v) { this.minMeal = v; }

    public double getHeight() { return height; }
    public void setHeight(double v) { this.height = v; }

    public int getTargetWater() { return targetWater; }
    public void setTargetWater(int v) { this.targetWater = v; } // ← 누적 아님: 설정

    public int getCurrentWater() { return currentWater; }
    public void addCurrentWater(int ml) { this.currentWater += ml; }
    public void setCurrentWater(int ml) { this.currentWater = ml; }
}
