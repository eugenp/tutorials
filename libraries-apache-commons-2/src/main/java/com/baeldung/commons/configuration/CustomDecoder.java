package com.baeldung.commons.configuration;

import org.apache.commons.configuration2.ConfigurationDecoder;
import org.apache.commons.net.util.Base64;

public class CustomDecoder implements ConfigurationDecoder {

    @Override
    public String decode(String encodedValue) {
        return new String(Base64.decodeBase64(encodedValue));
    }
}
