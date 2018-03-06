package org.designpattern.builder.tutorial;

public class App {

	public static void main(String[] args) {
		
		// create an instance, you use the constructor with the shortest parameter list 
		// containing all the parameters you want to set:
		 NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);
		 
		 NutritionFacts2 cocaCola2 = new NutritionFacts2();
		 cocaCola2.setServings(240);
		 cocaCola2.setServings(8);
		 cocaCola2.setCalories(100);
		 cocaCola2.setSodium(35);
		 cocaCola2.setCarbohydrate(27);
		 
		 
		 NutritionFacts3 cocaCola3 = new NutritionFacts3.Builder(240, 8)
		 	.calories(100).sodium(35).carbohydrate(27).build();
	}
}
