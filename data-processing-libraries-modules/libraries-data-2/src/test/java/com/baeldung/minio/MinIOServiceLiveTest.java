package com.baeldung.minio;

import io.minio.*;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinIOServiceLiveTest {

    private MinioClient minioClient;

    private final String BUCKET_NAME = "livetest";

    private final String OBJECT_NAME = "minio_sample.txt";

    private final String CONTENTS = "hello";

    @Before
    public void setup() {
        minioClient =
            MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }

    @Test
    public void givenFile_whenUploadAndRetrive_thenContentsAreCorrect() throws Exception {

        if(!minioClient.bucketExists(BucketExistsArgs
            .builder()
            .bucket(BUCKET_NAME)
            .build())) {

            minioClient.makeBucket(MakeBucketArgs
                .builder()
                .bucket(BUCKET_NAME)
                .build());
        }

        minioClient.putObject(PutObjectArgs
            .builder()
            .bucket(BUCKET_NAME)
            .object(OBJECT_NAME)
            .stream(new ByteArrayInputStream(CONTENTS.getBytes()), CONTENTS.length(),-1)
            .build());

        try (InputStream stream =
            minioClient.getObject(GetObjectArgs
                .builder()
                .bucket(BUCKET_NAME)
                .object(OBJECT_NAME)
                .build())) {

            byte[] obj = new byte[CONTENTS.length()];
            stream.read(obj);
            assertEquals(CONTENTS, new String(obj));
        }
        catch (IOException e) {

        }
        finally {
            minioClient.removeObject(RemoveObjectArgs
                .builder()
                .bucket(BUCKET_NAME)
                .object(OBJECT_NAME)
                .build());
        }
    }
}
