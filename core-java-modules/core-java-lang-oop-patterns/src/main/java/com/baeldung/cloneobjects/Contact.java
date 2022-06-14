package com.baeldung.cloneobjects;

public class Contact implements Cloneable {

    private String phone;
    private String email;

    public Contact(String phone, String email) {
        super();
        this.phone = phone;
        this.email = email;
    }

    // let's override the clone() method and make it public so that we can access it from outside the class package
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
