package com.baeldung.s3.v2;

import java.io.File;
import java.util.List;

import software.amazon.awssdk.regions.Region;

public class S3ApplicationV2 {

    private static String bucketName = "baeldung-tutorial-s3";
    public static Region AWS_REGION = Region.EU_CENTRAL_1;

    public static void main(String[] args) {
        AWSS3ServiceV2 awsService = new AWSS3ServiceV2(AWS_REGION);

        bucketName = "baeldung-bucket";

        //creating a bucket
        awsService.createBucket(bucketName);

        //check if a bucket exists
        if(awsService.doesBucketExist(bucketName)) {
            System.out.println("Bucket name is not available."
              + " Try again with a different Bucket name.");
            return;
        }

        //list all the buckets
        awsService.listBuckets();

        //deleting bucket
        awsService.deleteBucket("baeldung-bucket-test2");
        
        //uploading object
        awsService.putObject(
          bucketName, 
          "Document/hello.txt",
          new File("/Users/user/Document/hello.txt")
        );

        //listing objects
        awsService.listObjects(bucketName);

        //downloading an object
        awsService.getObject(bucketName, "Document/hello.txt");

        //copying an object
        awsService.copyObject(
          "baeldung-bucket", 
          "picture/pic.png", 
          "baeldung-bucket2", 
          "Document/picture.png"
        );
        
        //deleting an object
        awsService.deleteObject(bucketName, "Document/hello.txt");

        //deleting multiple objects
        List<String> objKeyList = List.of("Document/hello2.txt", "Document/picture.png");
        awsService.deleteObjects(bucketName, objKeyList);
    }
}
