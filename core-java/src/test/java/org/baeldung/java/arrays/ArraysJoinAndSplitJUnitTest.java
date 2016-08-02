package org.baeldung.java.arrays;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class ArraysJoinAndSplitJUnitTest {
    
    private final String[] sauces = {"Marinara", "Olive Oil"};
    private final String[] cheeses = {"Mozzarella", "Feta", "Parmesan"};
    private final String[] vegetables = {"Olives", "Spinach", "Green Peppers"};
    

    @Test
    public void givenThreeStringArrays_whenJoiningIntoOneStringArray_ShouldSucceed() throws Exception {
        String[] toppings = new String[sauces.length + cheeses.length + vegetables.length];
        
        System.arraycopy(sauces, 0, toppings, 0, sauces.length);
        int AddedSoFarCount = sauces.length;
        
        System.arraycopy(cheeses, 0, toppings, AddedSoFarCount, cheeses.length);
        AddedSoFarCount += cheeses.length;

        System.arraycopy(vegetables, 0, toppings, AddedSoFarCount, vegetables.length);
        
        Assert.assertArrayEquals(toppings,
          new String[] {"Marinara", "Olive Oil", "Mozzarella", "Feta",
          "Parmesan", "Olives", "Spinach", "Green Peppers"} );
    }
   
    
    private final String[] customers = {"Jay", "Harry", "Ronnie", "Gary", "Ross"};
    @Test
    public void givenOneStringArray_whenSplittingInHalfBetweenTwoStringArrays_ShouldSucceed() throws Exception {
        int ordersHalved = (customers.length / 2) + (customers.length % 2);
        
        String[] driverOne = Arrays.copyOf(customers, ordersHalved);
        String[] driverTwo = Arrays.copyOfRange(customers, ordersHalved, customers.length);
        
        Assert.assertArrayEquals(driverOne, new String[] {"Jay", "Harry", "Ronnie"} );
        Assert.assertArrayEquals(driverTwo, new String[] {"Gary", "Ross"} );
    }
}
