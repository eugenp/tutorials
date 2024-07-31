package com.baeldung.aws.rest.s3.download.dto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Component
public class S3ResponseReader implements FileReader {

    private final S3Client s3Client;

    public S3ResponseReader(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public FileData readResponse(S3ObjectRequest s3ObjectRequest) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
          .bucket(s3ObjectRequest.getBucketName())
          .key(s3ObjectRequest.getObjectKey())
          .build();
        try (ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = responseInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] fileContent = outputStream.toByteArray();
            String contentType = responseInputStream.response()
              .contentType();
            String contentDisposition = responseInputStream.response()
              .contentDisposition();
            return new FileData(fileContent, contentType, contentDisposition, getObjectRequest);
        }
    }
}

