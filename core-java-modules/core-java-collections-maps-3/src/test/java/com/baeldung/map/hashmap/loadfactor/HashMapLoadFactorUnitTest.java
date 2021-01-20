package com.baeldung.map.hashmap.loadfactor;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class HashMapLoadFactorUnitTest {

    @Test
    public void whenCreateMapWithDefaultParam_thenSucces() {
        Map<String, String> mapWithDefaultParams = new HashMap<String, String>();
        mapWithDefaultParams.put("1", "One");
        mapWithDefaultParams.put("2", "two");
        mapWithDefaultParams.put("3", "three");
        mapWithDefaultParams.put("4", "four");
        mapWithDefaultParams.put("5", "five");
        
        Assert.assertEquals(5, mapWithDefaultParams.size());
    }

    @Test
    public void whenCreateMapWithInitialCapacity_thenSucces() {
        Map<String, String> mapWithInitialCapacity = new HashMap<String, String>(5);
        mapWithInitialCapacity.put("1", "One");
        mapWithInitialCapacity.put("2", "two");
        mapWithInitialCapacity.put("3", "three");
        
        Assert.assertEquals(3, mapWithInitialCapacity.size());
    }

    @Test
    public void whenCreateMapWithInitialCapacityAndLF_thenSucces() {
        Map<String, String> mapWithInitialCapacityAndLF = new HashMap<String, String>(5, 0.5f);
        mapWithInitialCapacityAndLF.put("1", "one");
        mapWithInitialCapacityAndLF.put("2", "two");
        mapWithInitialCapacityAndLF.put("3", "three");
        mapWithInitialCapacityAndLF.put("4", "four");
        mapWithInitialCapacityAndLF.put("5", "five");
        mapWithInitialCapacityAndLF.put("6", "six");
        mapWithInitialCapacityAndLF.put("7", "seven");
        mapWithInitialCapacityAndLF.put("8", "eight");
        mapWithInitialCapacityAndLF.put("9", "nine");
        mapWithInitialCapacityAndLF.put("10", "ten");
     
        Assert.assertEquals(10, mapWithInitialCapacityAndLF.size());
    }
}
