import java.util.Iterator;
import java.util.Map;

import domain.HomeFood;
import domain.User;
import domain.dto.SignUpInfo;
import domain.food.homeFood.ChickenBreast;
import repo.UserRepo;
import service.AuthenticationService;

public class App {

	public static void main(String[] args) {
		 AuthenticationService auth = new AuthenticationService();

	        // 1) 회원가입
	        SignUpInfo info = new SignUpInfo(
	                "test3",            // userId
	                "password321312",   // password(원문)
	                70.0,               // currentWeight
	                65.0,               // targetWeight
	                100,                // targetProtein
	                2000,               // targetCalories
	                3,                  // minMeal
	                25,                 // age
	                175.0,              // height
	                2000,               // targetWater
	                null          // allergy (비어있음)
	        );
	        
	        SignUpInfo info2 = new SignUpInfo(
	                "test",            // userId
	                "1234",   // password(원문)
	                70.0,               // currentWeight
	                65.0,               // targetWeight
	                100,                // targetProtein
	                2000,               // targetCalories
	                3,                  // minMeal
	                25,                 // age
	                175.0,              // height
	                2000,               // targetWater
	                null          // allergy (비어있음)
	        );

	        auth.signUp(info); // 저장 + "회원가입 완료" 출력
	        auth.signUp(info2); // 저장 + "회원가입 완료" 출력

	        System.out.println("\n=== 저장된 사용자 목록 ===");
	        // 2) Repo에서 전체 사용자 출력
	        UserRepo repo = auth.userRepo; // AuthenticationService 내부 repo 접근
	        Map<String, User> all = repo.getAll();
	        Iterator<Map.Entry<String, User>> it = all.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry<String, User> e = it.next();
	            User u = e.getValue();
	            System.out.println("ID: " + e.getKey()
	                    + ", UserId: " + u.getUserId()
	                    + ", Hash: " + u.getPasswordHash()
	                    + ", Salt: " + u.getPasswordSalt());
	        }

	        System.out.println("\n=== 로그인 테스트 ===");
	        // 3) 로그인 성공 케이스
	        User ok = auth.login("test3", "password321312");
	        System.out.println("로그인 성공? " + (ok != null));

	        // 4) 로그인 실패 케이스
	        User fail = auth.login("test3", "wrong-password");
	        System.out.println("잘못된 비번으로 로그인 성공? " + (fail != null));
	        
	        System.out.println("\n=== 로그인 테스트 ===");
	        // 3) 로그인 성공 케이스
	        User ok2 = auth.login("test44", "1234");
	        System.out.println("로그인 성공? " + (ok2 != null));

	        // 4) 로그인 실패 케이스
	        User fail2 = auth.login("test", "1234");
	        System.out.println("잘못된 비번으로 로그인 성공? " + (fail2 != null));

	        
	        
	}

}
