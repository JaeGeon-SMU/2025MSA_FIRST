package domain;

import java.util.List;

public class User {
	// Instance Variable
	private double currentWeight; //현재체중
	private double targetWeight; // 목표체중
	private int targetProtein; //목표단백질
	private int targetCalories; //목표 칼로리
	private int minMeal;  //최소끼니
	private int age;      //연령
	private double height; //키
	private int targetWater; //목표물
	private List<Allergy> allergy; //알러지리스트 배열
	private int exerciseCarlories; //운동소모칼로리
	private Fridge fridge; //냉장고
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

