package com.baeldung.shallowDeepCopyExamples.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContactInfo implements Cloneable {
    private int countryCode;

    private String phoneNumber;

    private String emailId;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
