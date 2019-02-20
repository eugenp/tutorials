package com.baeldung.jpacriteria.entity;

import javax.persistence.*;

@Entity(name = "mobile_phones")
public class MobilePhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private int ramInGb;

    @Column
    private int androidVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRamInGb() {
        return ramInGb;
    }

    public void setRamInGb(int ramInGb) {
        this.ramInGb = ramInGb;
    }

    public int getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(int androidVersion) {
        this.androidVersion = androidVersion;
    }
}
