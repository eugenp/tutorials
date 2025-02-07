package com.baeldung.s3proxy;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.baeldung.storage")
public class StorageProperties {

    private String identity;

    private String credential;

    private String region;

    private String bucketName;

    private String proxyEndpoint;

    private String localFileBaseDirectory;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getProxyEndpoint() {
        return proxyEndpoint;
    }

    public void setProxyEndpoint(String proxyEndpoint) {
        this.proxyEndpoint = proxyEndpoint;
    }

    public String getLocalFileBaseDirectory() {
        return localFileBaseDirectory;
    }

    public void setLocalFileBaseDirectory(String localFileBaseDirectory) {
        this.localFileBaseDirectory = localFileBaseDirectory;
    }

}