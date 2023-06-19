package com.baeldung.test.cloning;

import com.baeldung.cloning.domain.Address;
import com.baeldung.cloning.domain.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DemoShallowCloningUnitTest {

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

        Person personShallowCopy = person.createShallowClone();
        personShallowCopy.setAge(45);
        Address address = personShallowCopy.getAddress();
        address.setCity("Chicago City");
        address.setZip("60007");

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
