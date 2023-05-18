package com.baeldung.s3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * Required defined environment variables AWS_ACCESS_KEY_ID & AWS_ACCESS_KEY to access S3.
 * Required S3 bucket and key that exist.
 */

public class AWSS3ObjectManualTest {

    private static final String BUCKET = "your-bucket";
    private static final String KEY_THAT_EXIST = "your-key-that-exist";
    private AWSS3ObjectUtils s3ObjectUtils;

    @Before
    public void setUp() {
        AmazonS3 client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.DEFAULT_REGION)
            .withCredentials(new EnvironmentVariableCredentialsProvider())
            .build();

        s3ObjectUtils = new AWSS3ObjectUtils(client);
    }

    @Test
    public void whenVerifyIfObjectExistByDefaultMethod_thenCorrect() {
        assertTrue(s3ObjectUtils.doesObjectExistByDefaultMethod(BUCKET, KEY_THAT_EXIST), "Key: " + KEY_THAT_EXIST + " doesn't exist");

    }

    @Test
    public void whenVerifyIfObjectExistByListObjects_thenCorrect() {
        assertTrue(s3ObjectUtils.doesObjectExistByListObjects(BUCKET, KEY_THAT_EXIST), "Key: " + KEY_THAT_EXIST + " doesn't exist");
    }

    @Test
    public void whenVerifyIfObjectExistByMetaData_thenCorrect() {
        assertTrue(s3ObjectUtils.doesObjectExistByMetaData(BUCKET, KEY_THAT_EXIST), "Key: " + KEY_THAT_EXIST + " doesn't exist");
    }
}
