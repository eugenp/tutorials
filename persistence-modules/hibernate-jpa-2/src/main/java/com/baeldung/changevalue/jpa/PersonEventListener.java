package com.baeldung.changevalue.jpa;

import com.baeldung.changevalue.entity.Person;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.apache.commons.lang3.StringUtils;

public class PersonEventListener<T extends Person> {

    @PrePersist
    @PreUpdate
    private void changeNameToUpperCase(T person) {
        person.setName(StringUtils.upperCase(person.getName()));
    }

}
