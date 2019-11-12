package com.baeldung.hexagonal.outer;

import javax.persistence.*;

@Entity
@Table(name = "ip_locations")
public class IpLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String ip;
    private double longitude;
    private double latitude;

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}