package com.baeldung.mockito.mocksettings;

public class SimpleService {

    public SimpleService(SpecialInterface special) {
        Runnable runnable = (Runnable) special;
        runnable.run();
    }

}
