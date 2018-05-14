package com.stackify.optionalparams;

public class MultiVitamin {

    private String name;    // required
    private int vitaminA;   // in mcg
    private int vitaminC;   // in mg
    private int calcium;    // in mg
    private int iron;       // in mg

    public MultiVitamin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(int vitaminA) {
        this.vitaminA = vitaminA;
    }

    public int getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(int vitaminC) {
        this.vitaminC = vitaminC;
    }

    public int getCalcium() {
        return calcium;
    }

    public void setCalcium(int calcium) {
        this.calcium = calcium;
    }

    public int getIron() {
        return iron;
    }

    public void setIron(int iron) {
        this.iron = iron;
    }
}