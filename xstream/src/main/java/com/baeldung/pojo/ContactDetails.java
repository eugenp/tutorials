package com.baeldung.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ContactDetails")
public class ContactDetails {

    private String mobile;

    private String landline;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

}
