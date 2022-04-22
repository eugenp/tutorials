package com.baeldung.math.swap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SwappingVariablesUnitTest {

    @Test
    public void swapObjectsUsingMethod() {
        
        String a = "a";
        String b = "b";        
        
        swap(a, b);
        
        Assertions.assertFalse(a.equals("b"));
        Assertions.assertFalse(b.equals("a"));
    }
    
    @Test
    public void swapObjectsInWrapperUsingMethod() {
        
        Wrapper a = new Wrapper("a");
        Wrapper b = new Wrapper("b");        
        
        swap(a, b);
        
        Assertions.assertTrue(a.string.equals("b"));
        Assertions.assertTrue(b.string.equals("a"));
    }
    
    @Test
    public void swapIntegersUsingAdditionSubstraction() {
        
        int a = 5;
        int b = 10;              
            
        a = a + b;
        b = a - b;
        a = a - b;
                
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    @Test
    public void swapIntegersUsingMultiplicationDivision() {
        
        int a = 5;
        int b = 10;              
            
        a = a * b;
        b = a / b;
        a = a / b;
                
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    @Test
    public void swapIntegersWithOverflow() {
        
        int a = Integer.MAX_VALUE;
        int b = 10;
                
        a = a * b;
        b = a / b;
        a = a / b;
        
        Assertions.assertTrue(a == 10);
        Assertions.assertFalse(b == Integer.MAX_VALUE);
    }
    
    @Test
    public void swapChars() {
        
        char a = 'a';
        char b = 'b';
                
        a = (char)(a * b);
        b = (char)(a / b);
        a = (char)(a / b);
        
        Assertions.assertTrue(a == 'b');
        Assertions.assertTrue(b == 'a');
    }
    
    @Test
    public void swapIntegersUsingXor() {
        
        int a = 5;
        int b = 10;
                
        a = a ^ b;  
        b = a ^ b;
        a = a ^ b;
        
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    @Test
    public void swapIntegersUsingXorSingleLine() {
        
        int a = 5;
        int b = 10;
                
        a = a ^ b ^ (b = a);
        
        Assertions.assertTrue(a == 10);
        Assertions.assertTrue(b == 5);
    }
    
    @Test
    public void swapIntegersUsingAdditionSubstractionSingleLine() {
        
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
