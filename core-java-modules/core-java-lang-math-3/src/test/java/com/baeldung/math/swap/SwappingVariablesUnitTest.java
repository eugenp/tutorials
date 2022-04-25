package com.baeldung.math.swap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SwappingVariablesUnitTest {

    @Test
    public void givenTwoStrings_whenSwappingInMethod_thenFails() {
        
        String a = "a";
        String b = "b";        
        
        swap(a, b);
        
        Assertions.assertFalse(a.equals("b"));
        Assertions.assertFalse(b.equals("a"));
    }
    
    @Test
    public void givenTwoWrappers_whenSwappingInMethod_thenSuccess() {
        
        Wrapper a = new Wrapper("a");
        Wrapper b = new Wrapper("b");        
        
        swap(a, b);
        
        Assertions.assertTrue(a.string.equals("b"));
        Assertions.assertTrue(b.string.equals("a"));
    }
    
    @Test
    public void givenTwoIntegers_whenSwappingUsingAdditionSubstraction_thenSuccess() {
        
        int a = 5;
        int b = 10;              
            
        a = a + b;
        b = a - b;
        a = a - b;
                
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    @Test
    public void givenTwoIntegers_whenSwappingUsingMultiplicationDivision_thenSuccess() {
        
        int a = 5;
        int b = 10;              
            
        a = a * b;
        b = a / b;
        a = a / b;
                
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    @Test
    public void givenTwoIntegers_whenSwappingWithOverflow_thenFails() {
        
        int a = Integer.MAX_VALUE;
        int b = 10;
                
        a = a * b;
        b = a / b;
        a = a / b;
        
        Assertions.assertTrue(a == 10);
        Assertions.assertFalse(b == Integer.MAX_VALUE);
    }
    
    @Test
    public void givenTwoChars_whenSwappingWithCast_thenSuccess() {
        
        char a = 'a';
        char b = 'b';
                
        a = (char)(a * b);
        b = (char)(a / b);
        a = (char)(a / b);
        
        Assertions.assertTrue(a == 'b');
        Assertions.assertTrue(b == 'a');
    }
    
    @Test
    public void givenTwoIntegers_whenSwappingUsingXor_thenSuccess() {
        
        int a = 5;
        int b = 10;
                
        a = a ^ b;  
        b = a ^ b;
        a = a ^ b;
        
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    @Test
    public void givenTwoIntegers_whenSwappingUsingSingleLineXor_thenSuccess() {
        
        int a = 5;
        int b = 10;
                
        a = a ^ b ^ (b = a);
        
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    @Test
    public void givenTwoIntegers_whenSwappingUsingAdditionSubstractionSingleLine_thenSuccess() {
        
        int a = 5;
        int b = 10;
                
        b = (a + b) - (a = b);
        
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    /**
     * Illustrates that swapping in a method doesn't work
     */
    private void swap(String a, String b) {
                
        String temp = b;
        b = a;
        a = temp;
    }
    
    /**
     * Illustrates swapping in a method with Wrapper class
     */
    private void swap(Wrapper a, Wrapper b) {
        
        String temp = b.string;
        b.string = a.string;
        a.string = temp;        
    }

    class Wrapper {
        
        public String string;

        public Wrapper(String string) {
            
            this.string = string;
        }
        
        
    }
}
