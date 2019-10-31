package com.baeldung.config;

import com.baeldung.theme.resolver.UserPreferenceThemeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

@Configuration
@ComponentScan(basePackages="com.baeldung")
@EnableWebMvc
public class ThemeMVCConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/themes/**").addResourceLocations("classpath:/themes/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(themeChangeInterceptor());
    }

    @Bean
    public ThemeChangeInterceptor themeChangeInterceptor() {
        ThemeChangeInterceptor interceptor = new ThemeChangeInterceptor();
        interceptor.setParamName("theme");
        return interceptor;
    }

    @Bean
    public ResourceBundleThemeSource resourceBundleThemeSource() {
        ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
        themeSource.setFallbackToSystemLocale(true);
        return themeSource;
    }

    @Bean
//    @Profile("cookie")
    public ThemeResolver themeResolver() {
        UserPreferenceThemeResolver themeResolver = new UserPreferenceThemeResolver();
        themeResolver.setDefaultThemeName("light");
        return themeResolver;
    }

//    @Bean
//    @Profile("database")
//    public ThemeResolver databaseThemeResolver() {
//        UserPreferenceThemeResolver themeResolver = new UserPreferenceThemeResolver();
//        themeResolver.setDefaultThemeName("light");
//        return themeResolver;
//    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry resolverRegistry) {
        resolverRegistry.jsp();
    }
}
