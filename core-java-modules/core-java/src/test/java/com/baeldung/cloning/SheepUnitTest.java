package com.baeldung.cloning;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

public class SheepUnitTest {
    
    @Test
    public void givenSheepAndHerder_whenShallowCopySheep_thenHerderIdentical() {
        Herder herderJohn = new Herder("John");
        Sheep sheep1 = new Sheep(1, herderJohn);
        
        Sheep sheep2 = null;
        try {
            sheep2 = (Sheep) sheep1.clone();
        } catch (CloneNotSupportedException e) {
            fail("Unexpected failure while cloning sheep: ", e);
        }
        
        assertFalse(sheep1 == sheep2, "Unexpected identical sheep");
        assertTrue(sheep1.getHerder() == sheep2.getHerder(), "Unexpected different herders");
    }
    
    @Test
    public void givenSheepAndHerder_whenDeepCopySheep_thenHerdersDifferent() {
        Herder herderJohn = new Herder("John");
        Sheep sheep1 = new Sheep(3, herderJohn);
        
        Sheep sheep2 = null;
        try {
            sheep2 = (Sheep) sheep1.deepCopy();
        } catch (CloneNotSupportedException e) {
            fail("Unexpected failure while cloning sheep: ", e);
        }
        
        assertFalse(sheep1 == sheep2, "Unexpected identical sheep");
        assertFalse(sheep1.getHerder() == sheep2.getHerder(), "Unexpected identical herders");
    }
}
