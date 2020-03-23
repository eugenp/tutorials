package com.baeldung.port.inbound;

import com.baeldung.domain.Juice;

import java.util.List;

public interface JuiceService {

    Juice retrieveJuice(String name);

    void createJuice(Juice juice);

    List<Juice> listJuices();

}
