package com.baeldung.s3.listobjects;

import software.amazon.awssdk.regions.Region;

public class Main {

    private static String AWS_BUCKET = "baeldung-tutorial-s3";
    public static Region AWS_REGION = Region.EU_CENTRAL_1;

    public static void main(String[] args) {
        S3Service s3Service = new S3Service(AWS_REGION);

        s3Service.putObject(AWS_BUCKET, FileGenerator.generateFiles(1000, 1));
        s3Service.listBuckets();
        s3Service.listObjectsInBucket(AWS_BUCKET);
        s3Service.listAllObjectsInBucket(AWS_BUCKET);
        s3Service.listAllObjectsInBucketPaginated(AWS_BUCKET, 500);
        s3Service.listAllObjectsInBucketPaginatedWithPrefix(AWS_BUCKET, 500, "backup/");

        s3Service.cleanup();
    }
}
