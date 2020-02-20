package com.baeldung.hexagonal.ports.driver;

import com.baeldung.hexagonal.bridge.IBridge;

public interface IModify<INPUT, OUTPUT> {
    OUTPUT execute(INPUT params);

    IBridge<INPUT, OUTPUT> getBridge();
}
