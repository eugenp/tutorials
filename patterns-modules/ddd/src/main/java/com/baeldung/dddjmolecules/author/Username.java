package com.baeldung.dddjmolecules.author;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
public record Username(String value) {
    public Username {
        Assert.isTrue(value != null && !value.isBlank(), "Username value cannot be null or blank.");
    }

}
