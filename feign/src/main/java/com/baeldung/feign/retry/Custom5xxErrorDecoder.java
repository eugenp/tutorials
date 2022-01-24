package com.baeldung.feign.retry;


import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import static feign.FeignException.errorStatus;

public class Custom5xxErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = errorStatus(methodKey, response);
        int status = response.status();
        if (status >= 500) {
            return new RetryableException(
              response.status(),
              exception.getMessage(),
              response.request().httpMethod(),
              exception,
              null,
              response.request());
        }
        return exception;
    }
}
