package com.baeldung.architecture.hexagonal.port;

import java.io.IOException;

public interface MovieLookup {
    void perform() throws IOException;
}
