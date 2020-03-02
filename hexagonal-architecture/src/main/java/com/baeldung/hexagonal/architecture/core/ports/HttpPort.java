package com.baeldung.hexagonal.architecture.core.ports;

import java.io.IOException;

public interface HttpPort {

    Object doGet(String url) throws IOException, InterruptedException;
}
