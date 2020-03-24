package com.stackify.optionalparams;

public class MultiVitaminOverloading {

    static final int DEFAULT_IRON_AMOUNT = 20;

    private final String name;    // required
    private final int vitaminA;   // in mcg
    private final int vitaminC;   // in mg
    private final int calcium;    // in mg
    private final int iron;       // in mg

    public MultiVitaminOverloading(String name) {
        this(name, 0);
    }

    public MultiVitaminOverloading(String name, int vitaminA) {
        this(name, vitaminA, 0);
    }

    public MultiVitaminOverloading(String name, int vitaminA, int vitaminC) {
        this(name, vitaminA, vitaminC, 0);
    }

    public MultiVitaminOverloading(String name, int vitaminA, int vitaminC, int calcium) {
        this(name, vitaminA, vitaminC, calcium, DEFAULT_IRON_AMOUNT);
    }

    public MultiVitaminOverloading(String name, int vitaminA, int vitaminC, int calcium, int iron) {
        this.name = name;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.calcium = calcium;
        this.iron = iron;
    }

    public String getName() {
        return name;
    }

    public int getVitaminA() {
        return vitaminA;
    }

    public int getVitaminC() {
        return vitaminC;
    }

    public int getCalcium() {
        return calcium;
    }

    public int getIron() {
        return iron;
    }
}