package com.baeldung.resttemplate.https.client.config;

public class SecureRestTemplateProperties {

    String trustStore = "trustore.jks";
    char[] trustStorePassword = "password".toCharArray();
    String protocol = "TLSv1.2";

    public String getTrustStore() {
        return trustStore;
    }

    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    public char[] getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(char[] trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}
