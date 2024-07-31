package com.baeldung.spring.cloud.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.BeforeClass;

/**
 * This class is needed only for testing. This is because we need to
 * create AWS resources before Spring's Application context is created
 * in a {@link BeforeClass} method. Since Autowired dependencies don't
 * work in static context, we will use this class for AWS clients.
 */
public class SpringCloudAwsTestUtil {

    private static String awsAccessKey;
    private static String awsSecretKey;
    private static String defaultRegion;

    static {
        try {
            InputStream is = SpringCloudAwsTestUtil.class.getResourceAsStream("/application.properties");
            Properties properties = new Properties();
            properties.load(is);
            awsAccessKey = properties.getProperty("cloud.aws.credentials.accessKey");
            awsSecretKey = properties.getProperty("cloud.aws.credentials.secretKey");
            defaultRegion = properties.getProperty("cloud.aws.region.static");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    public static AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(awsCredentials());
    }

    public static AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(awsCredentialsProvider())
            .withRegion(defaultRegion)
            .build();
    }

    public static AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.standard()
            .withCredentials(awsCredentialsProvider())
            .withRegion(defaultRegion)
            .build();
    }

    public static AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder.standard()
            .withCredentials(awsCredentialsProvider())
            .withRegion(defaultRegion)
            .build();
    }
}
