package com.habuma.spitter.client;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class CustomErrorHandler extends DefaultResponseErrorHandler {

  public boolean hasError(ClientHttpResponse response) throws IOException {
    HttpStatus statusCode = response.getStatusCode();
    if(statusCode == HttpStatus.NOT_FOUND) {
      return false;
    }
    
    return statusCode.series() == Series.CLIENT_ERROR || 
           statusCode.series() == Series.SERVER_ERROR;
  }
  
}
