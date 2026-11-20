package com.baeldung.sonarqubeandjacoco.transientorserializable;

import java.io.Serializable;

public class UserPreferences implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient PreferenceService service;

    public PreferenceService getService() {
        return service;
    }

    public void setService(PreferenceService service) {
        this.service = service;
    }
}
