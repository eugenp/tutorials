package org.baeldung.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = { "org.baeldung.web.controller"})
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    Environment env;

    public MvcConfig() {
        super();
    }

    // API

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/login.html");
        registry.addViewController("/home.html");

    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // For examples using Spring 4.1.0
        if ((env.getProperty("resource.handler.conf")).equals("4.1.0")) {
            registry.addResourceHandler("/js/**")
                    .addResourceLocations("/js/")
                    .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                    .resourceChain(false)
                    .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
//                    .addResolver(new VersionResourceResolver().addFixedVersionStrategy("1.2", "/**"))
                    .addResolver(new GzipResourceResolver())
                    .addResolver(new PathResourceResolver());

            registry.addResourceHandler("/resources/**")
                    .addResourceLocations("/resources/", "classpath:/other-resources/")
                    .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                    .resourceChain(false)
                    .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                    .addTransformer(new CssLinkResourceTransformer());

            registry.addResourceHandler("/files/**")
                    .addResourceLocations("file:/Users/Elena/")
                    .setCachePeriod(3600).resourceChain(true)
                    .addResolver(new PathResourceResolver());

            registry.addResourceHandler("/other-files/**")
                    .addResourceLocations("file:/Users/Elena/")
                    .setCachePeriod(3600)
                    .resourceChain(true)
                    .addResolver(new GzipResourceResolver());
        }
        // For examples using Spring 4.0.7
        else if ((env.getProperty("resource.handler.conf")).equals("4.0.7")) {
            registry.addResourceHandler("/resources/**")
                    .addResourceLocations("/", "/resources/", "classpath:/other-resources/");

            registry.addResourceHandler("/files/**")
                    .addResourceLocations("file:/Users/Elena/");

        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

}