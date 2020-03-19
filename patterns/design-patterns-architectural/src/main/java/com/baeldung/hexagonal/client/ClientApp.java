package com.baeldung.hexagonal.client;

import java.util.List;

import com.baeldung.hexagonal.adapters.driven.InMemoryDocumentConsoleRepository;
import com.baeldung.hexagonal.adapters.drivers.CommandConsoleClient;
import com.baeldung.hexagonal.application.DocumentConsoleService;
import com.baeldung.hexagonal.application.DocumentConsoleServiceImpl;
import com.baeldung.hexagonal.domain.DocumentConsole;
import com.baeldung.hexagonal.domain.DocumentConsoleDto;
import com.baeldung.hexagonal.ports.driven.DocumentConsoleRepository;
import com.baeldung.hexagonal.ports.drivers.CommandInterface;

public class ClientApp {

    private static CommandInterface commandInterface;

    public static void register() {
        DocumentConsoleRepository repository = new InMemoryDocumentConsoleRepository();
        DocumentConsoleService service = new DocumentConsoleServiceImpl(repository);
        commandInterface = new CommandConsoleClient(service);
    }

    public static void main(String[] args) {
        register();
        DocumentConsoleDto documentConsoleDto = new DocumentConsoleDto("payment order", "very important");
        commandInterface.create(documentConsoleDto);
        List<DocumentConsole> list = commandInterface.findAll();
        for (DocumentConsole doc : list) {
            System.out.println(doc.getId());
            System.out.println(doc.getName());
            System.out.println(doc.getCreationDate());
            System.out.println(doc.getNote());
        }

    }

}
