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
    private double latititude;
    private double longitude;

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public double getLatititude() {
        return latititude;
    }

    public void setLatititude(double latititude) {
        this.latititude = latititude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CabLocation [cabId=" + cabId + ", latititude=" + latititude + ", longitude=" + longitude + "]";
    }

}
