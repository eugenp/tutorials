package com.baeldung.multipartboundary;

import static com.baeldung.multipartboundary.FileController.FILES;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(FILES)
public class FileController {

    protected static final String FILES = "/files";

    @GetMapping()
    public String displayUploadForm(Model model) {
        return "files/uploadForm";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestParam("file") MultipartFile file, String fileDescription) {

        return "files/success";
    }
}
