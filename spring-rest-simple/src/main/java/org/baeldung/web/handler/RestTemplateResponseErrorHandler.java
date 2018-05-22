package org.baeldung.web.handler;

import org.baeldung.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);


    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR || httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR) {
            this.handleServerError(httpResponse);
        } else if (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
            this.handleClientError(httpResponse);
        }
    }

    private void handleServerError(ClientHttpResponse httpResponse) {
        //Handle Server specific errors
    }

    private void handleClientError(ClientHttpResponse httpResponse) throws IOException {
        //Handle Client specific errors
        if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            //Log details here...
            LOGGER.info("Throwing NotFoundException...");
            throw new NotFoundException();
        }
    }
}
