package com.baeldung.autofactory.model;

/**
 * @author aiet
 */
public class Camera {

    private final String manufacturer;
    private final String serial;

    public Camera(String manufacturer, String serial) {
        this.manufacturer = manufacturer;
        this.serial = serial;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getSerial() {
        return serial;
    }

}
