import java.util.Iterator;
import java.util.Map;

import domain.User;
import repo.UserRepo;
import service.FridgeService;

public class App {

	public static void main(String[] args) {
		UserRepo userRepo = new UserRepo();
		userRepo.save(new User("test3","password321312",200));
		Map<String, User> all = userRepo.getAll();
		Iterator<Map.Entry<String, User>> it = all.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, User> entry = it.next();
            String key = entry.getKey();
            User user = entry.getValue();
            System.out.println("ID: " + key + ", User: " + user.getUserId() + "  " + user.getPassword());
        }
	}

}
