package com.baeldung.s3;

import java.io.File;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Application {

    private static final AWSCredentials credentials;
    private static String bucketName;
    
    static {
        //put your accesskey and secretkey here
        credentials = new BasicAWSCredentials(
          "<AWS accesskey>", 
          "<AWS secretkey>"
        );
    }
    
    public static void main(String[] args) {
        //set-up the client
        AmazonS3 s3client = AmazonS3ClientBuilder
          .standard()
          .withCredentials(new AWSStaticCredentialsProvider(credentials))
          .withRegion(Regions.US_EAST_2)
          .build();
        
        AWSS3Service awsService = new AWSS3Service(s3client);

        //creating a bucket
        bucketName = "baeldung-bucket";
        if(awsService.doesBucketExist(bucketName)) {
            System.out.println("Bucket name is not available."
              + " Try again with a different Bucket name.");
            return;
        }
        awsService.createBucket(bucketName);
        
        //list all the buckets
        for(Bucket s : awsService.listBuckets() ) {
            System.out.println(s.getName());
        }
        
        //uploading objects
        awsService.putObject(
          bucketName, 
          "Document/hello.txt",
          new File("/Users/user/Document/hello.txt")
        );

        //list all the objects
        ObjectListing objectListing = s3client.listObjects(bucketName);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            System.out.println(os.getKey());
        }
    }
}
