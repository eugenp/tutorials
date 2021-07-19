package com.baeldung.defaultparams;

public class Tea {

    static final int DEFAULT_TEA_POWDER = 1; // add 1 tbsp tea powder by default

    private String name; // mandatory
    private int milk; // ml
    private boolean herbs; // add herbs or don't
    private int sugar; // tbsp
    private int teaPowder; // tbsp

    public Tea(String name, int milk, boolean herbs, int sugar, int teaPowder) {
        this.name = name;
        this.milk = milk;
        this.herbs = herbs;
        this.sugar = sugar;
        this.teaPowder = teaPowder;
    }

    // when tea powder is not provided by client, use default value
    public Tea(String name, int milk, boolean herbs, int sugar) {
        this(name, milk, herbs, sugar, DEFAULT_TEA_POWDER);
    }

    // when sugar is not provided by client, don't add it
    public Tea(String name, int milk, boolean herbs) {
        this(name, milk, herbs, 0);
    }

    // when herbs is not provided by client, don't add it
    public Tea(String name, int milk) {
        this(name, milk, false);
    }

    // when milk is not provided by client, don't add it
    public Tea(String name) {
        this(name, 0);
    }   

    public String getName() {
        return name;
    }

    public int getMilk() {
        return milk;
    }

    public boolean isHerbs() {
        return herbs;
    }

    public int getSugar() {
        return sugar;
    }

    public int getTeaPowder() {
        return teaPowder;
    }

}
