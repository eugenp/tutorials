package com.stackify.optionalparams;

public class MultiVitaminAllowingNulls {

    private String name;    // required
    private Integer vitaminA;   // in mcg
    private Integer vitaminC;   // in mg
    private Integer calcium;    // in mg
    private Integer iron;       // in mg

    public MultiVitaminAllowingNulls(String name, Integer vitaminA, Integer vitaminC, Integer calcium, Integer iron) {
        this.name = name;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.calcium = calcium;
        this.iron = iron;
    }

    public String getName() {
        return name;
    }

    public Integer getVitaminA() {
        return vitaminA;
    }

    public Integer getVitaminC() {
        return vitaminC;
    }

    public Integer getCalcium() {
        return calcium;
    }

    public Integer getIron() {
        return iron;
    }
}