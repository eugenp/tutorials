package com.baeldung.hexagonal.architecture.ports;

import java.io.IOException;

public interface HttpPort {

    Object doGet(String url) throws IOException, InterruptedException;
}
