package com.baeldung.cloning.deep;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Address implements Cloneable {

    private String streetName;
    private String cityName;

}