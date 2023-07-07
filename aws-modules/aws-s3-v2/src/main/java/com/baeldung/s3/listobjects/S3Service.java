package com.baeldung.s3.listobjects;

import java.util.List;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

class S3Service {

    private S3Client s3Client;

    public S3Service(Region awsRegion) {
        init(awsRegion);
    }

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void init(Region awsRegion) {
        this.s3Client = S3Client.builder()
          .region(awsRegion)
          .credentialsProvider(ProfileCredentialsProvider.create("default"))
          .build();
    }

    public void listObjectsInBucket(String bucketName) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
          .bucket(bucketName)
          .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

        List<S3Object> contents = listObjectsV2Response.contents();

        System.out.println("Number of objects in the bucket: " + contents.stream().count());
        contents.stream().forEach(System.out::println);
    }

    public void listAllObjectsInBucket(String bucketName) {
        String nextContinuationToken = null;
        long totalObjects = 0;

        do {
            ListObjectsV2Request.Builder requestBuilder = ListObjectsV2Request.builder()
              .bucket(bucketName)
              .continuationToken(nextContinuationToken);

            ListObjectsV2Response response = s3Client.listObjectsV2(requestBuilder.build());
            nextContinuationToken = response.nextContinuationToken();

            totalObjects += response.contents().stream()
              .peek(System.out::println)
              .reduce(0, (subtotal, element) -> subtotal + 1, Integer::sum);
        } while (nextContinuationToken != null);
        System.out.println("Number of objects in the bucket: " + totalObjects);
    }

    public void listAllObjectsInBucketPaginated(String bucketName, int pageSize) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
          .bucket(bucketName)
          .maxKeys(pageSize) // Set the maxKeys parameter to control the page size
          .build();

        ListObjectsV2Iterable listObjectsV2Iterable = s3Client.listObjectsV2Paginator(listObjectsV2Request);
        long totalObjects = 0;

        for (ListObjectsV2Response page : listObjectsV2Iterable) {
            long retrievedPageSize = page.contents().stream()
              .peek(System.out::println)
              .reduce(0, (subtotal, element) -> subtotal + 1, Integer::sum);
            totalObjects += retrievedPageSize;
            System.out.println("Page size: " + retrievedPageSize);
        }
        System.out.println("Total objects in the bucket: " + totalObjects);
    }

    public void listAllObjectsInBucketPaginatedWithPrefix(String bucketName, int pageSize, String prefix) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
          .bucket(bucketName)
          .maxKeys(pageSize) // Set the maxKeys parameter to control the page size
          .prefix(prefix)
          .build();

        ListObjectsV2Iterable listObjectsV2Iterable = s3Client.listObjectsV2Paginator(listObjectsV2Request);
        long totalObjects = 0;

        for (ListObjectsV2Response page : listObjectsV2Iterable) {
            long retrievedPageSize = page.contents().stream()
              .peek(System.out::println)
              .reduce(0, (subtotal, element) -> subtotal + 1, Integer::sum);
            totalObjects += retrievedPageSize;
            System.out.println("Page size: " + retrievedPageSize);
        }
        System.out.println("Total objects in the bucket: " + totalObjects);
    }

    public void listBuckets() {
        // List all buckets
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();

        // Display the bucket names
        List<Bucket> buckets = listBucketsResponse.buckets();
        System.out.println("Buckets:");
        for (Bucket bucket : buckets) {
            System.out.println(bucket.name());
        }
    }

    public void putObject(String bucketName, List<File> files) {
        try {
            files.stream().forEach(file -> {
                s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(file.getName()).build(),
                  RequestBody.fromByteBuffer(file.getContent()));
                System.out.println("Uploaded file: " + file.getName());
            });
        } catch (S3Exception e) {
            System.err.println("Upload failed");
            e.printStackTrace();
        }
    }

    public void cleanup() {
        this.s3Client.close();
    }
}