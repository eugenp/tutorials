package org.baeldung.java.arrays;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class ArraysJoinAndSplitJUnitTest {
    
    //pizza toppings
    private final String[] sauces = {"Marinara", "Olive Oil"};
    private final String[] cheeses = {"Mozzarella", "Feta", "Parmesan"};
    private final String[] vegetables = {"Olives", "Spinach", "Green Peppers"};
    

    @Test
    public void givenThreeStringArrays_whenJoiningIntoOneStringArray_ShouldSucceed() throws Exception {
        //create the destination array
        String[] toppings = new String[sauces.length + cheeses.length + vegetables.length];
        
        //add the sauces
        System.arraycopy(sauces, 0, toppings, 0, sauces.length);
        int AddedSoFarCount = sauces.length;
        
        //add the cheeses
        System.arraycopy(cheeses, 0, toppings, AddedSoFarCount, cheeses.length);
        AddedSoFarCount += cheeses.length;

        //add the vegetables
        System.arraycopy(vegetables, 0, toppings, AddedSoFarCount, vegetables.length);
        
        //check the result
        Assert.assertArrayEquals(toppings,
          new String[] {"Marinara", "Olive Oil", "Mozzarella", "Feta",
          "Parmesan", "Olives", "Spinach", "Green Peppers"} );
    }
   
    
    private final String[] customers = {"Jay", "Harry", "Ronnie", "Gary", "Ross"};
    @Test
    public void givenOneStringArray_whenSplittingInHalfBetweenTwoStringArrays_ShouldSucceed() throws Exception {
        //split the orders in half, rounding up
        int ordersHalved = (customers.length / 2) + (customers.length % 2);
        
        //divide the orders between two drivers
        String[] driverOne = Arrays.copyOf(customers, ordersHalved);
        String[] driverTwo = Arrays.copyOfRange(customers, ordersHalved, customers.length);
        
        //check ther result
        Assert.assertArrayEquals(driverOne, new String[] {"Jay", "Harry", "Ronnie"} );
        Assert.assertArrayEquals(driverTwo, new String[] {"Gary", "Ross"} );
    }
}
