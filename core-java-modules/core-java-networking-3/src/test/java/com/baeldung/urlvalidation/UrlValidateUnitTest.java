package com.baeldung.networking.url;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.baeldung.networking.urlvalidation.UrlValidation;

public class UrlValidateUnitTest {
    
    @Test
    public void javaNet_givenValidStringAsURL_isTrue() throws MalformedURLException, URISyntaxException {
        UrlValidation urlValidator = new UrlValidation();
        assertTrue(urlValidator.isValidURLJavaNet("http://baeldung.com/"));
    }
    
    @Test
    public void javaNet_givenInValidStringAsURL_isFalse() throws MalformedURLException, URISyntaxException {
        UrlValidation urlValidator = new UrlValidation();
        assertFalse(urlValidator.isValidURLJavaNet("https://www.baeldung.com/ java-%%$^&& iuyi"));
    }
    
    @Test
    public void javaNet_givenInValidStringAsURL_isTrue() throws MalformedURLException {
        UrlValidation urlValidator = new UrlValidation();
        assertTrue(urlValidator.inValidUrlAsValidJavaNet("https://www.baeldung.com/ java-%%$^&& iuyi"));
    }
    
    @Test
    public void apache_givenValidStringAsURL_isTrue() throws MalformedURLException {
        UrlValidation urlValidator = new UrlValidation();
        assertTrue(urlValidator.isValidURLApache("http://baeldung.com/"));
    }
    
    @Test
    public void apache_givenInValidStringAsURL_isFalse() throws MalformedURLException {
        UrlValidation urlValidator = new UrlValidation();
        assertFalse(urlValidator.isValidURLApache("https://www.baeldung.com/ java-%%$^&& iuyi"));
    }
    
}
