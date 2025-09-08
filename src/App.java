import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import domain.HomeFood;
import domain.User;
import domain.dto.SignUpInfo;
import domain.enums.Allergy;
import repo.UserRepo;
import service.AuthenticationService;
import service.ChatGptSummaryService;
import service.FridgeService;
import domain.User;
import domain.dto.SignUpInfo;
import service.AuthenticationService;
import service.UserService;


public class App {

	public static void main(String[] args) {
		AuthenticationService auth = new AuthenticationService();
        UserService userService = new UserService();

        try (Scanner sc = new Scanner(System.in)) {
            AuthMenu menu = new AuthMenu(auth, userService, sc);
            User user = menu.run();  // 로그인 성공 시 User, 종료 시 null

            if (user == null) {
                System.out.println("프로그램 종료");
                return;
            }
            // 로그인 성공 후 메인 메뉴로 이동
            MainAfterLoginMenu mainMenu = new MainAfterLoginMenu(user, sc);
            mainMenu.run();
            
        }
	}
}
