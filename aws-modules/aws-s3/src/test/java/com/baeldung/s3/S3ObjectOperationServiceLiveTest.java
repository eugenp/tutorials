package com.baeldung.s3;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.io.TempDir;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@TestInstance(Lifecycle.PER_CLASS)
class S3ObjectOperationServiceLiveTest {

    private static final DockerImageName LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack:3.8");
    private static final LocalStackContainer localStackContainer = new LocalStackContainer(LOCALSTACK_IMAGE)
        .withCopyFileToContainer(MountableFile.forClasspathResource("init-s3-bucket.sh", 0744), "/etc/localstack/init/ready.d/init-s3-bucket.sh")
        .withServices(Service.S3)
        .waitingFor(Wait.forLogMessage(".*Executed init-s3-bucket.sh.*", 1));

    static {
        localStackContainer.start();
    }

    private S3Client s3Client;
    private S3ObjectOperationService s3ObjectOperationService;

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

        s3ObjectOperationService = new S3ObjectOperationService(s3Client);
    }

    // Bucket name as configured in src/test/resources/init-s3-bucket.sh
    private static final String DEFAULT_BUCKET_NAME = "baeldung-bucket";

    @Test
    void whenUploadCalled_thenFileSavedToBucket() throws IOException {
        File fileToUpload = FileGeneratorUtil.generate();
        s3ObjectOperationService.upload(DEFAULT_BUCKET_NAME, fileToUpload);

        boolean isObjectSaved = objectExists(DEFAULT_BUCKET_NAME, fileToUpload.getName());
        assertThat(isObjectSaved)
            .isTrue();
    }

    @Test
    void whenUploadCalledWithMetadata_thenFileAndMetadataSavedToBucket() throws IOException {
        String metadataKey = "company";
        String metadataValue = "Baeldung";
        Map<String, String> metadata = Map.of(metadataKey, metadataValue);
        File fileToUpload = FileGeneratorUtil.generate();

        s3ObjectOperationService.upload(DEFAULT_BUCKET_NAME, fileToUpload, metadata);

        HeadObjectResponse headObjectResponse = s3Client.headObject(request ->
            request
                .bucket(DEFAULT_BUCKET_NAME)
                .key(fileToUpload.getName()));
        Map<String, String> retreivedMetadata = headObjectResponse.metadata();
        assertThat(retreivedMetadata.get(metadataKey))
            .isEqualTo(metadataValue);
    }

    @Test
    void whenDownloadCalled_thenFileDownloadedSuccessfully(@TempDir Path tempDir) throws Exception {
        File sourceFile = FileGeneratorUtil.generate();
        s3ObjectOperationService.upload(DEFAULT_BUCKET_NAME, sourceFile);

        Path downloadPath = tempDir.resolve("downloaded-" + sourceFile.getName());
        s3ObjectOperationService.download(DEFAULT_BUCKET_NAME, sourceFile.getName(), downloadPath);

        assertThat(Files.exists(downloadPath))
            .isTrue();
        assertThat(Files.size(downloadPath))
            .isEqualTo(sourceFile.length());
    }

    @Test
    void whenCopyObjectCalled_thenObjectCopiedSuccessfully() throws IOException {
        File sourceFile = FileGeneratorUtil.generate();
        s3ObjectOperationService.upload(DEFAULT_BUCKET_NAME, sourceFile);

        String destinationKey = "copied-" + sourceFile.getName();
        s3ObjectOperationService.copyObject(
            DEFAULT_BUCKET_NAME, 
            sourceFile.getName(), 
            DEFAULT_BUCKET_NAME, 
            destinationKey
        );

        boolean originalObjectExists = objectExists(DEFAULT_BUCKET_NAME, sourceFile.getName());
        boolean newCopiedObjectExists = objectExists(DEFAULT_BUCKET_NAME, destinationKey);
        assertThat(originalObjectExists)
            .isTrue();
        assertThat(newCopiedObjectExists)
            .isTrue();
    }

    @Test
    void whenDeleteCalled_thenObjectDeleted() throws IOException {
        File fileToDelete = FileGeneratorUtil.generate();
        s3ObjectOperationService.upload(DEFAULT_BUCKET_NAME, fileToDelete);

        s3ObjectOperationService.delete(DEFAULT_BUCKET_NAME, fileToDelete.getName());

        boolean isObjectPresent = objectExists(DEFAULT_BUCKET_NAME, fileToDelete.getName());
        assertThat(isObjectPresent)
            .isFalse();
    }

    @Test
    void whenDeleteMultipleCalled_thenObjectsDeleted() throws IOException {
        File file1 = FileGeneratorUtil.generate();
        File file2 = FileGeneratorUtil.generate();
        s3ObjectOperationService.upload(DEFAULT_BUCKET_NAME, file1);
        s3ObjectOperationService.upload(DEFAULT_BUCKET_NAME, file2);

        List<String> keysToDelete = List.of(file1.getName(), file2.getName());
        s3ObjectOperationService.delete(DEFAULT_BUCKET_NAME, keysToDelete);

        boolean file1Exists = objectExists(DEFAULT_BUCKET_NAME, file1.getName());
        boolean file2Exists = objectExists(DEFAULT_BUCKET_NAME, file2.getName());
        assertThat(file1Exists)
            .isFalse();
        assertThat(file2Exists)
            .isFalse();
    }

    private boolean objectExists(String bucket, String key) {
        try {
            s3Client.headObject(request -> request.bucket(bucket).key(key));
            return true;
        } catch (NoSuchKeyException exception) {
            return false;
        }
    }

}