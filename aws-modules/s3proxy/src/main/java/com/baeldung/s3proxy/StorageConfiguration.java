package com.baeldung.s3proxy;

import java.net.URI;

import org.gaul.s3proxy.S3Proxy;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
@Profile("!local && !test")
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfiguration {

    private final StorageProperties storageProperties;

    public StorageConfiguration(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    @Profile("azure")
    public BlobStore azureBlobStore() {
        return ContextBuilder
            .newBuilder("azureblob")
            .credentials(storageProperties.getIdentity(), storageProperties.getCredential())
            .build(BlobStoreContext.class)
            .getBlobStore();
    }

    @Bean
    @Profile("gcp")
    public BlobStore gcpBlobStore() {
        return ContextBuilder
            .newBuilder("google-cloud-storage")
            .credentials(storageProperties.getIdentity(), storageProperties.getCredential())
            .build(BlobStoreContext.class)
            .getBlobStore();
    }

    @Bean
    public S3Proxy s3Proxy(BlobStore blobStore) {
        return S3Proxy
            .builder()
            .blobStore(blobStore)
            .endpoint(URI.create(storageProperties.getProxyEndpoint()))
            .build();
    }

    @Bean
    public S3Client s3Client() {
        S3Configuration s3Configuration = S3Configuration
            .builder()
            .checksumValidationEnabled(false)
            .build();
        AwsCredentials credentials = AwsBasicCredentials.create(
            storageProperties.getIdentity(),
            storageProperties.getCredential()
        );
        return S3Client
            .builder()
            .region(Region.of(storageProperties.getRegion()))
            .endpointOverride(URI.create(storageProperties.getProxyEndpoint()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .serviceConfiguration(s3Configuration)
            .build();
    }

}