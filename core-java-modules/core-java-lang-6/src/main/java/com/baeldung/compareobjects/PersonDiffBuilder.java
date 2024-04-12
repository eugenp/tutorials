package com.baeldung.compareobjects;

import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PersonDiffBuilder {
    public static DiffResult compare(Person first, Person second) {
        DiffBuilder diffBuilder = new DiffBuilder(first, second, ToStringStyle.DEFAULT_STYLE).append("person", first.getFirstName(), second.getFirstName())
            .append("lastName", first.getLastName(), second.getLastName())
            .append("streetAddress", first.getAddress()
                .getStreetAddress(), second.getAddress()
                .getStreetAddress())
            .append("city", first.getAddress()
                .getCity(), second.getAddress()
                .getCity())
            .append("postalCode", first.getAddress()
                .getPostalCode(), second.getAddress()
                .getPostalCode())
            .append("age", first.getAge(), second.getAge());

        for (int i = 0; i < first.getPhoneNumbers()
            .size(); i++) {
            diffBuilder.append("phoneNumbers[" + i + "].number", first.getPhoneNumbers()
                .get(i)
                .getNumber(), second.getPhoneNumbers()
                .get(i)
                .getNumber());
        }
        return diffBuilder.build();
    }
}
