package com.baeldung.spring;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// If we want to enable xml configurations for message-converter:
// @ImportResource("classpath:WEB-INF/api-servlet.xml")
public class WebConfig implements WebMvcConfigurer {

    // @Override
    // public void configureMessageConverters(final List<HttpMessageConverter<?>> messageConverters) {
    // messageConverters.add(new MappingJackson2HttpMessageConverter());
    // messageConverters.add(createXmlHttpMessageConverter());
    // }
    //
    // private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
    // final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
    //
    // final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
    // xstreamMarshaller.setAutodetectAnnotations(true);
    // xmlConverter.setMarshaller(xstreamMarshaller);
    // xmlConverter.setUnmarshaller(xstreamMarshaller);
    //
    // return xmlConverter;
    // }

    // Another possibility is to create a bean which will be automatically added to the Spring Boot Autoconfigurations
//    @Bean
//    public HttpMessageConverter<Object> createXmlHttpMessageConverter() {
//        final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
//
//        final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
//        xstreamMarshaller.setAutodetectAnnotations(true);
//        xmlConverter.setMarshaller(xstreamMarshaller);
//        xmlConverter.setUnmarshaller(xstreamMarshaller);
//
//        return xmlConverter;
//    }

}