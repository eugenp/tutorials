package org.baeldung.java.collections;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


//import java.util.AbstractSet;

public class CollectionsJoinAndSplitJUnitTest {

    public ArrayList<String> sauces = new ArrayList<String>();
    public ArrayList<String> cheeses = new ArrayList<String>();
    public ArrayList<String> vegetables = new ArrayList<String>();
    
    public ArrayList<ArrayList<String>> ingredients = new ArrayList<ArrayList<String>>();

    public CollectionsJoinAndSplitJUnitTest() throws Exception {
        //generate test data
        whenGeneratingTestData_ShouldSucceed();
    }

    @Test
    public void whenGeneratingTestData_ShouldSucceed() throws Exception {
        sauces.clear();
        sauces.add("Olive Oil");
        sauces.add("Marinara");
        
        cheeses.clear();
        cheeses.add("Mozarella");
        cheeses.add("Feta");
        cheeses.add("Parmesan");
        
        vegetables.clear();
        vegetables.add("Olives");
        vegetables.add("Spinach");
        vegetables.add("Green Peppers");
        
        ingredients.clear();
        ingredients.add(sauces);
        ingredients.add(cheeses);
        ingredients.add(vegetables);

        Assert.assertTrue(sauces.size() == 2);
        Assert.assertTrue(cheeses.size() == 3);
        Assert.assertTrue(vegetables.size() == 3);
        Assert.assertTrue(ingredients.size() == 3);
    }
        
    @Test
    public void givenThreeArrayLists_whenJoiningIntoOneArrayList_ShouldSucceed() throws Exception {
        ArrayList<ArrayList<String>> toppings = new ArrayList<ArrayList<String>>();
        
        toppings.add(sauces);
        toppings.add(cheeses);
        toppings.add(vegetables);

        Assert.assertTrue(toppings.size() == 3);
        Assert.assertTrue(toppings.contains(sauces));
        Assert.assertTrue(toppings.contains(cheeses));
        Assert.assertTrue(toppings.contains(vegetables));
    }
    
   @Test
   public void givenOneArrayList_whenSplittingIntoTwoArrayLists_shouldSucceed() throws Exception {
       ArrayList<ArrayList<String>> toppings = ingredients;
       ArrayList<ArrayList<String>> removedToppings = new ArrayList<ArrayList<String>>(); 
       
       removedToppings.add(toppings.remove(toppings.indexOf(vegetables)));
       
       Assert.assertTrue(removedToppings.contains(vegetables));
       Assert.assertTrue(removedToppings.size() == 1);
       Assert.assertTrue(toppings.size() == 2);
       Assert.assertTrue(toppings.contains(sauces));
       Assert.assertTrue(toppings.contains(cheeses));
   }
}
