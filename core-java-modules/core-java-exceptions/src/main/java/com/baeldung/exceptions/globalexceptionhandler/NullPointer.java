package com.baeldung.exceptions.globalexceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullPointer {

    private static Logger LOGGER = LoggerFactory.getLogger(NullPointer.class);

    public static void main(String[] args) {

        Person personObj = null;

        try {
            String name = personObj.personName; // Accessing the field of a null object
            personObj.personName = "Jon Doe"; // Modifying the field of a null object
        } catch (NullPointerException e) {
            LOGGER.error("NullPointerException caught!");
        }

    }
}

class Person {

    public String personName;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

}
