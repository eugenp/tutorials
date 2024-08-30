package com.example.javadeepandshadowcopy;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Address implements Cloneable {
     String country;
     String city;

    public Address(Address address) {
        this.country = address.country;
        this.city = address.city;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
