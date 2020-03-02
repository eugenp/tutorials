package com.baeldung.spring;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
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
    // @Bean
    // public HttpMessageConverter<Object> createXmlHttpMessageConverter() {
    // final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
    //
    // final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
    // xstreamMarshaller.setAutodetectAnnotations(true);
    // xmlConverter.setMarshaller(xstreamMarshaller);
    // xmlConverter.setUnmarshaller(xstreamMarshaller);
    //
    // return xmlConverter;
    // }

    // Etags

    // If we're not using Spring Boot we can make use of
    // AbstractAnnotationConfigDispatcherServletInitializer#getServletFilters
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>( new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("/foos/*");
        filterRegistrationBean.setName("etagFilter");
        return filterRegistrationBean;
    }
    
    // We can also just declare the filter directly
    // @Bean
    // public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
    // return new ShallowEtagHeaderFilter();
    // }

}