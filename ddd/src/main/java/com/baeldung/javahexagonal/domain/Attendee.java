package com.baeldung.javahexagonal.domain;

public class Attendee {

    private final String name;

    private final String mailAddress;

    public Attendee(String name, String mailAddress) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (mailAddress == null) {
            throw new IllegalArgumentException("mailAddress must not be null");
        }
        if (!mailAddress.contains("@")) {
            throw new IllegalArgumentException("mailAddress is not valid");
        }

        this.name = name;
        this.mailAddress = mailAddress;
    }

    public String getName() {
        return name;
    }

    public String getMailAddress() {
        return mailAddress;
    }
}
