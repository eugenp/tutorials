package com.baeldung.shallowvsdeepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ShallowCopyUnitTest {


    @Test
    public void whenShallowCopying_thenObjectsShouldNotBeSame() {
        
        Address address = new Address("108", "Sector 7", "Los Angeles", "USA");
        Person originalPerson = new Person("John", address);
        
        Person copiedPerson = new Person(originalPerson.getName(), originalPerson.getAddress());
        
        assertThat(copiedPerson).isNotSameAs(originalPerson);
}
    
    @Test
    public void whenModifyingOriginalObject_ThenCopyShouldChange() {
     
        Address address = new Address("108", "Sector 7", "Los Angeles", "USA");
        Person originalPerson = new Person("John", address);
        
        Person copiedPerson = new Person(originalPerson.getName(), originalPerson.getAddress());

        address.setCountry("Argentina");
        assertThat(copiedPerson.getAddress().getCountry())
          .isEqualTo(originalPerson.getAddress().getCountry());
}    
}
