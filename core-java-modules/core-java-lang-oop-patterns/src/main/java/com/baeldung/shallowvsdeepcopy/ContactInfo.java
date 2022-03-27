package com.baeldung.shallowvsdeepcopy;

public class ContactInfo {
    private String gsmNo;
    private String email;
    
    public ContactInfo makeACopy() {
        ContactInfo copy = new ContactInfo();
        copy.setEmail(email);
        copy.setGsmNo(gsmNo);
        return copy;
    }

    public String getGsmNo() {
        return gsmNo;
    }

    public void setGsmNo(String gsmNo) {
        this.gsmNo = gsmNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
