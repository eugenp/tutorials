package com.baeldung.shallowDeepCopyExamples;

import java.util.Collections;
import com.baeldung.shallowDeepCopyExamples.pojo.Person;

public class ShallowCopyExample {
    
    public Person copyPersonObjectByVariableAssignment(Person firstPerson) {
        Person secondPerson = firstPerson;
        secondPerson.setFirstName("Mickey");
        secondPerson.setAge(12);
        secondPerson.getContactInfo().setPhoneNumber("9839098390");
        return secondPerson;
    }

    public Person copyPersonObjectUsingClone(Person firstPerson) throws CloneNotSupportedException {
        Person secondPerson = (Person) firstPerson.clone();
        secondPerson.setLastName("Parker");
        secondPerson.setAge(25);
        secondPerson.setAliasNames(Collections.singletonList("superhero"));
        secondPerson.getContactInfo().setPhoneNumber("9839098390");
        return secondPerson;
    }
}
