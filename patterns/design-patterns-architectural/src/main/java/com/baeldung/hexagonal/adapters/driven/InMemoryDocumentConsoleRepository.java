package com.baeldung.hexagonal.adapters.driven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baeldung.hexagonal.domain.DocumentConsole;
import com.baeldung.hexagonal.ports.driven.DocumentConsoleRepository;

public class InMemoryDocumentConsoleRepository implements DocumentConsoleRepository {

    private Map<String, DocumentConsole> map = new HashMap<>();

    @Override
    public void saveDocumentConsole(DocumentConsole documentConsole) {
        map.put(documentConsole.getId(), documentConsole);
    }

    @Override
    public void deleteDocumentConsole(String id) {
        map.remove(id);

    }

    @Override
    public List<DocumentConsole> findAllDocumentConsole() {
        return new ArrayList<DocumentConsole>(map.values());
    }

    @Override
    public DocumentConsole findDocumentConsoleById(String id) {
        return map.get(id);
    }

}
