package com.baeldung.hexagonal.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.baeldung.hexagonal.domain.DocumentConsole;
import com.baeldung.hexagonal.domain.DocumentConsoleDto;
import com.baeldung.hexagonal.ports.driven.DocumentConsoleRepository;

public class DocumentConsoleServiceImpl implements DocumentConsoleService {

    private DocumentConsoleRepository repository;

    public DocumentConsoleServiceImpl(DocumentConsoleRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public void createDocumentConsole(DocumentConsoleDto documentConsoleDto) {
        String uniqueID = UUID.randomUUID()
            .toString();
        DocumentConsole documentConsole = new DocumentConsole(uniqueID, documentConsoleDto.getName(), LocalDateTime.now(), documentConsoleDto.getNote());
        this.repository.saveDocumentConsole(documentConsole);
    }

    @Override
    public void removeDocumentConsole(String id) {
        this.repository.deleteDocumentConsole(id);

    }

    @Override
    public void updateDocumentConsole(DocumentConsoleDto documentConsoleDto) {
        DocumentConsole documentConsole = this.repository.findDocumentConsoleById(documentConsoleDto.getId());
        documentConsole.setName(documentConsoleDto.getName());
        documentConsole.setNote(documentConsoleDto.getNote());
        this.repository.saveDocumentConsole(documentConsole);
    }

    @Override
    public List<DocumentConsole> getAllDocumentConsole() {
        return this.repository.findAllDocumentConsole();
    }

    @Override
    public DocumentConsole getDocumentConsoleById(String id) {
        return this.repository.findDocumentConsoleById(id);
    }

}
