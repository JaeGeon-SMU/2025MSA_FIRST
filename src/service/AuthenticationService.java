package service;

import java.util.List;

import domain.Allergy;
import domain.User;
import domain.dto.SignUpInfo;
import repo.UserRepo;
import util.SHA256PasswordSecurity;

public class AuthenticationService {
	public UserRepo userRepo;
	
	public AuthenticationService() {
		this.userRepo = new UserRepo();
	}
	

	void logout() {
		System.out.println("로그아웃 되었습니다.");
		System.exit(0);
	}
	
	public User login(String id,String password) {
		 if (id == null || id.isBlank() || password == null) return null;

	        User user = userRepo.findById(id);
	        if (user == null) return null;

	        boolean ok = SHA256PasswordSecurity.verifyPassword(
	                password,
	                user.getPasswordHash(),   // storedHash
	                user.getPasswordSalt()    // salt
	        );
	        return ok ? user : null;
	}
	
	public void signUp(SignUpInfo signUpInfo) {
		  String salt = SHA256PasswordSecurity.generateSalt();
	        String hash = SHA256PasswordSecurity.hashPassword(signUpInfo.getPassword(), salt);
	
	        User user = new User(
	                signUpInfo.getUserId(),
	                hash,               // passwordHash
	                salt,               // passwordSalt
	                signUpInfo.getCurrentWeight(),
	                signUpInfo.getTargetWeight(),
	                signUpInfo.getTargetProtein(),
	                signUpInfo.getTargetCalories(),
	                signUpInfo.getMinMeal(),
	                signUpInfo.getAge(),
	                signUpInfo.getHeight(),
	                signUpInfo.getTargetWater(),
	                signUpInfo.getAllergy()
	        );
	
	        userRepo.save(user);
	        System.out.println("회원가입 완료: " + signUpInfo.getUserId());
    }
}
