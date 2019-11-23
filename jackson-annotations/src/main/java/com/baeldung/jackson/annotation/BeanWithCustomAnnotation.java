package com.baeldung.jackson.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import com.baeldung.jackson.annotation.BeanWithCustomAnnotation.CustomAnnotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@CustomAnnotation
public class BeanWithCustomAnnotation {
    public int id;
    public String name;
    public Date dateCreated;

    public BeanWithCustomAnnotation() {

    }

    public BeanWithCustomAnnotation(final int id, final String name, final Date dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @JacksonAnnotationsInside
    @JsonInclude(Include.NON_NULL)
    @JsonPropertyOrder({ "name", "id", "dateCreated" })
    public @interface CustomAnnotation {

    }
}
