package com.baeldung.ambiguousmethodcall;

class MyClass {
    
    Integer myMethod(Integer i) {
        return 1;
    }
    
    String myMethod(MyOwnType myOwnType) {
        return "baeldung";
    }

}
