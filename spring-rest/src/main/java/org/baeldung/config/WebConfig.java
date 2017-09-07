package org.baeldung.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * Please note that main web configuration is in src/main/webapp/WEB-INF/api-servlet.xml
 */
@Configuration
@EnableWebMvc
@ComponentScan({ "org.baeldung.web" })
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
        super();
    }

    //
    /*
        @Override
        public void configureMessageConverters(final List<HttpMessageConverter<?>> messageConverters) {
            final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
            builder.indentOutput(true)
                .dateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm"));
            messageConverters.add(new MappingJackson2HttpMessageConverter(builder.build()));
            // messageConverters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()));

            // messageConverters.add(createXmlHttpMessageConverter());
            // messageConverters.add(new MappingJackson2HttpMessageConverter());

            // messageConverters.add(new ProtobufHttpMessageConverter());
            super.configureMessageConverters(messageConverters);
        }

        private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
            final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();

            final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
            xmlConverter.setMarshaller(xstreamMarshaller);
            xmlConverter.setUnmarshaller(xstreamMarshaller);

            return xmlConverter;
        }
    */
}
