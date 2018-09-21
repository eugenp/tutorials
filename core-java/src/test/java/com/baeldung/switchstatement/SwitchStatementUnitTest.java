package com.baeldung.switchstatement;

import org.junit.Test;

import org.junit.Assert;

public class SwitchStatementUnitTest {
    private SwitchStatement s = new SwitchStatement();

    
    @Test
    public void whenDog_thenDomesticAnimal() {
        
        String animal = "DOG";
        Assert.assertEquals("domestic animal", s.exampleOfSwitch(animal));
    }
    
    @Test
    public void whenNoBreaks_thenGoThroughBlocks() {
        String animal = "DOG";
        Assert.assertEquals("unknown animal", s.forgetBreakInSwitch(animal));
    }
    
    @Test(expected=NullPointerException.class)
    public void whenSwitchAgumentIsNull_thenNullPointerException() {
        String animal = null;
        Assert.assertEquals("domestic animal", s.exampleOfSwitch(animal));
    }
    
       
    @Test
    public void whenCompareStrings_thenByEqual() {
        String animal = new String("DOG");
        Assert.assertEquals("domestic animal", s.exampleOfSwitch(animal));
    }
    
  
}
