package com.baeldung.spring.web.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

//@EnableWebMvc
//@ComponentScan(basePackages = { "com.baeldung.web.controller" })
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/")
            .setViewName("index");
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(10000000L));
        factory.setMaxRequestSize(DataSize.ofBytes(10000000L));
        return factory.createMultipartConfig();
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        bean.setOrder(2);
        return bean;
    }

    /** Static resource locations including themes*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("/", "/resources/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }

    /** BEGIN theme configuration */
    @Bean
    public ResourceBundleThemeSource themeSource() {
        ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
        themeSource.setDefaultEncoding("UTF-8");
        themeSource.setBasenamePrefix("themes.");
        return themeSource;
    }

    @Bean
    public CookieThemeResolver themeResolver() {
        CookieThemeResolver resolver = new CookieThemeResolver();
        resolver.setDefaultThemeName("default");
        resolver.setCookieName("example-theme-cookie");
        return resolver;
    }

    @Bean
    public ThemeChangeInterceptor themeChangeInterceptor() {
        ThemeChangeInterceptor interceptor = new ThemeChangeInterceptor();
        interceptor.setParamName("theme");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(themeChangeInterceptor());
    }

    /** END theme configuration */

    @Bean
    public BeanNameViewResolver beanNameViewResolver(){
        BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        beanNameViewResolver.setOrder(1);
        return beanNameViewResolver;
    }

    @Bean
    public View sample() {
        return new JstlView("/WEB-INF/view/sample.jsp");
    }

    @Bean
    public View sample2() {
        return new JstlView("/WEB-INF/view2/sample2.jsp");
    }

    @Bean
    public View sample3(){
        return new JstlView("/WEB-INF/view3/sample3.jsp");
    }

}