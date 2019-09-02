package com.baeldung.hexagonal.ports;

public interface DataReceiverService {
    void process(int id, Object data) throws Exception;
}
