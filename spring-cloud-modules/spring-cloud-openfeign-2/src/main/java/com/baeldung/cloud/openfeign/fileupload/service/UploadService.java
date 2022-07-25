package com.baeldung.cloud.openfeign.fileupload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import feign.Feign;
import feign.Response;
import feign.form.spring.SpringFormEncoder;

@Service
public class UploadService {
    private static final String HTTP_FILE_UPLOAD_URL = "http://localhost:8080";
    
    @Autowired
    private FileUploadClientWithFallbackFactory fileUploadClient;
    @Autowired
    private FileUploadClientWithFallBack fileUploadClientWithFallback;
    
    public boolean uploadFileWithManualClient(MultipartFile file) {
        UploadResource fileUploadResource = Feign.builder().encoder(new SpringFormEncoder())
                .target(UploadResource.class, HTTP_FILE_UPLOAD_URL);
        Response response = fileUploadResource.uploadFile(file);
        return response.status() == 200;
    }

    public String uploadFileWithFallbackFactory(MultipartFile file) {
        return fileUploadClient.fileUpload(file);
    }

    public String uploadFileWithFallback(MultipartFile file) {
        return fileUploadClientWithFallback.fileUpload(file);
    }
}