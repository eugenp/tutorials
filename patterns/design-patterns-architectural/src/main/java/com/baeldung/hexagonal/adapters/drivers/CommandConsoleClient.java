package com.baeldung.hexagonal.adapters.drivers;

import java.util.List;

import com.baeldung.hexagonal.application.DocumentConsoleService;
import com.baeldung.hexagonal.domain.DocumentConsole;
import com.baeldung.hexagonal.domain.DocumentConsoleDto;
import com.baeldung.hexagonal.ports.drivers.CommandInterface;

public class CommandConsoleClient implements CommandInterface {

    private DocumentConsoleService service;

    public CommandConsoleClient(DocumentConsoleService service) {
        super();
        this.service = service;
    }

    @Override
    public void create(DocumentConsoleDto documentConsoleDto) {
        this.service.createDocumentConsole(documentConsoleDto);
    }

    @Override
    public void delete(String id) {
        this.service.removeDocumentConsole(id);
    }

    @Override
    public void update(DocumentConsoleDto documentConsoleDto) {
        this.service.updateDocumentConsole(documentConsoleDto);
    }

    @Override
    public List<DocumentConsole> findAll() {
        return this.service.getAllDocumentConsole();
    }

    @Override
    public DocumentConsole findById(String id) {
        return this.service.getDocumentConsoleById(id);
    }

}
