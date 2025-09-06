package repo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import domain.User;

public class UserRepo {
	private Map<String, User> userRepo;
	private static final String DB_FILE = "src/database/user.txt";
	
	public UserRepo(){
		userRepo=loadAll();
	}
	
	public void save(User user) {
		 if (user == null || user.getUserId() == null) return;

	        userRepo.put(user.getUserId(), user);

	        try (ObjectOutputStream oos = new ObjectOutputStream(
	                new BufferedOutputStream(new FileOutputStream(DB_FILE)))) {
	            oos.writeObject(userRepo);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }     
	}

	public Map<String, User> loadAll() {
		File file = new File(DB_FILE);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(file)))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                return (Map<String, User>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
	
	public User findById(String Id) {
		return userRepo.getOrDefault(Id, null);
	}
	
	public  Map<String, User> getAll(){
		return userRepo;
	}
}
