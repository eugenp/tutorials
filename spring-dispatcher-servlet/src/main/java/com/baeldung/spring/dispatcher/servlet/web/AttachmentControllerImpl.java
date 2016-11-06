package com.baeldung.spring.dispatcher.servlet.web;

import com.baeldung.spring.dispatcher.servlet.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class AttachmentControllerImpl implements AttachmentController {
    @Autowired
    private Map<String, List<Task>> taskMap;

    @Override
    public ResponseEntity<FileSystemResource> getAttachment(
      @PathVariable("attachmentId") String attachmentId,
      @RequestParam(name = "download", required = false, defaultValue = "false") boolean forcedDownload
    ) {
        FileSystemResource resource = new FileSystemResource("/tmp/" + attachmentId);
        HttpHeaders headers = new HttpHeaders();
        taskMap.values().stream()
          .flatMap(Collection::stream)
          .flatMap(t -> t.getAttachments().stream())
          .filter(a -> a.getId().equals(attachmentId))
          .findFirst()
          .ifPresent(a -> {
              headers.add("Content-Disposition",
                "attachment; filename=" + a.getName());
              headers.add("Content-Type", forcedDownload ?
                MediaType.APPLICATION_OCTET_STREAM_VALUE :
                URLConnection.guessContentTypeFromName(a.getName()));
          });
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
