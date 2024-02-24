package com.baeldung.cloning.deep;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Person implements Cloneable {

    private String firstName;
    private String lastName;
    private Address address;

    public Person(Person personToBeCloned) {
        Address addressToBeCloned = personToBeCloned.getAddress();

        this.firstName = personToBeCloned.getFirstName();
        this.lastName = personToBeCloned.getLastName();
        this.address = new Address(addressToBeCloned.getStreetName(), addressToBeCloned.getCityName());
    }

    @Override
    public Person clone() {
        return new Person(this);
    }
}
