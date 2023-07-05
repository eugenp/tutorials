package com.baeldung.s3;

import java.io.File;
import java.util.List;

import software.amazon.awssdk.regions.Region;

public class S3Application {

    private static String AWS_BUCKET = "baeldung-tutorial-s3";
    public static Region AWS_REGION = Region.EU_CENTRAL_1;

    public static void main(String[] args) {
        S3Service s3Service = new S3Service(AWS_REGION);

        //creating a bucket
        s3Service.createBucket(AWS_BUCKET);

        //check if a bucket exists
        if(s3Service.doesBucketExist(AWS_BUCKET)) {
            System.out.println("Bucket name is not available."
                + " Try again with a different Bucket name.");
            return;
        }


        s3Service.putObjects(AWS_BUCKET, FileGenerator.generateFiles(1000, 1));

        //list all the buckets
        s3Service.listBuckets();
        s3Service.listObjectsInBucket(AWS_BUCKET);
        s3Service.listAllObjectsInBucket(AWS_BUCKET);
        s3Service.listAllObjectsInBucketPaginated(AWS_BUCKET, 500);
        s3Service.listAllObjectsInBucketPaginatedWithPrefix(AWS_BUCKET, 500, "backup/");



        //deleting bucket
        s3Service.deleteBucket("baeldung-bucket-test2");

        //uploading object
        s3Service.putObject(
            AWS_BUCKET,
            "Document/hello.txt",
            new File("/Users/user/Document/hello.txt")
        );

        //listing objects
        s3Service.listObjects(AWS_BUCKET);

        //downloading an object
        s3Service.getObject(AWS_BUCKET, "Document/hello.txt");

        //copying an object
        s3Service.copyObject(
            "baeldung-bucket",
            "picture/pic.png",
            "baeldung-bucket2",
            "Document/picture.png"
        );

        //deleting an object
        s3Service.deleteObject(AWS_BUCKET, "Document/hello.txt");

        //deleting multiple objects
        List<String> objKeyList = List.of("Document/hello2.txt", "Document/picture.png");
        s3Service.deleteObjects(AWS_BUCKET, objKeyList);
    }
}
