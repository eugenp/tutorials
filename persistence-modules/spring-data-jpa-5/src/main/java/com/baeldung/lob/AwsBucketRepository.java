package com.baeldung.lob;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;

@Repository
class AwsBucketRepository {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${S3_BUCKET_NAME:your-bucket-name}")
    private String s3BucketName;

    private static final long PART_MAX_BYTES = 5 * 1024 * 1024;

    CompleteMultipartUploadResult uploadMultipartFile(String objectPath, final MultipartFile multipartFile) throws IOException {

        String uploadId = startRequest(objectPath);

        List<PartETag> partIdentifiers = uploadParts(objectPath, multipartFile, uploadId);

        return completeRequest(objectPath, uploadId, partIdentifiers);
    }

    private String startRequest(String objectKey) {
        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(s3BucketName, objectKey);
        return s3Client.initiateMultipartUpload(initRequest)
            .getUploadId();
    }

    private List<PartETag> uploadParts(String objectKey, final MultipartFile multipartFile,
        String uploadId) throws IOException {
        long partSize = PART_MAX_BYTES;
        List<PartETag> partIdenfiers = new ArrayList<>();
        long fileOffset = 0;
        for (int partNumber = 1; fileOffset < multipartFile.getSize(); partNumber++) {
            partSize = Math.min(partSize, multipartFile.getSize() - fileOffset);

            UploadPartResult uploadResult = s3Client
                .uploadPart(createUploadPartRequest(objectKey, multipartFile, uploadId, partSize, fileOffset, partNumber));
            partIdenfiers.add(uploadResult.getPartETag());

            fileOffset += partSize;
        }
        return partIdenfiers;
    }

    private UploadPartRequest createUploadPartRequest(String objectKey, final MultipartFile multipartFile,
        String uploadId, long partSize, long fileOffset, int partNumber) throws IOException {
        UploadPartRequest uploadRequest = new UploadPartRequest()
            .withBucketName(s3BucketName)
            .withKey(objectKey)
            .withUploadId(uploadId)
            .withPartNumber(partNumber)
            .withFileOffset(fileOffset)
            .withInputStream(multipartFile.getInputStream())
            .withPartSize(partSize);
        return uploadRequest;
    }

    private CompleteMultipartUploadResult completeRequest(String objectPath, String uploadId, List<PartETag> partIdentifiers) {
        CompleteMultipartUploadRequest finalRequest = new CompleteMultipartUploadRequest(s3BucketName, objectPath,
            uploadId, partIdentifiers);

        return s3Client.completeMultipartUpload(finalRequest);
    }
}
