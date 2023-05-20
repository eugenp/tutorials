package com.baeldung.minio;

import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;

public class MinIOService {
    private MinioClient minioClient =
        MinioClient.builder()
            .endpoint("http://127.0.0.1:9000")
            .credentials("minioadmin", "minioadmin")
            .build();

    public void doSomething() throws Exception {
        minioClient.makeBucket(
            MakeBucketArgs
                .builder()
                .bucket("user2")
                .build());

        minioClient.uploadObject(
            UploadObjectArgs
                .builder()
                .bucket("user2")
                .filename("/tmp/Resume.pdf")
                .build());

        minioClient.getObject(
            GetObjectArgs.builder()
                .bucket("user2")
                .object("Resume.pdf")
                .build());
    }
}
