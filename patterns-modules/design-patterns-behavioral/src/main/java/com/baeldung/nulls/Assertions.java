package com.baeldung.nulls;

public class Assertions {
    
    public void accept(Object param){
        assert param != null;
        
        doSomething(param);
    }

    private void doSomething(Object param) {
    }
}
