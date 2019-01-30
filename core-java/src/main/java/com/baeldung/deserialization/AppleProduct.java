package com.baeldung.deserialization;

import java.io.Serializable;

/**
 * @author zn.wang
 */
public class AppleProduct implements Serializable {

    // user-defined (i.e. not default or generated)
    private static final long serialVersionUID = 1234567L;
    // user-defined (i.e. not default or generated)
    // private static final long serialVersionUID = 7654321L;

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

    @Override
    public String toString() {
        return "AppleProduct{" +
                "headphonePort='" + headphonePort + '\'' +
                ", thunderboltPort='" + thunderboltPort + '\'' +
                ", lightningPort='" + lightningPort + '\'' +
                '}';
    }
}