package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Documentation;
import com.baeldung.hexagonal.repository.DocumentationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service public class DocumentationService implements DocumentationRepository {

        private HashMap<Long, Documentation> documentations = new HashMap<>();

        @Override public void create(String content) {
                Documentation documentation = new Documentation(content);
                documentations.put(documentation.getId(), documentation);
        }

        @Override public Documentation getDocumentation(Long id) {
                return documentations.get(id);
        }
}
