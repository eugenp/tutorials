package com.baeldung.patterns.es.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserAddressAddedEvent extends Event {

    private String city;
    private String state;
    private String postCode;

}
