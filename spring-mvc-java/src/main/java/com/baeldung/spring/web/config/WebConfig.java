package com.baeldung.spring.web.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;
import org.springframework.web.util.UrlPathHelper;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.baeldung.excel.ExcelPOIHelper;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.baeldung.web.controller" })
public class WebConfig implements WebMvcConfigurer {

	 @Autowired
	    private ServletContext ctx;
	
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
    
    @Bean
    public ViewResolver thymeleafViewResolver() {
        final ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        bean.setOrder(0);
        return bean;
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ServletContextTemplateResolver templateResolver() {
        final ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(ctx);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    @Description("Spring message resolver")
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(true).useRegisteredExtensionsOnly(false).defaultContentType(MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML).mediaType("json",
                MediaType.APPLICATION_JSON);
    }
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {

        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);

        return multipartResolver;
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