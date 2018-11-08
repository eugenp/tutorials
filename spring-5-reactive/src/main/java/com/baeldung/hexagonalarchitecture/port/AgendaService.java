package com.baeldung.hexagonalarchitecture.port;

import com.baeldung.hexagonalarchitecture.domain.Agenda;

import java.util.Collection;

public interface AgendaService {

    public void add(Agenda agenda);

    public Collection<Agenda> list();
}
