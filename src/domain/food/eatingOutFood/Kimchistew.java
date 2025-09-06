package domain.food.eatingOutFood;

import java.util.List;

import domain.Allergy;
import domain.EatingOutFood;

public class Kimchistew extends EatingOutFood{
	
	public Kimchistew(){
		super("kimchistew", 
				458, 
				36, 
				List.of(Allergy.PORK), 
				13000
				);
	}	

}