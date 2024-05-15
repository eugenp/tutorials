package com.baeldung.boot.json.convertfile.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.boot.json.convertfile.ImportUtils;
import com.baeldung.boot.json.convertfile.service.ImportJsonService;

@RestController
@RequestMapping("/import-json")
public class ImportJsonController {
    @Autowired
    private ImportJsonService service;

    @PostMapping("/{collection}")
    public String postJson(@RequestBody String jsonStrings, @PathVariable String collection) {
        List<String> jsonLines = ImportUtils.lines(jsonStrings);
        return service.importTo(collection, jsonLines);
    }

    @PostMapping("/file/{collection}")
    public String postJsonFile(@RequestPart("parts") MultipartFile jsonStringsFile, @PathVariable String collection) throws IOException {
        List<String> jsonLines = ImportUtils.lines(jsonStringsFile);
        return service.importTo(collection, jsonLines);
    }
}
