package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.LoaderPort;

public class ConsoleLoaderAdapter implements LoaderPort {
    public void loadData(byte[] data) {
        System.out.println("Data loaded: " + new String(data));
    }
}
