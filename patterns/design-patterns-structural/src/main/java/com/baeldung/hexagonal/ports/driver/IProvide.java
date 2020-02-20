package com.baeldung.hexagonal.ports.driver;

import com.baeldung.hexagonal.bridge.IBridge;

import java.util.List;

public interface IProvide<INPUT, OUTPUT> {
    List<OUTPUT> list();

    IBridge<INPUT, OUTPUT> getBridge();
}
