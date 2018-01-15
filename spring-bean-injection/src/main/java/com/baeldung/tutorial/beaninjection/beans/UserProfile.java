package com.baeldung.tutorial.beaninjection.beans;

public class UserProfile {

    private UserCredentials credentials;
    private Demographic demoGraphic;

    public UserProfile(UserCredentials creds) {
        setCredentials(creds);
    }

    public UserCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public Demographic getDemographic() {
        return demoGraphic;
    }

    public void setDemographic(Demographic demoGraphic) {
        this.demoGraphic = demoGraphic;
    }

}
