package domain;

import java.util.List;

public class User {
	// Instance Variable
	private double currentWeight;
	private double targetWeight;
	private int targetProtein;
	private int targetCalories;
	private int minMeal;
	private int age;
	private double height;
	private int targetWater;
	private List<Allergy> allergy;
	private int exerciseCarlories;
	private Fridge fridge;
	//생성자
	User(){
		
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
	public int getAge() {
		return this.age;
	}
	public void setAge(int age) {
		this.age=age;
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
	
	
	
	
}

