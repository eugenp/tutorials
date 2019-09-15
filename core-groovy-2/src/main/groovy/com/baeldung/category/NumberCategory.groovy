package com.baeldung.category;

import groovy.lang.Category

@Category(Number)
class NumberCategory {
    
    public Number cube() {
        return this*this*this
    }
    
    public Number toThePower(Number exponent) {
        return Math.pow(this, exponent)
    }

}
