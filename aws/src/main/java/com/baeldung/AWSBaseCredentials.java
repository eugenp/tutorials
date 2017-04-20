package com.baeldung;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

public abstract class AWSBaseCredentials {
    protected AWSCredentialsProvider credentialsProvider;

    public AWSBaseCredentials() {
        BasicAWSCredentials credentials = new BasicAWSCredentials("your_access_key_id", "your_secret_access_key");

        credentialsProvider = new AWSStaticCredentialsProvider(credentials);

//        credentialsProvider = new ProfileCredentialsProvider("baeldung");

    }
}
