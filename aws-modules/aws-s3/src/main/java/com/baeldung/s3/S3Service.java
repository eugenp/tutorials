package com.baeldung.s3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
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

    //is bucket exist?
    public boolean doesBucketExist(String bucketName) {
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
            .bucket(bucketName)
            .build();

        try {
            s3Client.headBucket(headBucketRequest);
            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    }

    //create a bucket
    public void createBucket(String bucketName) {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
            .bucket(bucketName)
            .build();

        s3Client.createBucket(bucketRequest);
    }

    //delete a bucket
    public void deleteBucket(String bucketName) {
        try {
            DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build();

            s3Client.deleteBucket(deleteBucketRequest);
            System.out.println("Successfully deleted bucket : " + bucketName);
        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    //uploading object
    public PutObjectResponse putObject(String bucketName, String key, java.io.File file) {
        PutObjectRequest request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        return s3Client.putObject(request, Path.of(file.toURI()) );
    }

    //listing objects
    public void listObjects(String bucketName) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

        for(S3Object os : listObjectsV2Response.contents()) {
            System.out.println(os.key());
        }
    }

    //get an object
    public void getObject(String bucketName, String objectKey) {
        try {
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

            ResponseBytes<GetObjectResponse> responseResponseBytes = s3Client.getObjectAsBytes(objectRequest);

            byte[] data = responseResponseBytes.asByteArray();

            // Write the data to a local file.
            java.io.File myFile = new java.io.File("/Users/user/Desktop/hello.txt" );
            OutputStream os = new FileOutputStream(myFile);
            os.write(data);
            System.out.println("Successfully obtained bytes from an S3 object");
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    //copying an object
    public CopyObjectResponse copyObject(
        String sourceBucketName,
        String sourceKey,
        String destinationBucketName,
        String destinationKey
    ) {
        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
            .sourceBucket(sourceBucketName)
            .sourceKey(sourceKey)
            .destinationBucket(destinationBucketName)
            .destinationKey(destinationKey)
            .build();

        return s3Client.copyObject(copyObjectRequest);
    }

    //deleting an object
    public void deleteObject(String bucketName, String objectKey) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(objectKey)
            .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    //deleting multiple Objects
    public void deleteObjects(String bucketName, List<String> keys ) {

        ArrayList<ObjectIdentifier> toDelete = new ArrayList<>();
        for(String objKey : keys) {
            toDelete.add(ObjectIdentifier.builder()
                .key(objKey)
                .build());
        }

        DeleteObjectsRequest deleteObjectRequest = DeleteObjectsRequest.builder()
            .bucket(bucketName)
            .delete(Delete.builder()
                .objects(toDelete).build())
            .build();

        s3Client.deleteObjects(deleteObjectRequest);
    }

    public boolean doesObjectExistByListObjects(String bucketName, String key) {

        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);

        return listObjectsV2Response.contents()
            .stream()
            .filter(s3ObjectSummary -> s3ObjectSummary.getValueForField("key", String.class)
                .equals(key))
            .findFirst()
            .isPresent();
    }

    public boolean doesObjectExistByDefaultMethod(String bucket, String key) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

            s3Client.headObject(headObjectRequest);

            System.out.println("Object exists");
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                System.out.println("Object does not exist");
                return false;
            } else {
                throw e;
            }
        }
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

    public void putObjects(String bucketName, List<File> files) {
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