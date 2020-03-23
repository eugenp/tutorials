package com.baeldung.port.outbound;

import com.baeldung.domain.Juice;

import java.util.List;

public interface JuiceDao {

    void addJuice(Juice juice);

    Juice getJuice(String name);

    List<Juice> getAllJuice();

}
