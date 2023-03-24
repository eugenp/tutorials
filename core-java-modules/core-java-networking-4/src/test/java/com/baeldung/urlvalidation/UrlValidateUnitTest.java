package com.baeldung.urlvalidation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.baeldung.urlvalidation.UrlValidation;

public class UrlValidateUnitTest {
    
    @Test
    public void givenValidStringAsURL_whenUsingJDK_shouldReturnTrue() throws MalformedURLException, URISyntaxException {
        UrlValidation urlValidator = new UrlValidation();
        assertTrue(urlValidator.isValidURLJavaNet("http://baeldung.com/"));
    }
    
    @Test
    public void givenInvalidStringAsURL_whenUsingJDK_shouldReturnFalse() throws MalformedURLException, URISyntaxException {
        UrlValidation urlValidator = new UrlValidation();
        assertFalse(urlValidator.isValidURLJavaNet("https://www.baeldung.com/ java-%%$^&& iuyi"));
    }
    
    @Test
    public void givenInvalidStringAsURL_whenUsingJDK_shouldReturnTrue() throws MalformedURLException {
        UrlValidation urlValidator = new UrlValidation();
        assertTrue(urlValidator.invalidUrlAsValidJavaNet("https://www.baeldung.com/ java-%%$^&& iuyi"));
    }
    
    @Test
    public void givenValidStringAsURL_whenUsingApache_shouldReturnTrue() throws MalformedURLException {
        UrlValidation urlValidator = new UrlValidation();
        assertTrue(urlValidator.isValidURLApache("http://baeldung.com/"));
    }
    
    @Test
    public void givenInvalidStringAsURL_whenUsingApache_shouldReturnFalse() throws MalformedURLException {
        UrlValidation urlValidator = new UrlValidation();
        assertFalse(urlValidator.isValidURLApache("https://www.baeldung.com/ java-%%$^&& iuyi"));
    }
}