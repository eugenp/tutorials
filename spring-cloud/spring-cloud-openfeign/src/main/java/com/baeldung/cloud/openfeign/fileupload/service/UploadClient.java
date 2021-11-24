package com.baeldung.cloud.openfeign.fileupload.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.cloud.openfeign.fileupload.config.FeignSupportConfig;

@FeignClient(name = "file", url = "http://localhost:8081", configuration = FeignSupportConfig.class)
public interface UploadClient {
    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String fileUpload(@RequestPart(value = "file") MultipartFile file);
}
