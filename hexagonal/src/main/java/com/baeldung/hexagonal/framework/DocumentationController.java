package com.baeldung.hexagonal.framework;

import com.baeldung.hexagonal.service.DocumentationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/documentation/") public class DocumentationController {

        private final DocumentationService documentationService;

        public DocumentationController(DocumentationService documentationService) {
                this.documentationService = documentationService;
        }

        @PostMapping(value = "add/{className}/{methodName}/{methodDescription}") public void add(@PathVariable String className, @PathVariable String methodName, @PathVariable String methodDescription) {
                documentationService.add(className, methodName, methodDescription);
        }

        @PostMapping(value = "remove/{className}/{methodName}") public boolean remove(@PathVariable String className, @PathVariable String methodName) {
                return documentationService.remove(className, methodName);

        }

}
