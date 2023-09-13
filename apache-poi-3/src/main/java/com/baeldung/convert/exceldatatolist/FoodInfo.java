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
    @ExcelCellName("Protein")
    private double protein; //amount of protein in grams/measure
    @ExcelCellName("Fat")
    private double fat; //amount of fat in grams/measure
    @ExcelCellName("Carbs")
    private double carbs; //amount of carbs in grams/measure
    @ExcelCellName("Fiber")
    private double fiber; //amount of dietary fiber in grams/measure

    @Override
    public String toString() {
        return "FoodInfo{" + "category='" + category + '\'' + ", name='" + name + '\'' + ", measure='" + measure + '\'' + ", calories=" + calories + ", protein=" + protein + ", fat=" + fat + ", carbs=" + carbs + ", fiber=" + fiber + "} \n";
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

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }
}
