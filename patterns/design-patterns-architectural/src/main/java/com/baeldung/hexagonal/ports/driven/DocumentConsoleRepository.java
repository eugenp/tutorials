package com.baeldung.hexagonal.ports.driven;

import java.util.List;

import com.baeldung.hexagonal.domain.DocumentConsole;
import com.baeldung.hexagonal.domain.DocumentConsoleDto;

public interface DocumentConsoleRepository {

    void saveDocumentConsole(DocumentConsole documentConsole);

    void deleteDocumentConsole(String id);

    List<DocumentConsole> findAllDocumentConsole();

    DocumentConsole findDocumentConsoleById(String id);

}
