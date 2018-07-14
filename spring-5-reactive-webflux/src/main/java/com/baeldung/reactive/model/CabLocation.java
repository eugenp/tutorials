/**
 * 
 */
package com.baeldung.reactive.model;

import java.io.Serializable;

/**
 * @author swapanpramanick2004
 *
 */
public class CabLocation implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -3923503044822400093L;

    private String cabId;
    private double latitude;
    private double longitude;
    
    // default constructor
    public CabLocation() {
        // create a CabLocation with empty values
    }

    public CabLocation(String cabId, double latt, double longt) {
        this.cabId = cabId;
        this.latitude = latt;
        this.longitude = longt;
    }

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latititude) {
        this.latitude = latititude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CabLocation [cabId=" + cabId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }

}
