package com.baeldung.sneakythrows;

import java.io.IOException;

public class SneakyThrows {


    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

    public static void throwsSneakyIOException() {
        sneakyThrow(new IOException("sneaky"));
    }


    public static void main(String[] args) {
        try {
            throwsSneakyIOException();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
