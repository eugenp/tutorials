package com.baeldung.test.cloning;

import com.baeldung.cloning.domain.Address;
import com.baeldung.cloning.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotEquals;

public class DemoDeepCloningUnitTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoDeepCloningUnitTest.class);
    Person person = null;

    @Before
    public void createPersonObj() {
        //create person object
        this.person = new Person("Adam", 40, Boolean.TRUE);
        //create Address object
        Address address = new Address("Jackson st.", "10001", "1056", "Newyork City");
        this.person.setAddress(address);
    }

    @Test
    public void deepCloneObject_newReference() throws CloneNotSupportedException {

        Person personDeepCopy = person.createDeepClone();
        personDeepCopy.setAge(45);
        Address address = personDeepCopy.getAddress();
        address.setCity("Chicago City");
        address.setZip("60007");

        LOGGER.info("personDeepCopy obj --\n" + personDeepCopy + "\n"
                + "person obj --\n" + person);

        assertNotEquals(
                "Deep cloning failed"
                , personDeepCopy.getAddress().getCity()
                , person.getAddress().getCity()
        );
        assertNotEquals(
                "Deep cloning failed"
                , personDeepCopy.getAge()
                , person.getAge()
        );

        assertNotEquals(
                "Deep cloning failed"
                , personDeepCopy.getAddress().getZip()
                , person.getAddress().getZip()
        );
    }
}
