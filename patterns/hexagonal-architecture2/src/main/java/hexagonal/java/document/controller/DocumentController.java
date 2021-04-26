package hexagonal.java.document.controller;

import hexagonal.java.document.model.Document;
import hexagonal.java.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createDocument(@RequestBody Document document) {
        documentService.createDocument(document);
    }

    @GetMapping("/read/{documentId}")
    @ResponseBody
    public Document getDocumentById(@PathVariable Long documentId) {
        return documentService.getDocumentById(documentId);
    }

    @PutMapping("/update")
    public void changeDocumentName(@RequestParam Long documentId,
                                   @RequestParam String newName) {
        documentService.changeDocumentName(documentId, newName);
    }

    @DeleteMapping("/delete/{documentId}")
    public void deleteDocumentById(@PathVariable Long documentId) {
        documentService.deleteDocumentById(documentId);
    }
}
