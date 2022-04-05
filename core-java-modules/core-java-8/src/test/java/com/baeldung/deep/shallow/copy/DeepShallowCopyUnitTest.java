package com.baeldung.deep.shallow.copy;

import org.junit.Assert;
import org.junit.Test;

public class DeepShallowCopyUnitTest {

    @Test
    public void shouldHaveEqualValues_whenReferenceCopyIsCreated() throws CloneNotSupportedException {
        Person newPersonObject = new Person("John", 24, new Contact("johndoe@domain.com", "+3477686868"));
        Person newPersonObjectReference = newPersonObject;
        Assert.assertEquals(newPersonObject.getName(), newPersonObjectReference.getName());
        Assert.assertEquals(newPersonObject.getContactDetail().getEmailAddress(), newPersonObjectReference.getContactDetail().getEmailAddress());
    }

    @Test
    public void shouldHaveDifferentValues_whenDeepCopyIsCreated() throws CloneNotSupportedException {
        Person newPersonObject = new Person("John", 24, new Contact("johndoe@domain.com", "+3477686868"));
        Person secondPersonObject = (Person) newPersonObject.clone();
        secondPersonObject.setName("Jane");
        Assert.assertNotEquals(newPersonObject.getName(),secondPersonObject.getName());
        secondPersonObject.getContactDetail().setEmailAddress("janedoe@domain.com");
        Assert.assertNotEquals(newPersonObject.getContactDetail().getEmailAddress(),secondPersonObject.getContactDetail().getEmailAddress());
    }


}
