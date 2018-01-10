package com.baeldung.beans.model;

import lombok.Getter;

public class CFVersion {

    @Getter
    private final String version;

    public CFVersion(String version) {
        this.version = version;
    }

}
