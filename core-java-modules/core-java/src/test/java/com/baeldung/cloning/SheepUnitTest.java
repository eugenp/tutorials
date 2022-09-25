package com.baeldung.cloning;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

public class SheepUnitTest {
    
    @Test
    public void givenSheepAndHerder_whenShallowCopySheep_thenHerderIdentical() {
        Herder herderJohn = new Herder("John");
        Sheep originalSheep = new Sheep(1, herderJohn);
        
        Sheep clonedSheep = null;
        try {
            clonedSheep = (Sheep) originalSheep.clone();
        } catch (CloneNotSupportedException e) {
            fail("Unexpected failure while cloning sheep: ", e);
        }
        
        assertFalse(originalSheep == clonedSheep, "Unexpected identical sheep");
        assertTrue(originalSheep.getHerder() == clonedSheep.getHerder(), "Unexpected different herders");
    }
    
    @Test
    public void givenSheepAndHerder_whenDeepCopySheep_thenHerdersDifferent() {
        Herder herderJohn = new Herder("John");
        Sheep originalSheep = new Sheep(3, herderJohn);
        
        Sheep clonedSheep = null;
        try {
            clonedSheep = (Sheep) originalSheep.deepCopy();
        } catch (CloneNotSupportedException e) {
            fail("Unexpected failure while cloning sheep: ", e);
        }
        
        assertFalse(originalSheep == clonedSheep, "Unexpected identical sheep");
        assertFalse(originalSheep.getHerder() == clonedSheep.getHerder(), "Unexpected identical herders");
    }
}
