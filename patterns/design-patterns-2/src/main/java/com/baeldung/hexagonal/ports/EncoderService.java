package com.baeldung.hexagonal.ports;

public interface EncoderService {
    String encode(Object data) throws Exception;
}
