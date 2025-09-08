package service;

import java.util.ArrayList;

import domain.User;
import domain.enums.Allergy;


public class MockUserFactory {

    public static User createMockUser() {
        // 임의의 알레르기 리스트 (없으면 빈 리스트)
        ArrayList<Allergy> allergies = new ArrayList<>();

        // User 생성자 매개변수에 맞게 값 세팅
        User mockUser = new User(
                "testUser",              // userId
                "hashedPassword123",     // passwordHash
                "randomSalt",            // passwordSalt
                70.0,                    // currentWeight
                65.0,                    // targetWeight
                120,                     // targetProtein
                2000,                    // targetCalories
                3,                       // minMeal
                1996,                    // birthYear
                170.5,                   // height
                2000,                    // targetWater
                allergies                // allergy 리스트
        );

        return mockUser;
    }
}

