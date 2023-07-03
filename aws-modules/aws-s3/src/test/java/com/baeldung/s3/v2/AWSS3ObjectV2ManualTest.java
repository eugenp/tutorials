package com.baeldung.s3.v2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Required defined environment variables AWS_ACCESS_KEY_ID & AWS_ACCESS_KEY to access S3.
 * Required S3 bucket and key that exist.
 */

public class AWSS3ObjectV2ManualTest {
}
