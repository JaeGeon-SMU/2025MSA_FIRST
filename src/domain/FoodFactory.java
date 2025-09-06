package domain;

import domain.food.eatingOutFood.FlatfishNigiriSushi;
import domain.food.eatingOutFood.FriedChicken;
import domain.food.eatingOutFood.Kimbap;
import domain.food.eatingOutFood.Kimchistew;
import domain.food.eatingOutFood.Pizza;
import domain.food.eatingOutFood.SalmonNigiriSushi;
import domain.food.eatingOutFood.ShrimpWapperSet;
import domain.food.eatingOutFood.Tonkatsu;
import domain.food.eatingOutFood.Udon;
import domain.food.eatingOutFood.YukkeNigiriSushi;
import domain.food.homeFood.BasilBagle;
import domain.food.homeFood.ChickenBreast;
import domain.food.homeFood.CreamCheeseBagle;
import domain.food.homeFood.CupNoodle;
import domain.food.homeFood.KimchiDumpling;
import domain.food.homeFood.Mackerel;
import domain.food.homeFood.MeatDumpling;
import domain.food.homeFood.Omlet;
import domain.food.homeFood.ProteinBar;
import domain.food.homeFood.Rice;
import domain.food.homeFood.StirNodle;

public class FoodFactory {
	
	/*
	 * 냉장고 안 음식 객체에 대한 팩토리 메서드
	 */
	public HomeFood createHomeFood(String name) {
		
		HomeFood homeFood = null;
		
		switch(name) {
		case "basilBagle":
			homeFood = new BasilBagle();
			break;
		case "chickenBreast":
			homeFood = new ChickenBreast();
			break;
		case "creamCheeseBagle":
			homeFood = new CreamCheeseBagle();
			break;
		case "cupNoodle":
			homeFood = new CupNoodle();
			break;
		case "kimchiDumpling":
			homeFood = new KimchiDumpling();
			break;
		case "mackerel":
			homeFood = new Mackerel();
			break;
		case "meatDumpling":
			homeFood = new MeatDumpling();
			break;
		case "omlet":
			homeFood = new Omlet();
			break;
		case "proteinBar":
			homeFood = new ProteinBar();
			break;
		case "rice":
			homeFood = new Rice();
			break;
		case "stirNodle":
			homeFood = new StirNodle();
			break;
			
			
		}

		return homeFood;
	}
	
	/*
	 * 외식 음식 객체에 대한 팩토리 메서드
	 */
	public EatingOutFood createEatingOutFood(String name) {
		
		EatingOutFood eatingOutFood = null;
		
		switch(name) {
		case "flatfishNigiriSushi":
			eatingOutFood = new FlatfishNigiriSushi();
			break;
		case "friedChicken":
			eatingOutFood = new FriedChicken();
			break;
		case "kimbap":
			eatingOutFood = new Kimbap();
			break;
		case "kimchistew":
			eatingOutFood = new Kimchistew();
			break;
		case "pizza":
			eatingOutFood = new Pizza();
			break;
		case "salmonNigiriSushi":
			eatingOutFood = new SalmonNigiriSushi();
			break;
		case "shrimpWapperSet":
			eatingOutFood = new ShrimpWapperSet();
			break;
		case "tonkatsu":
			eatingOutFood = new Tonkatsu();
			break;
		case "udon":
			eatingOutFood = new Udon();
			break;
		case "yukkeNigiriSushi":
			eatingOutFood = new YukkeNigiriSushi();
			break;
			
		}

		return eatingOutFood;
	}
	
	
}
