package com.baeldung.shallowvsdeepcopy;

public class Customer {
    private String first;
    private String last;
    private ContactInfo contactInfo;

    public Customer makeAShallowCopy() {
        Customer copy = new Customer();
        copy.setFirst(this.first);
        copy.setLast(this.last);
        copy.setContactInfo(this.contactInfo);
        return copy;
    }
    
    public Customer makeADeepCopy() {
        Customer copy = new Customer();
        copy.setFirst(this.first);
        copy.setLast(this.last);
        copy.setContactInfo(this.contactInfo.makeACopy());
        return copy;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}
