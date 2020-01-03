package com.baeldung.jackson.advancedannotations;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = POJOBuilderBean.BeanBuilder.class)
public class POJOBuilderBean {
    private int identity;
    private String beanName;

    @JsonPOJOBuilder(buildMethodName = "createBean", withPrefix = "construct")
    public static class BeanBuilder {
        private int idValue;
        private String nameValue;

        public BeanBuilder constructId(int id) {
            idValue = id;
            return this;
        }

        public BeanBuilder constructName(String name) {
            nameValue = name;
            return this;
        }

        public POJOBuilderBean createBean() {
            return new POJOBuilderBean(idValue, nameValue);
        }
    }

    public POJOBuilderBean(int identity, String beanName) {
        this.identity = identity;
        this.beanName = beanName;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}