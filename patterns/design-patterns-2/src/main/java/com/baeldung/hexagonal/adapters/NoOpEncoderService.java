package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.EncoderService;

public class NoOpEncoderService implements EncoderService {
    public String encode(Object data) throws Exception {
        if (data == null) {
            return "";
        }
        return data.toString();
    }
}
