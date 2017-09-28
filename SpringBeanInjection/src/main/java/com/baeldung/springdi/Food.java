package com.baeldung.springdi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Food {
	private VegNonVeg vegnonveg;

	@Autowired
	public void setVegnonveg(VegNonVeg vegnonveg) {
		this.vegnonveg = vegnonveg;
	}
	//DI Using constructor injection
	/*public Food(VegNonVeg vegnonveg) {
		this.vegnonveg = vegnonveg;
	}*/
	
	public void myFood(){
		System.out.println(vegnonveg.food());
	}
	

}
