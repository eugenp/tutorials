package com.baeldung.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baeldung.domain.Document;
import com.baeldung.domain.port.DocumentService;

@RestController
@RequestMapping("/doc")
public class DocumentRestAdapter {
    @Autowired
    private DocumentService documentService;

    @PostMapping
    public void createDocument(@RequestBody Document document) {
        documentService.createDocument(document);
    }

    @GetMapping("/{id}")
    public Document findById(@PathVariable String id) {
        return documentService.findById(id);
    }

}
