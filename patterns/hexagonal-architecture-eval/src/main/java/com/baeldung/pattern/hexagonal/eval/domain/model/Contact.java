package main.java.com.baeldung.pattern.hexagonal.eval.domain.model;

public class Contact {

    private ContactName contactName;
    private ContactNumber contactNumber;

    public Contact(ContactName contactName, ContactNumber contactNumber) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public ContactName getContactName() {
        return contactName;
    }

    public void setContactName(ContactName contactName) {
        this.contactName = contactName;
    }

    public ContactNumber getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(ContactNumber contactNumber) {
        this.contactNumber = contactNumber;
    }
}
