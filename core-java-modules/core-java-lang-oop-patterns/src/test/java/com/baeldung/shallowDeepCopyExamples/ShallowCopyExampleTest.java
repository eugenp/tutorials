package com.baeldung.shallowDeepCopyExamples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.Arrays;
import org.junit.Test;
import com.baeldung.shallowDeepCopyExamples.pojo.ContactInfo;
import com.baeldung.shallowDeepCopyExamples.pojo.Person;

public class ShallowCopyExampleTest {

    ShallowCopyExample shallowCopyExample = new ShallowCopyExample();

    @Test
    public void whenCopyingObjectUsingClone_thenPrimitiveValuesAreUnchanged() throws CloneNotSupportedException {
        Person firstPerson = getTestPerson();
        Person secondPerson = shallowCopyExample.copyPersonObjectUsingClone(firstPerson);
        assertNotEquals(firstPerson,secondPerson);
        assertNotEquals(firstPerson.getLastName(), secondPerson.getLastName());
        assertNotEquals( firstPerson.getAge(), secondPerson.getAge());
        assertEquals("9839098390",firstPerson.getContactInfo().getPhoneNumber());
    }

    @Test
    public void whenCopyingObjectsByVariableAssignment_thenOriginalObjectValuesAreChanged(){
        Person firstPerson = getTestPerson();
        Person secondPerson = shallowCopyExample.copyPersonObjectByVariableAssignment(firstPerson);
        assertEquals(firstPerson,secondPerson);
        assertEquals(firstPerson.getFirstName(), secondPerson.getFirstName());
        assertEquals(12, firstPerson.getAge());
    }

    private Person getTestPerson(){
        return Person.builder()
                     .firstName("Looney")
                     .lastName("Tunes")
                     .aliasNames(Arrays.asList("cartoon","bunny"))
                     .age(5)
                     .contactInfo(ContactInfo.builder().countryCode(80).phoneNumber("9839012345").build())
                     .build();
    }
}