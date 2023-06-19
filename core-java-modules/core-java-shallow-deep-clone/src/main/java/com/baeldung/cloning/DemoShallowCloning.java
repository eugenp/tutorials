package com.baeldung.cloning;

import com.baeldung.cloning.domain.Address;
import com.baeldung.cloning.domain.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoShallowCloning {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoShallowCloning.class);

    public static void main(String[] args) {
        //create person object
        Person person = new Person("Adam", 40, Boolean.TRUE);
        //create Address object
        Address address = new Address("Jackson st.", "10001", "1056", "Newyork City");
        person.setAddress(address);
        LOGGER.info("person obj:\n" + person);
        try {
            //create a shallow copy of the person object
            Person personShallowCopy = person.createShallowClone();
            //change the age in the shallow copy, Adam turns 45
            personShallowCopy.setAge(45);
            //change the address in the shallow copy, Adam moves to Chicago :)
            Address address1 = personShallowCopy.getAddress();
            address1.setCity("Chicago City");
            address1.setZip("60007");

            LOGGER.info("personShallowCopy obj --\n" + personShallowCopy + "\n"
                    + "person obj --\n" + person);
        } catch (CloneNotSupportedException e) {

            LOGGER.error("Cannot clone", e);
        }
    }
}
