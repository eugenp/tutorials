package com.spaceprogram.bigcache;

import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Put a file in the root classpath called aws-auth.properties with three lines:
 * accessKey = ABC
 * secretKey = DEF
 * bucketName = test bucket name
 * <p/>
 * User: treeder
 * Date: Aug 28, 2008
 * Time: 10:31:19 AM
 */
public class S3CacheTests extends BaseCacheTest {

    static S3Cache s3cache;

    public S3CacheTests() {
        super(s3cache);
    }

    @BeforeClass
    public static void setupCache() throws IOException {
        System.out.println("Setting up cache in S3CacheTests");
        Properties props = new Properties();
        InputStream is = S3CacheTests.class.getResourceAsStream("/aws-auth.properties");
        if (is == null) {
            throw new RuntimeException("No aws-auth.properties file found.");
        }
        props.load(is);
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        s3cache = new S3Cache(props.getProperty("accessKey"), props.getProperty("secretKey"), props.getProperty("bucketName"), executorService);
    }

    @AfterClass
    public static void shutdown() {
        s3cache.getExecutorService().shutdown();
    }


}
