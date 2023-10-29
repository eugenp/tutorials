package com.baeldung.gcp.firebase.publisher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "gcp.firebase")
public class FirebaseProperties {
    private Resource serviceAccount;
    

    /**
     * @return the serviceAccount
     */
    public Resource getServiceAccount() {
        return serviceAccount;
    }

    /**
     * @param serviceAccount the serviceAccount to set
     */
    public void setServiceAccount(Resource serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

}
