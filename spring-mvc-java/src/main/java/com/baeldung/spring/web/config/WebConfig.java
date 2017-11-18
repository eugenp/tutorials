package com.baeldung.spring.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;
import org.springframework.web.util.UrlPathHelper;
import com.baeldung.excel.*;

@Configuration
@EnableWebMvc
@ComponentScan("com.baeldung.web")
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
        super();
    }

    // @Bean
    // public StandardServletMultipartResolver multipartResolver() {
    // return new StandardServletMultipartResolver();
    // }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {

        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);

        return multipartResolver;
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/sample.html");
    }

    @Bean
    public ViewResolver internalResourceViewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        bean.setOrder(2);
        return bean;
    }

    @Bean
    public ViewResolver xmlViewResolver() {
        final XmlViewResolver bean = new XmlViewResolver();
        bean.setLocation(new ClassPathResource("views.xml"));
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public ViewResolver resourceBundleViewResolver() {
        final ResourceBundleViewResolver bean = new ResourceBundleViewResolver();
        bean.setBasename("views");
        bean.setOrder(0);
        return bean;
    }

    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        converters.add(byteArrayHttpMessageConverter());
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        final ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());

        return arrayHttpMessageConverter;
    }

    private List<MediaType> getSupportedMediaTypes() {
        final List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.IMAGE_JPEG);
        list.add(MediaType.IMAGE_PNG);
        list.add(MediaType.APPLICATION_OCTET_STREAM);

        return list;
    }

    @Override
    public void configurePathMatch(final PathMatchConfigurer configurer) {
        final UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);

        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Bean
    public ExcelPOIHelper excelPOIHelper() {
        return new ExcelPOIHelper();
    }

}
