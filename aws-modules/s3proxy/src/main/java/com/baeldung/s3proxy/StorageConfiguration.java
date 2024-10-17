package com.baeldung.s3proxy;

import java.net.URI;

import org.gaul.s3proxy.S3Proxy;
import org.jclouds.ContextBuilder;
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
    public BlobStoreContext azureBlobStoreContext() {
        return ContextBuilder
            .newBuilder("azureblob")
            .credentials(storageProperties.getIdentity(), storageProperties.getCredential())
            .build(BlobStoreContext.class);
    }

    @Bean
    @Profile("gcp")
    public BlobStoreContext gcpBlobStoreContext() {
        return ContextBuilder
            .newBuilder("google-cloud-storage")
            .credentials(storageProperties.getIdentity(), storageProperties.getCredential())
            .build(BlobStoreContext.class);
    }

    @Bean
    public S3Proxy s3Proxy(BlobStoreContext blobStoreContext) {
        return S3Proxy
            .builder()
            .blobStore(blobStoreContext.getBlobStore())
            .endpoint(URI.create(storageProperties.getProxyEndpoint()))
            .build();
    }

    @Bean
    public S3Client s3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(
            storageProperties.getIdentity(),
            storageProperties.getCredential()
        );
        return S3Client
            .builder()
            .region(Region.of(storageProperties.getRegion()))
            .endpointOverride(URI.create(storageProperties.getProxyEndpoint()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();
    }

}