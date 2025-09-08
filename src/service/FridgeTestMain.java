package service;

import domain.EatingOutFood;
import domain.Food;
import domain.Fridge;
import domain.HomeFood;
import domain.User;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

public class FridgeTestMain {

    public static void main(String[] args) {
        // Mock 사용자 및 냉장고 생성
    	UserService userService = new UserService();
    	userService.saveUser(userService.createDummyUserWithFridgeAndMonthData());
    }
}

