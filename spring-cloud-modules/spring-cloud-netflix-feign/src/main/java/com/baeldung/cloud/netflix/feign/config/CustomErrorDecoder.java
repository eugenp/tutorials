package com.baeldung.cloud.netflix.feign.config;

import com.baeldung.cloud.netflix.feign.exception.BadRequestException;
import com.baeldung.cloud.netflix.feign.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                return new NotFoundException();
            default:
                return new Exception("Generic error");
        }
    }
}
