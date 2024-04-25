package com.baeldung.aws.rest.s3.download.dto;

import java.io.IOException;

public interface FileReader {
    FileData readResponse(S3ObjectRequest s3ObjectRequest) throws IOException;
}
