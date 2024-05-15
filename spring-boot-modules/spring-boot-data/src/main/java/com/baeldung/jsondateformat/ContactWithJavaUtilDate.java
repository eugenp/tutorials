package com.baeldung.jsondateformat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ContactWithJavaUtilDate {

    private String name;
    private String address;
    private String phone;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public ContactWithJavaUtilDate() {
    }

    public ContactWithJavaUtilDate(String name, String address, String phone, Date birthday, Date lastUpdate) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.lastUpdate = lastUpdate;
    }
}
