package com.baeldung.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;

/**
 * Another possibility is to create a bean which will be automatically added to the Spring Boot Autoconfigurations.
 *
 * ATTENTION: Multiple converter registration of the same type most likely causes problem (serialize twice, etc.)
 * Therefore, be sure to remove manually added XML message converter first then uncomment
 * this @{@link org.springframework.context.annotation.Configuration} to use
 */
//@Configuration
public class ConverterExtensionsConfig {

    @Bean
    public HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();

        final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xstreamMarshaller.setAutodetectAnnotations(true);
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);

        return xmlConverter;
    }

}
