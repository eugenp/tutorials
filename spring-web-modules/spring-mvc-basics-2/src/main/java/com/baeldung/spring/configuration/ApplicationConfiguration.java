package com.baeldung.spring.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.baeldung.spring.controller.rss.ArticleRssFeedViewResolver;
import com.baeldung.spring.controller.rss.JsonChannelHttpMessageConverter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.baeldung.springmvcforms", "com.baeldung.spring.controller", "com.baeldung.spring.validator", "com.baeldung.spring.mail", "com.baeldung.spring.service" })
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ContentNegotiatingViewResolver viewResolver(ContentNegotiationManager cnManager) {
        ContentNegotiatingViewResolver cnvResolver = new ContentNegotiatingViewResolver();
        cnvResolver.setContentNegotiationManager(cnManager);
        List<ViewResolver> resolvers = new ArrayList<>();

        InternalResourceViewResolver bean = new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
        ArticleRssFeedViewResolver articleRssFeedViewResolver = new ArticleRssFeedViewResolver();

        resolvers.add(bean);
        resolvers.add(articleRssFeedViewResolver);

        cnvResolver.setViewResolvers(resolvers);
        return cnvResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5242880);
        return multipartResolver;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(new RssChannelHttpMessageConverter());
        converters.add(new JsonChannelHttpMessageConverter());
    }

}
