package com.baeldung.deepshallowcopy.cloneable;

import lombok.*;

@Data
@AllArgsConstructor
public class Address implements Cloneable{
    private String street;
    private String city;
    private String state;

    @Override
    public Address clone() {
        try {
            return (Address) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
