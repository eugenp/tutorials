package com.baeldung.aws.rest.s3.download.service;

import java.io.IOException;

import com.baeldung.aws.rest.s3.download.dto.FileDownloadResponse;

import software.amazon.awssdk.services.s3.model.S3Exception;

public interface FileService {
    FileDownloadResponse downloadFile(String url) throws IOException, S3Exception;
}
