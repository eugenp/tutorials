package com.baeldung.hexagonal.calculator.ports;

import java.util.List;

public interface Reader {
    List<Integer> read();
}
