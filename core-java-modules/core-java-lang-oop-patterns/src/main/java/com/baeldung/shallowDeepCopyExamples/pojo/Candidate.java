package com.baeldung.shallowDeepCopyExamples.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Candidate implements Cloneable {
    private String firstName;

    private String lastName;

    private int age;

    private ContactInfo contactInfo;

    @Override
    public Candidate clone() throws CloneNotSupportedException {
        Candidate cloned = (Candidate) super.clone();
        cloned.setContactInfo((ContactInfo) cloned.getContactInfo().clone());
        return cloned;
    }
}
