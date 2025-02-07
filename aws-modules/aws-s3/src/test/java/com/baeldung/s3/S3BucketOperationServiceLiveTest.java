package com.baeldung.s3;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import net.bytebuddy.utility.RandomString;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

@TestInstance(Lifecycle.PER_CLASS)
class S3BucketOperationServiceLiveTest {
    
    private static final DockerImageName LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack:3.8");
    private static final LocalStackContainer localStackContainer = new LocalStackContainer(LOCALSTACK_IMAGE)
        .withCopyFileToContainer(MountableFile.forClasspathResource("init-s3-bucket.sh", 0744), "/etc/localstack/init/ready.d/init-s3-bucket.sh")
        .withServices(Service.S3)
        .waitingFor(Wait.forLogMessage(".*Executed init-s3-bucket.sh.*", 1));

    static {
        localStackContainer.start();
    }
    
    private S3Client s3Client;
    private S3BucketOperationService s3BucketOperationService;
    
    @BeforeAll
    void setup() {
        String accessKey = localStackContainer.getAccessKey();
        String secretKey = localStackContainer.getSecretKey();
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        s3Client = S3Client
            .builder()
            .region(Region.of(localStackContainer.getRegion()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .endpointOverride(localStackContainer.getEndpoint())
            .build();
        
        s3BucketOperationService = new S3BucketOperationService(s3Client);
    }
    
    // Bucket name as configured in src/test/resources/init-s3-bucket.sh
    private static final String DEFAULT_BUCKET_NAME = "baeldung-bucket";
    
    @Test
    void whenBucketExists_thenResponseIsTrue() {
        boolean result = s3BucketOperationService.bucketExists(DEFAULT_BUCKET_NAME);
        assertThat(result)
            .isTrue();
    }
    
    @Test
    void whenBucketDoesNotExist_thenResponseIsFalse() {
        String bucketName = RandomString.make().toLowerCase();
        boolean result = s3BucketOperationService.bucketExists(bucketName);
        assertThat(result)
            .isFalse();
    }
    
    @Test
    void whenCreateBucketCalled_thenBucketCreatedInS3() {
        String bucketName = RandomString.make().toLowerCase();
        s3BucketOperationService.createBucket(bucketName);
        
        boolean isBucketCreated = s3BucketOperationService.bucketExists(bucketName);
        assertThat(isBucketCreated)
            .isTrue();
    }
    
    @Test
    void whenListBucketsCalled_thenCreatedBucketsReturned() {
        List<String> bucketNames = new ArrayList<>();
        int count = 10;
        for (int i = 0; i < count; i++) {
            String bucketName = RandomString.make().toLowerCase();
            s3BucketOperationService.createBucket(bucketName);
            bucketNames.add(bucketName);
        }
        
        List<Bucket> buckets = s3BucketOperationService.listBuckets();
        List<String> retreivedBucketNames = buckets.stream().map(Bucket::name).toList();
        
        assertThat(retreivedBucketNames)
            .containsAll(bucketNames);
    }
    
    @Test
    void whenDeleteBucketCalled_thenS3BucketIsDeleted() {
        String bucketName = RandomString.make().toLowerCase();
        s3BucketOperationService.createBucket(bucketName);
        
        boolean isBucketPresent = s3BucketOperationService.bucketExists(bucketName);
        assertThat(isBucketPresent)
            .isTrue();
        
        s3BucketOperationService.deleteBucket(bucketName);
        
        isBucketPresent = s3BucketOperationService.bucketExists(bucketName);
        assertThat(isBucketPresent)
            .isFalse();
    }

}