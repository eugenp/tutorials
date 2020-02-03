package com.baeldung.hexagonal.bridge;

import java.util.List;

public interface IBridge<INPUT, OUTPUT> {

    OUTPUT create(INPUT input);

    List<OUTPUT> list();
}
