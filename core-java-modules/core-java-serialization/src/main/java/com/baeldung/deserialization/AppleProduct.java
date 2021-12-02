package com.baeldung.deserialization;

import java.io.Serializable;

public class AppleProduct implements Serializable {

    private static final long serialVersionUID = 1234567L; // user-defined (i.e. not default or generated)
    // private static final long serialVersionUID = 7654321L; // user-defined (i.e. not default or generated)

    public String headphonePort;
    public String thunderboltPort;
    public String lightningPort;

    public String getHeadphonePort() {
        return headphonePort;
    }

    public String getThunderboltPort() {
        return thunderboltPort;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLightningPort() {
        return lightningPort;
    }

}