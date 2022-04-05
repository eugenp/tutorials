package com.baeldung.annotations.componentscanautoconfigure.employee;

import org.springframework.stereotype.Component;

@Component
public class SeniorEmployee {

    @Override
    public String toString() {
        return "Senior Employee" + this.hashCode();
    }
}
