package com.baeldung.s3;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.util.Arrays;
import java.util.Collections;

class S3ServiceLiveTest {

    @Mock
    private S3Client s3Client;

    private S3Service s3Service;

    private String AWS_BUCKET = "baeldung-tutorial-s3";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        s3Service = new S3Service(s3Client);
    }

    @AfterEach
    public void cleanup() {
        s3Service.cleanup();
        Mockito.verify(s3Client).close();
    }

    @Test
    void givenBucketName_whenListObjectsInBucket_thenReturnList() {
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(AWS_BUCKET).build();
        ListObjectsV2Response response = ListObjectsV2Response.builder().contents(Collections.emptyList()).build();

        when(s3Client.listObjectsV2(request)).thenReturn(response);

        s3Service.listObjectsInBucket(AWS_BUCKET);
        Mockito.verify(s3Client).listObjectsV2(request);
    }

    @Test
    void givenBucketName_whenListAllObjectsInBucket_thenReturnList() {
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(AWS_BUCKET).build();
        ListObjectsV2Response response = ListObjectsV2Response.builder().contents(Collections.emptyList()).build();

        when(s3Client.listObjectsV2(request)).thenReturn(response);

        s3Service.listAllObjectsInBucket(AWS_BUCKET);
        Mockito.verify(s3Client).listObjectsV2(request);
    }

    @Test
    void givenBucketNameAndPageSize_whenListAllObjectsInBucketPaginated_thenReturnPaginatedList() {
        int pageSize = 10;
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(AWS_BUCKET).maxKeys(pageSize).build();

        ListObjectsV2Iterable mockIterable = Mockito.mock(ListObjectsV2Iterable.class);

        S3Object s3Object1 = S3Object.builder().key("object1").build();
        S3Object s3Object2 = S3Object.builder().key("object2").build();
        ListObjectsV2Response response = ListObjectsV2Response.builder().contents(s3Object1, s3Object2).build();

        when(s3Client.listObjectsV2Paginator(request)).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(Arrays.asList(response).iterator());

        s3Service.listAllObjectsInBucketPaginated(AWS_BUCKET, pageSize);
        Mockito.verify(s3Client).listObjectsV2Paginator(request);
    }

    @Test
    void givenBucketNamePageSizeAndPrefix_whenListAllObjectsInBucketPaginatedWithPrefix_thenReturnPaginatedList() {
        int pageSize = 1;
        String prefix = "folder/";
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(AWS_BUCKET).maxKeys(pageSize).prefix(prefix).build();

        ListObjectsV2Iterable mockIterable = Mockito.mock(ListObjectsV2Iterable.class);

        S3Object s3Object1 = S3Object.builder().key("folder/object1").build();
        S3Object s3Object2 = S3Object.builder().key("folder/object2").build();
        ListObjectsV2Response response = ListObjectsV2Response.builder().contents(s3Object1, s3Object2).build();

        when(s3Client.listObjectsV2Paginator(request)).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(Arrays.asList(response).iterator());

        s3Service.listAllObjectsInBucketPaginatedWithPrefix(AWS_BUCKET, pageSize, prefix);
        Mockito.verify(s3Client).listObjectsV2Paginator(request);
    }

    @Test
    void whenListBuckets_thenReturnBucketList() {
        when(s3Client.listBuckets()).thenReturn(ListBucketsResponse.builder().buckets(Collections.emptyList()).build());

        s3Service.listBuckets();
        Mockito.verify(s3Client).listBuckets();
    }
}
