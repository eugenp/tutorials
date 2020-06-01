package com.baeldung.hexagonal.framework;

import com.baeldung.hexagonal.application.DocumentationHUDPort;
import com.baeldung.hexagonal.domain.Documentation;
import com.baeldung.hexagonal.service.DocumentationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/documentation/") public class DocumentationController implements DocumentationHUDPort {

        private final DocumentationService documentationService;

        public DocumentationController(DocumentationService documentationService) {
                this.documentationService = documentationService;
        }

        @Override public void create(@RequestBody String request) {
                documentationService.create(request);
        }

        @Override public Documentation view(@PathVariable Long id) {
                return documentationService.getDocumentation(id);
        }
}
