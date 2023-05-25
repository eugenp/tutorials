package com.baeldung.cloud.openfeign.client;

import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.cloud.openfeign.defaulterrorhandling.model.FormData;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(name = "form-client", url = "http://localhost:8085/api", configuration = FormFeignEncoderConfig.class)
public interface FormClient {

    @PostMapping(value = "/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void postFormData(@RequestBody FormData data);

    @PostMapping(value = "/form/map", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void postFormMapData(Map<String, ?> data);

}

class FormFeignEncoderConfig {

    @Bean
    public Encoder encoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }

}