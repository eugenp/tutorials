package com.baeldung.jsondateformat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PlainContact {

    private String name;
    private String address;
    private String phone;

    private LocalDate birthday;

    private LocalDateTime lastUpdate;

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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public PlainContact() {
    }

    public PlainContact(String name, String address, String phone, LocalDate birthday, LocalDateTime lastUpdate) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.lastUpdate = lastUpdate;
    }
}
