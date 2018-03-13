package org.designpattern.builder.tutorial;

public class NutritionFacts {

	private final int servingSize; 
	
	private final int servings;
	
	private final int calories;
	
	private final int fat;
	
	private final int sodium;
	
	private final int carbohydrate;
	
	public NutritionFacts(int servịngSize, int servings) {
		this(servịngSize, servings, 0);
	
	}

	public NutritionFacts(int servingSize, int servings, int calories) {
		
		this(servingSize, servings, 0, 0);
	}

	public NutritionFacts(int servingSize, int servings, int calories, int fat) {
		this(servingSize, servings, 0, 0, 0);
	}

	public NutritionFacts(int servingSize, int servings, int calories, int fat,
			int sodium) {
		this(servingSize, servings, 0, 0, 0, 0);
	}

	public NutritionFacts(int servingSize, int servings, int calories, int fat,
			int sodium, int carbohydrate) {
	
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}
	
}
