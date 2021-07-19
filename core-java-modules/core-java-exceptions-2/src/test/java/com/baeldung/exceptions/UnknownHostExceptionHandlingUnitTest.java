package com.baeldung.exceptions;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

public class UnknownHostExceptionHandlingUnitTest {

    @Test(expected = UnknownHostException.class)
    public void givenUnknownHost_whenResolve_thenUnknownHostException() throws IOException {
        UnknownHostExceptionHandling.getResponseCodeUnhandled("http://locaihost");
    }
    
}
