package com.baeldung.jackson.try1;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = RestLoaderRequestDeserializer.class)
// @Produces(MediaType.APPLICATION_JSON)
public class RestLoaderRequest<T extends IEntity> implements Serializable {
    private T entity; // entity to load field to
    private String className; // actual class of entity
    private String fieldName; // fieldName to lazy REST load

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(final T entity) {
        this.entity = entity;
    }

}