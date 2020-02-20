package com.baeldung.hexagonal.bridge;

import com.baeldung.hexagonal.ports.driven.INotify;

import java.util.List;

public interface IBridge<INPUT, OUTPUT> {
    OUTPUT create(INPUT input);

    List<OUTPUT> list();

    INotify getNotifier();
}
