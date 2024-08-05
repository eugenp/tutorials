package com.baeldung.dddjmolecules.article;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
public class Slug {
    private final String value;

    Slug(String value) {
        Assert.isTrue(value != null, "Article's slug cannot be null!");
        Assert.isTrue(value.length() >= 5, "Article's slug should be at least 5 characters long!");
        this.value = value;
    }

    public String value() {
        return value;
    }
}
