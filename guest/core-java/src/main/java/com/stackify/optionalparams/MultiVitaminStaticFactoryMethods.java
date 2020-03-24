package com.stackify.optionalparams;

public class MultiVitaminStaticFactoryMethods {

    static final int IRON_AMT_DEF = 20;
    static final int IRON_AMT_MEN = 30;

    static final int CALCIUM_AMT_DEF = 100;
    static final int CALCIUM_AMT_WOMEN = 120;

    private final String name;    // required
    private final int vitaminA;   // in mcg
    private final int vitaminC;   // in mg
    private final int calcium;    // in mg
    private final int iron;       // in mg

    public static MultiVitaminStaticFactoryMethods forMen(String name) {
        return new MultiVitaminStaticFactoryMethods(name, 5000, 60, CALCIUM_AMT_DEF, IRON_AMT_MEN);
    }

    public static MultiVitaminStaticFactoryMethods forWomen(String name) {
        return new MultiVitaminStaticFactoryMethods(name, 5000, 60, CALCIUM_AMT_WOMEN, IRON_AMT_DEF);
    }

    private MultiVitaminStaticFactoryMethods(String name, int vitaminA, int vitaminC, int calcium, int iron) {
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