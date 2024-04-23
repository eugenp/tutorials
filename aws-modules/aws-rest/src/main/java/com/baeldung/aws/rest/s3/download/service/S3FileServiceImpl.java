package com.baeldung.aws.rest.s3.download.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.baeldung.aws.rest.s3.download.dto.FileData;
import com.baeldung.aws.rest.s3.download.dto.FileDownloadResponse;
import com.baeldung.aws.rest.s3.download.dto.FileReader;
import com.baeldung.aws.rest.s3.download.dto.S3ObjectRequest;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements FileService {

    private final FileReader s3ResponseReader;

    @Override
    public FileDownloadResponse downloadFile(String s3Url) {
        try {
            // Parse the S3 URL
            URI uri = new URI(s3Url);

            // Extract bucket name and object key
            String bucketName = uri.getHost();
            String objectKey = uri.getPath()
              .substring(1); // Remove leading "/"

            S3ObjectRequest s3Request = S3ObjectRequest.builder()
              .bucketName(bucketName)
              .objectKey(objectKey)
              .build();

            FileData s3Response = s3ResponseReader.readResponse(s3Request);

            // Get object metadata
            String contentType = s3Response.getContentType();
            String contentDisposition = s3Response.getContentDisposition();
            byte[] fileContent = s3Response.getFileContent();
            String key = s3Response.getRequest()
              .key();
            String filename = extractFilenameFromKey(key);

            String originalFilename =
              contentDisposition == null ? filename : contentDisposition.substring(contentDisposition.indexOf("=") + 1);

            return FileDownloadResponse.builder()
              .fileContent(fileContent)
              .originalFilename(originalFilename)
              .contentType(contentType)
              .build();
        } catch (IOException | S3Exception e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractFilenameFromKey(String objectKey) {
        int lastSlashIndex = objectKey.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < objectKey.length() - 1) {
            return objectKey.substring(lastSlashIndex + 1);
        }
        return objectKey;
    }
}
