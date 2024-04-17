package com.baeldung.prototypebean.dynamicarguments;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class EmployeeBeanUsingLookUp {

    @Lookup
    public Employee getEmployee(String arg) {
        return null;
    }

}
