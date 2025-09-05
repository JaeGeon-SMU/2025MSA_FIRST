package service;

import domain.User;
import repo.UserRepo;

public class AuthenticationService {
	UserRepo userRepo;
	
	public AuthenticationService() {
		this.userRepo = new UserRepo();
	}

	void logout() {
		System.out.println("로그아웃 되었습니다.");
		System.exit(0);
	}
	
	User login(String id,String password) {
	    User user = userRepo.findById(id);
	    if (user != null && password.equals(user.getPassword())) {
	        return user;
	    }
	    return null;
	}
}
