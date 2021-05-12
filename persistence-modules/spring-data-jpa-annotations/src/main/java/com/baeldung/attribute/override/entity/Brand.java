package com.baeldung.attribute.override.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;

@Embeddable
public class Brand {
    private String name;
    private LocalDate foundationDate;
    @Embedded
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }
}
