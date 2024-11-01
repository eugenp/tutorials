package com.baeldung.s3proxy;

import java.net.URI;
import java.util.Properties;

import org.gaul.s3proxy.AuthenticationType;
import org.gaul.s3proxy.S3Proxy;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Profile("local | test")
@EnableConfigurationProperties(StorageProperties.class)
public class LocalStorageConfiguration {
    
    private final StorageProperties storageProperties;

    public LocalStorageConfiguration(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }
    
    @Bean
    public BlobStore blobStore() {
        Properties properties = new Properties();
        properties.setProperty("jclouds.filesystem.basedir", storageProperties.getLocalFileBaseDirectory());
        return ContextBuilder
            .newBuilder("filesystem")
            .overrides(properties)
            .build(BlobStoreContext.class)
            .getBlobStore();
    }

    @Bean
    public S3Proxy s3Proxy(BlobStore blobStore) {
        return S3Proxy
            .builder()
            .awsAuthentication(AuthenticationType.NONE, null, null)
            .blobStore(blobStore)
            .endpoint(URI.create(storageProperties.getProxyEndpoint()))
            .build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client
            .builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI.create(storageProperties.getProxyEndpoint()))
            .build();
    }

}