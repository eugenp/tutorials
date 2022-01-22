package com.baeldung.dynamicvalidation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ContactInfoExpression {

    @Id
    @Column(name = "expression_type")
    private String type;
    private String pattern;

    public ContactInfoExpression() {

    }

    public ContactInfoExpression(final String type, final String pattern) {
        this.type = type;
        this.pattern = pattern;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

}
