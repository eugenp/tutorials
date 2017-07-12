package com.baeldung.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Application {

    private static final AWSCredentials credentials;
    private static String bucketName;  

    static {
        credentials = new BasicAWSCredentials(
          "AKIAICO6PD7ZRIO5C4RA", 
          "/x+X6QSE5bi2sk4/fWJW0lnw3lYhTg17yGMAH3Tl"
        );
    }
    
//    static {
//        //put your accesskey and secretkey here
//        credentials = new BasicAWSCredentials(
//          "<AWS accesskey>", 
//          "<AWS secretkey>"
//        );
//    }  
    
    public static void main(String[] args) throws IOException {
        //set-up the client
        AmazonS3 s3client = AmazonS3ClientBuilder
          .standard()
          .withCredentials(new AWSStaticCredentialsProvider(credentials))
          .withRegion(Regions.US_EAST_2)
          .build();
        
        AWSS3Service awsService = new AWSS3Service(s3client);

        bucketName = "baeldung-bucket";

        //creating a bucket
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
        
        //deleting bucket
        awsService.deleteBucket("baeldung-bucket-test2");
        
        //uploading object
        awsService.putObject(
          bucketName, 
          "Document/hello.txt",
          new File("/Users/user/Document/hello.txt")
        );

        //listing objects
        ObjectListing objectListing = awsService.listObjects(bucketName);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            System.out.println(os.getKey());
        }

        //downloading an object
        S3Object s3object = awsService.getObject(bucketName, "picture/pic.png");
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        FileOutputStream fos = new FileOutputStream(new File("/Users/ramansahasi/Desktop/pic3.png"));

        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            fos.write(bytes, 0, read);
        }
        inputStream.close();
        fos.close();
        
        //copying an object
        awsService.copyObject(
          "baeldung-bucket", 
          "picture/pic.png", 
          "baeldung-bucket2", 
          "document/picture.png"
        );
        
        //deleting an object
        awsService.deleteObject(bucketName, "Document/hello.txt2");

        //deleting multiple objects
        String objkeyArr[] = {
          "document/hello.txt", 
          "document/pic.png"
        };
        
        DeleteObjectsRequest delObjReq = new DeleteObjectsRequest("baeldung-bucket")
          .withKeys(objkeyArr);
        awsService.deleteObjects(delObjReq);
    }
}
