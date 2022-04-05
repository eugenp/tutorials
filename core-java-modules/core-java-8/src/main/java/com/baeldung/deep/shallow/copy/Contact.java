package com.baeldung.deep.shallow.copy;

public class Contact implements Cloneable {
    private String emailAddress;
    private String phoneNumber;

    public Contact(String emailAddress, String phoneNumber) {
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Contact contact = (Contact) super.clone();
        return contact;
    }
}