package com.baeldung.test.cloning;

import com.baeldung.cloning.domain.Address;
import com.baeldung.cloning.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class DemoShallowCloningUnitTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoShallowCloningUnitTest.class);
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
    public void shallowCloneObject_noNewReference() throws CloneNotSupportedException {

        Person personShallowCopy = person.createShallowClone();
        personShallowCopy.setAge(45);
        Address address = personShallowCopy.getAddress();
        address.setCity("Chicago City");
        address.setZip("60007");

        LOGGER.info("personShallowCopy obj --\n" + personShallowCopy + "\n"
                + "person obj --\n" + person);

        assertEquals(
                "Shallow cloning failed"
                , personShallowCopy.getAddress().getCity()
                , person.getAddress().getCity()
        );
        assertEquals(
                "Shallow cloning failed"
                , personShallowCopy.getAddress().getZip()
                , person.getAddress().getZip()
        );
     }
}
