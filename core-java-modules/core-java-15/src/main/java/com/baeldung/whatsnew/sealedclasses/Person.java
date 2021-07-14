package com.baeldung.whatsnew.sealedclasses;

import java.util.Date;

public sealed class Person permits Employee, Manager {
    /**
     * Demonstration of pattern matching for instanceof
     *
     * @param person A Person object
     * @return
     */
    public static void patternMatchingDemo(Person person) {
        if(person instanceof Employee employee) {
            Date hiredDate = employee.getHiredDate();
        }

        if(person instanceof Employee employee && employee.getHiredDate() != null) {
            Date hiredDate = employee.getHiredDate();
        }
    }
}
