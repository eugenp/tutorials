package com.baeldung.patterns.es.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserCreatedEvent extends Event {

    private String userId;
    private String firstName;
    private String lastName;

}
