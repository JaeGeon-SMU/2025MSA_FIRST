package domain;

public class DailyGoalInfo {
	private double currentWeight;
	private double targetWeight;
	private int targetProtein;
    private int targetCalories;
    private int minMeal;
    private double height;
    private int targetWater;
	public double getCurrentWeight() {
		return currentWeight;
	}

	public double getTargetWeight() {
		return targetWeight;
	}
	
	public int getTargetProtein() {
		return targetProtein;
	}

	public int getTargetCalories() {
		return targetCalories;
	}
	
	public int getMinMeal() {
		return minMeal;
	}

	public double getHeight() {
		return height;
	}

	public int getTargetWater() {
		return targetWater;
	}
	public void setTargetWater(int targetWater) {
		this.targetWater += targetWater;
	}

    
}
