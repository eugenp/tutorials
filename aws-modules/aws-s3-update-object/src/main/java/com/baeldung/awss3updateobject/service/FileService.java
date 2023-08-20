package com.baeldung.awss3updateobject.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public AmazonS3 amazonS3;

    @Value("${aws.s3bucket}")
    public String awsS3Bucket;

    @PostConstruct
    private void init(){
        AWSCredentials credentials = new BasicAWSCredentials(
                "AWS AccessKey",
                "AWS secretKey"
        );
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName("us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String uploadFile(MultipartFile multipartFile) throws Exception {
        String key = "/documents/" + multipartFile.getOriginalFilename();
        return this.uploadDocument(this.awsS3Bucket, key, multipartFile);
    }

    public String updateFile(MultipartFile multipartFile, String key) throws Exception {
        return this.uploadDocument(this.awsS3Bucket, key, multipartFile);
    }

    private String uploadDocument(String s3bucket, String key, MultipartFile multipartFile) throws Exception {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            Map<String, String> attributes = new HashMap<>();
            attributes.put("document-content-size", String.valueOf(multipartFile.getSize()));
            metadata.setUserMetadata(attributes);
            InputStream documentStream = multipartFile.getInputStream();
            PutObjectResult putObjectResult = this.amazonS3.putObject(new PutObjectRequest(s3bucket, key, documentStream, metadata));

            S3Object s3Object = this.amazonS3.getObject(s3bucket, key);
            logger.info("Last Modified: " + s3Object.getObjectMetadata().getLastModified());
            return key;
        } catch (AmazonS3Exception ex) {
            if (ex.getErrorCode().equalsIgnoreCase("NoSuchBucket")) {
                String msg = String.format("No bucket found with name %s", s3bucket);
                throw new Exception(msg);
            } else if (ex.getErrorCode().equalsIgnoreCase("AccessDenied")) {
                String msg = String.format("Access denied to S3 bucket %s", s3bucket);
                throw new Exception(msg);
            }
            throw ex;
        } catch (IOException ex) {
            String msg = String.format("Error saving file %s to AWS S3 bucket %s", key, s3bucket);
            throw new Exception(msg);
        }
    }
}
