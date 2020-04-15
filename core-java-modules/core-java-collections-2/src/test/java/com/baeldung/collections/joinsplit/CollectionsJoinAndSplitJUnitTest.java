package com.baeldung.collections.joinsplit;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CollectionsJoinAndSplitJUnitTest {

    private ArrayList<String> sauces = new ArrayList<>();
    private ArrayList<String> cheeses = new ArrayList<>();
    private ArrayList<String> vegetables = new ArrayList<>();

    private ArrayList<ArrayList<String>> ingredients = new ArrayList<>();

    @Before
    public void init() {
        sauces.add("Olive Oil");
        sauces.add("Marinara");

        cheeses.add("Mozzarella");
        cheeses.add("Feta");
        cheeses.add("Parmesan");

        vegetables.add("Olives");
        vegetables.add("Spinach");
        vegetables.add("Green Peppers");

        ingredients.add(sauces);
        ingredients.add(cheeses);
        ingredients.add(vegetables);
    }

    @Test
    public void givenThreeArrayLists_whenJoiningIntoOneArrayList_shouldSucceed() {
        ArrayList<ArrayList<String>> toppings = new ArrayList<>();

        toppings.add(sauces);
        toppings.add(cheeses);
        toppings.add(vegetables);

        Assert.assertTrue(toppings.size() == 3);
        Assert.assertTrue(toppings.contains(sauces));
        Assert.assertTrue(toppings.contains(cheeses));
        Assert.assertTrue(toppings.contains(vegetables));
    }

    @Test
    public void givenOneArrayList_whenSplittingIntoTwoArrayLists_shouldSucceed() {

        ArrayList<ArrayList<String>> removedToppings = new ArrayList<>();
        removedToppings.add(ingredients.remove(ingredients.indexOf(vegetables)));

        Assert.assertTrue(removedToppings.contains(vegetables));
        Assert.assertTrue(removedToppings.size() == 1);
        Assert.assertTrue(ingredients.size() == 2);
        Assert.assertTrue(ingredients.contains(sauces));
        Assert.assertTrue(ingredients.contains(cheeses));
    }
}
