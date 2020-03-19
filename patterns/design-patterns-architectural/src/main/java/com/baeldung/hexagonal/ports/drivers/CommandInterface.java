package com.baeldung.hexagonal.ports.drivers;

import java.util.List;

import com.baeldung.hexagonal.domain.DocumentConsole;
import com.baeldung.hexagonal.domain.DocumentConsoleDto;

public interface CommandInterface {

    void create(DocumentConsoleDto documentConsoleDto);

    void delete(String id);

    void update(DocumentConsoleDto documentConsoleDto);

    List<DocumentConsole> findAll();

    DocumentConsole findById(String id);

}
