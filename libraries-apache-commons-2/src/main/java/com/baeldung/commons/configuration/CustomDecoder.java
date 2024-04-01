package com.baeldung.commons.configuration;

import org.apache.commons.configuration2.ConfigurationDecoder;
import org.apache.commons.net.util.Base64;

public class CustomDecoder implements ConfigurationDecoder {

    public static final String DUMMY_CONSTANT = "dummyConstant";

    @Override
    public String decode(String s) {
        return new String(Base64.decodeBase64(s));
    }
}
