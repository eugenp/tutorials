package com.baeldung.convert.exceldatatolist;

import com.poiji.annotation.ExcelCellName;

public class FoodInfo {

    @ExcelCellName("Category")
    private String category; //food category
    @ExcelCellName("Name")
    private String name; // food name
    @ExcelCellName("Measure")
    private String measure;
    @ExcelCellName("Calories")
    private double calories; //amount of calories in kcal/measure

    @Override
    public String toString() {
        return "FoodInfo{" + "category='" + category + '\'' + ", name='" + name + '\'' + ", measure='" + measure + '\'' + ", calories=" + calories + "} \n";
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

}
