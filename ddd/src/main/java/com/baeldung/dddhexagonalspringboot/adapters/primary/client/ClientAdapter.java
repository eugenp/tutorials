package com.baeldung.dddhexagonalspringboot.adapters.primary.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.dddhexagonalspringboot.port.inbound.ClientPort;

@RestController
@RequestMapping("/clients")
public class ClientAdapter {

    @Autowired
    private ClientPort clientPort;

    @GetMapping("/get/{id}")
    public WorkOrderClientDTO get(@PathVariable Long id) {
        return WorkOrderClientDTO.fromDomain(clientPort.get(id));
    }

    @GetMapping("/new")
    public Long create() {
        return clientPort.createWorkOrder();
    }

}
