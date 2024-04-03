package com.baeldung.learningplatform;

public enum JavaVersion {

    JAVA_8("1.8"), JAVA_9("9"), JAVA_17("17");

    private final String version;


    JavaVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }
}
