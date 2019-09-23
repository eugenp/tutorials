package com.baeldung.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baeldung.config.converter.KryoHttpMessageConverter;
import com.baeldung.config.converter.StringToEnumConverter;

import java.text.SimpleDateFormat;
import java.util.List;

/*
 * Please note that main web configuration is in src/main/webapp/WEB-INF/api-servlet.xml
 */
@Configuration
@EnableWebMvc
@ComponentScan({ "com.baeldung.web", "com.baeldung.requestmapping" })
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig() {
        super();
    }

    //

    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> messageConverters) {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true)
            .dateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm"));
        messageConverters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        messageConverters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true)
            .build()));

        messageConverters.add(createXmlHttpMessageConverter());
        // messageConverters.add(new MappingJackson2HttpMessageConverter());

        messageConverters.add(new ProtobufHttpMessageConverter());
        messageConverters.add(new KryoHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
    }

    private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();

        final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);

        return xmlConverter;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
       registry.addConverter(new StringToEnumConverter());
    }
}
