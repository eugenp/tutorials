package com.baeldung.hexagonal.application;

import java.util.List;

import com.baeldung.hexagonal.domain.DocumentConsole;
import com.baeldung.hexagonal.domain.DocumentConsoleDto;

public interface DocumentConsoleService {

    void createDocumentConsole(DocumentConsoleDto documentConsoleDto);

    void removeDocumentConsole(String id);

    void updateDocumentConsole(DocumentConsoleDto documentConsoleDto);

    List<DocumentConsole> getAllDocumentConsole();

    DocumentConsole getDocumentConsoleById(String id);

}
