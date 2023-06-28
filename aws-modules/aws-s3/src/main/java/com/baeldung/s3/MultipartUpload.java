package com.baeldung.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.util.concurrent.Executors;

public class MultipartUpload {

    public static void main(String[] args) throws Exception {
        String existingBucketName = "baeldung-bucket";
        String keyName = "my-picture.jpg";
        String filePath = "documents/my-picture.jpg";

        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.DEFAULT_REGION)
                .build();

        int maxUploadThreads = 5;

        TransferManager tm = TransferManagerBuilder
                .standard()
                .withS3Client(amazonS3)
                .withMultipartUploadThreshold((long) (5 * 1024 * 1024))
                .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
                .build();

        ProgressListener progressListener =
                progressEvent -> System.out.println("Transferred bytes: " + progressEvent.getBytesTransferred());

        PutObjectRequest request = new PutObjectRequest(existingBucketName, keyName, new File(filePath));

        request.setGeneralProgressListener(progressListener);

        Upload upload = tm.upload(request);

        try {
            upload.waitForCompletion();
            System.out.println("Upload complete.");
        } catch (AmazonClientException e) {
            System.out.println("Error occurred while uploading file");
            e.printStackTrace();
        }
    }
}