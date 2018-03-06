package org.designpattern.builder.tutorial;

// JavaBeans Pattern - allows inconsistency, mandates mutability
public class NutritionFacts2 {

	// Parameters initialized to defaut values (if any)
	private int servingSize = -1;
	private int servings = -1;
	private int calories = 0;
	private int fat = 0;
	private int sodium = 0;
	private int carbohydrate = 0;
	
	public NutritionFacts2() {
		
	}
	
	public void setServingSize(int val) {
		this.servingSize = val;
	}
	
	public void setServings(int val) {
		this.servings = val;
	}
	
	public void setCalories(int val) {
		this.calories = val;
	}
	
	public void setFat(int val) {
		this.fat = val;
	}
	
	public void setSodium(int val) {
		this.sodium = val;
	}
	
	public void setCarbohydrate(int val) {
		this.carbohydrate = val;
	}
}
