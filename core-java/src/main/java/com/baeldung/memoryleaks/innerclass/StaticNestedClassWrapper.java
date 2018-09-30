package com.baeldung.memoryleaks.innerclass;


public class StaticNestedClassWrapper {
    private BulkyObject bulkyObject = new BulkyObject();
    
    public static class StaticNestedClass {
        
    }
}
