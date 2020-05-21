package com.baeldung.properties.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    private String someInitialValue;
    private String anotherManagedValue;

    public InitializerBean(@Value("someInitialValue") String someInitialValue, @Value("anotherValue") String anotherManagedValue) {
        this.someInitialValue = someInitialValue;
        this.anotherManagedValue = anotherManagedValue;
    }

    public ClassNotManagedBySpring initClass() {
        return new ClassNotManagedBySpring(this.someInitialValue, this.anotherManagedValue);
    }

}
