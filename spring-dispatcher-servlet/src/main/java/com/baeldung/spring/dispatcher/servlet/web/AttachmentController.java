package com.baeldung.spring.dispatcher.servlet.web;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/attachments")
public interface AttachmentController {
    @GetMapping("/{attachmentId}")
    ResponseEntity<FileSystemResource> getAttachment(
      @PathVariable("attachmentId") String attachmentId,
      @RequestParam(name = "download", required = false, defaultValue = "false") boolean forcedDownload
    );
}
