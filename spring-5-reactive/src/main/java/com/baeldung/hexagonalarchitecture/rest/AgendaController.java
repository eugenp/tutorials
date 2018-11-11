package com.baeldung.hexagonalarchitecture.rest;

import com.baeldung.hexagonalarchitecture.domain.Agenda;
import com.baeldung.hexagonalarchitecture.port.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping
    public Collection<Agenda> findAllEntries(){
        return agendaService.list();

    }

    @PostMapping
    public void addToAgenda(@RequestBody Map<String,String> payload){
        String name = payload.get("name");
        Integer number = Integer.parseInt(payload.get("number"));

        agendaService.add(new Agenda(name, number));
    }
}
