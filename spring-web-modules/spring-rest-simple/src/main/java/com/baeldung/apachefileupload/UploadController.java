package com.baeldung.apachefileupload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.core.FileItemInput;
import org.apache.commons.fileupload2.core.FileItemInputIterator;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleUpload(HttpServletRequest request) {
        System.out.println(System.getProperty("java.io.tmpdir"));
        boolean isMultipart = JakartaServletFileUpload.isMultipartContent(request);
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = DiskFileItemFactory.builder().get();
        // Configure a repository (to ensure a secure temp location is used)
        JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);
        try {
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            // Process the uploaded items
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();

                if (!item.isFormField()) {
                    try (InputStream uploadedStream = item.getInputStream();
                            OutputStream out = new FileOutputStream("file.mov");) {
                        IOUtils.copy(uploadedStream, out);
                        out.close();
                    }
                }
            }
            // Parse the request with Streaming API
            upload = new JakartaServletFileUpload();
            FileItemInputIterator iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemInput item = iterStream.next();
                String name = item.getFieldName();
                InputStream stream = item.getInputStream();
                if (!item.isFormField()) {
                    //Process the InputStream
                } else {
                    //process form fields
                    String formFieldValue = IOUtils.toString(stream, StandardCharsets.UTF_8);
                }
            }
            return "success!";
        } catch (IOException ex) {
            return "failed: " + ex.getMessage();
        }
    }

}
