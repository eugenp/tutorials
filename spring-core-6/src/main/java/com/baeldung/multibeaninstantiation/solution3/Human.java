package com.baeldung.multibeaninstantiation.solution3;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class Human implements InitializingBean {

    private Person personOne;

    private Person personTwo;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(personOne, "Harold is alive!");
        Assert.notNull(personTwo, "John is alive!");
    }

    /* Setter injection */
    @Autowired
    public void setPersonOne(Person personOne) {
        this.personOne = personOne;
        this.personOne.setFirstName("Harold");
        this.personOne.setSecondName("Finch");
    }

    @Autowired
    public void setPersonTwo(Person personTwo) {
        this.personTwo = personTwo;
        this.personTwo.setFirstName("John");
        this.personTwo.setSecondName("Reese");
    }
}