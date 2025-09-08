import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import domain.Food;
import domain.HomeFood;
import domain.User;
import domain.enums.Allergy;
import service.EatingOutService;
import service.FridgeService;

public class FridgeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		User user = new User(
				"u1", "ph", "ps",
				50.0,
				60.0,
				75,
				1800,
				3,
				2000,
				160.0,
				2000,
				//List.of()				
				List.of(Allergy.MILK)
		);
		
		FridgeService svc = new FridgeService(user);
		
		Map<String, Queue<Food>> map = user.getFridge().getFoodList();

		Queue<Food> qTest = new LinkedList<>();
		qTest.add(new HomeFood("Test", 200, 20, List.of(), LocalDate.now().minusDays(1), 1)); //유통기한 만료
		qTest.add(new HomeFood("Test", 210, 21, List.of(), LocalDate.now().plusDays(3), 1));
		map.put("Test", qTest);
		
		
		svc.putFood("chickenBreast", 5);
		svc.putFood("mackerel", 1);
		svc.putFood("creamCheeseBagle", 4);
		svc.putFood("rice", 3);
		
		svc.foodList();
		
		System.out.println("===========");
		
		svc.recommend();
		 
		System.out.println("===========");
		 
		svc.eatFood("mackerel");
		
		System.out.println("===========");
		
		svc.deleteFood("rice", 1);
				
		svc.foodList();
		
		System.out.println("===========");
		
		svc.recommend();
		
		System.out.println("===========");
		System.out.println("===========");
		
		EatingOutService svc2 = new EatingOutService(user);
		
		svc2.recommend();
		
		System.out.println("===========");
		
		svc2.eatFood("friedChicken");
		
		System.out.println("===========");

	}

}
