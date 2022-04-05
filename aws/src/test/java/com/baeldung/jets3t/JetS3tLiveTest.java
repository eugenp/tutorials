package com.baeldung.jets3t;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jets3t.service.S3Service;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.StorageObject;
import org.jets3t.service.security.AWSCredentials;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JetS3tLiveTest {

    private Log log = LogFactory.getLog(JetS3tLiveTest.class);

    private static final String BucketName = "baeldung-barfoo";
    private static final String TestString = "test string";
    private static final String TestStringName = "string object";
    private static final String TgtBucket = "baeldung-tgtbucket";

    private static S3Service s3Service;

    @BeforeClass
    public static void connectS3() throws Exception {

        // Replace with your keys
        String awsAccessKey = "your access key";
        String awsSecretKey = "your secret key";

        // Create credentials
        AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);

        // Create service
        s3Service = new RestS3Service(awsCredentials);
    }

    @Test
    public void givenCreate_AndDeleteBucket_CountGoesUpThenDown() throws Exception {

        // List buckets, get a count
        S3Bucket[] myBuckets = s3Service.listAllBuckets();
        int count = Arrays.stream(myBuckets).map(S3Bucket::getName).collect(Collectors.toList()).size();

        // Create a bucket
        S3Bucket bucket = createBucket();
        assertNotNull(bucket);

        // List again
        myBuckets = s3Service.listAllBuckets();
        int newCount = Arrays.stream(myBuckets).map(S3Bucket::getName).collect(Collectors.toList()).size();

        // We should have one more
        assertEquals((count + 1), newCount);

        // Delete so next test doesn't fail
        deleteBucket();

        // Check the count again, just for laughs
        myBuckets = s3Service.listAllBuckets();
        newCount = Arrays.stream(myBuckets).map(S3Bucket::getName).collect(Collectors.toList()).size();
        assertEquals(count, newCount);

    }

    private S3Bucket createBucket() throws Exception {
        S3Bucket bucket = s3Service.createBucket(BucketName);
        log.info(bucket);
        return bucket;
    }


    private void deleteBucket() throws ServiceException {
        s3Service.deleteBucket(BucketName);
    }

    @Test
    public void givenString_Uploaded_StringInfoIsAvailable() throws Exception {

        // Create a bucket
        S3Bucket bucket = createBucket();
        assertNotNull(bucket);

        // Upload a string
        uploadStringData();

        // Get the details
        StorageObject objectDetailsOnly = s3Service.getObjectDetails(BucketName, TestStringName);
        log.info("Content type: " + objectDetailsOnly.getContentType() + " length: " + objectDetailsOnly.getContentLength());

        // Delete it
        deleteObject(TestStringName);

        // For next test
        deleteBucket();
    }

    private void uploadStringData() throws Exception {
        S3Object stringObject = new S3Object(TestStringName, TestString);
        s3Service.putObject(BucketName, stringObject);
        log.info("Content type:" + stringObject.getContentType());
    }

    private void deleteObject(String objectName) throws ServiceException {
        s3Service.deleteObject(BucketName, objectName);
    }

    @Test
    public void givenStringUploaded_StringIsDownloaded() throws Exception {

        // Get a bucket
        S3Bucket bucket = createBucket();
        assertNotNull(bucket);

        uploadStringData();

        // Download
        S3Object stringObject = s3Service.getObject(BucketName, TestStringName);

        // Process stream into a string
        String downloadedString = new BufferedReader(new InputStreamReader(stringObject.getDataInputStream())).lines().collect(Collectors.joining("\n"));

        // Verify
        assertTrue(TestString.equals(downloadedString));


        // Clean up for next test
        deleteObject(TestStringName);
        deleteBucket();
    }

    @Test
    public void givenBinaryFileUploaded_FileIsDownloaded() throws Exception {

        // get a bucket
        S3Bucket bucket = createBucket();
        assertNotNull(bucket);

        // Put a binary file
        S3Object fileObject = new S3Object(new File("src/test/resources/test.jpg"));
        s3Service.putObject(BucketName, fileObject);

        // Print info about type and name
        log.info("Content type:" + fileObject.getContentType());
        log.info("File object name is " + fileObject.getName());

        // Download
        S3Object newFileObject = s3Service.getObject(BucketName, "test.jpg");

        // Save to a different name
        File newFile = new File("src/test/resources/newtest.jpg");
        Files.copy(newFileObject.getDataInputStream(), newFile.toPath(), REPLACE_EXISTING);


        // Get hashes and compare
        String origMD5 = getFileMD5("src/test/resources/test.jpg");
        String newMD5 = getFileMD5("src/test/resources/newtest.jpg");
        assertTrue(origMD5.equals(newMD5));

        // Clean up
        deleteObject("test.jpg");
        deleteBucket();
    }

    // Get MD5 hash for a file
    private String getFileMD5(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(new File(filename))) {
            return DigestUtils.md5Hex(fis);
        }
    }



    @Test
    public void givenStreamDataUploaded_StreamDataIsDownloaded() throws Exception {

        // get a bucket
        S3Bucket bucket = createBucket();
        assertNotNull(bucket);

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(2);
        numbers.add(3);
        numbers.add(5);
        numbers.add(7);

        // Serialize ArrayList
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bytes);
        objectOutputStream.writeObject(numbers);

        // Wrap bytes
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes.toByteArray());

        // Create and populate object
        S3Object streamObject = new S3Object("stream");
        streamObject.setDataInputStream(byteArrayInputStream);
        streamObject.setContentLength(byteArrayInputStream.available());
        streamObject.setContentType("binary/octet-stream");

        // Put it
        s3Service.putObject(BucketName, streamObject);

        // Get it
        S3Object newStreamObject = s3Service.getObject(BucketName, "stream");

        // Convert back to ArrayList
        ObjectInputStream objectInputStream = new ObjectInputStream(newStreamObject.getDataInputStream());
        ArrayList<Integer> newNumbers = (ArrayList<Integer>)objectInputStream.readObject();

        assertEquals(2, (int)newNumbers.get(0));
        assertEquals(3, (int)newNumbers.get(1));
        assertEquals(5, (int)newNumbers.get(2));
        assertEquals(7, (int)newNumbers.get(3));

        // Clean up
        deleteObject("stream");
        deleteBucket();
    }

    @Test
    public void whenFileCopied_CopyIsSame() throws Exception {

        // get a bucket
        S3Bucket bucket = createBucket();
        assertNotNull(bucket);

        // Put a binary file
        S3Object fileObject = new S3Object(new File("src/test/resources/test.jpg"));
        s3Service.putObject(BucketName, fileObject);


        // Copy it
        S3Object targetObject = new S3Object("testcopy.jpg");
        s3Service.copyObject(BucketName, "test.jpg", BucketName, targetObject, false);


        // Download
        S3Object newFileObject = s3Service.getObject(BucketName, "testcopy.jpg");

        // Save to a different name
        File newFile = new File("src/test/resources/testcopy.jpg");
        Files.copy(newFileObject.getDataInputStream(), newFile.toPath(), REPLACE_EXISTING);


        // Get hashes and compare
        String origMD5 = getFileMD5("src/test/resources/test.jpg");
        String newMD5 = getFileMD5("src/test/resources/testcopy.jpg");
        assertTrue(origMD5.equals(newMD5));

        // Clean up
        deleteObject("test.jpg");
        deleteObject("testcopy.jpg");
        deleteBucket();

    }


    @Test
    public void whenFileRenamed_NewNameIsSame() throws Exception {

        // get a bucket
        S3Bucket bucket = createBucket();
        assertNotNull(bucket);

        // Put a binary file
        S3Object fileObject = new S3Object(new File("src/test/resources/test.jpg"));
        s3Service.putObject(BucketName, fileObject);


        // Copy it
        s3Service.renameObject(BucketName, "test.jpg", new S3Object("spidey.jpg"));


        // Download
        S3Object newFileObject = s3Service.getObject(BucketName, "spidey.jpg");

        // Save to a different name
        File newFile = new File("src/test/resources/spidey.jpg");
        Files.copy(newFileObject.getDataInputStream(), newFile.toPath(), REPLACE_EXISTING);


        // Get hashes and compare
        String origMD5 = getFileMD5("src/test/resources/test.jpg");
        String newMD5 = getFileMD5("src/test/resources/spidey.jpg");
        assertTrue(origMD5.equals(newMD5));

        // Clean up
        deleteObject("test.jpg");
        deleteObject("spidey.jpg");
        deleteBucket();

    }

    @Test
    public void whenFileMoved_NewInstanceIsSame() throws Exception {

        // get a bucket
        S3Bucket bucket = createBucket();
        assertNotNull(bucket);

        // create another bucket
        S3Bucket tgtBucket = s3Service.createBucket(TgtBucket);


        // Put a binary file
        S3Object fileObject = new S3Object(new File("src/test/resources/test.jpg"));
        s3Service.putObject(BucketName, fileObject);


        // Copy it
        s3Service.moveObject(BucketName, "test.jpg", TgtBucket,
            new S3Object("spidey.jpg"), false);


        // Download
        S3Object newFileObject = s3Service.getObject(TgtBucket, "spidey.jpg");

        // Save to a different name
        File newFile = new File("src/test/resources/spidey.jpg");
        Files.copy(newFileObject.getDataInputStream(), newFile.toPath(), REPLACE_EXISTING);


        // Get hashes and compare
        String origMD5 = getFileMD5("src/test/resources/test.jpg");
        String newMD5 = getFileMD5("src/test/resources/spidey.jpg");
        assertTrue(origMD5.equals(newMD5));

        // Clean up
        deleteBucket();

        s3Service.deleteObject(TgtBucket, "spidey.jpg");
        s3Service.deleteBucket(TgtBucket);
    }


}
