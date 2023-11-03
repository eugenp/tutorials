package com.baeldung.restvalidation.config;

import javax.validation.MessageInterpolator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:CustomValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public MessageInterpolator getMessageInterpolator(MessageSource messageSource) {
        MessageSourceResourceBundleLocator resourceBundleLocator = new MessageSourceResourceBundleLocator(messageSource);
        ResourceBundleMessageInterpolator messageInterpolator = new ResourceBundleMessageInterpolator(resourceBundleLocator);
        return new RecursiveLocaleContextMessageInterpolator(messageInterpolator);
    }

    @Bean
    public LocalValidatorFactoryBean getValidator(MessageInterpolator messageInterpolator) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setMessageInterpolator(messageInterpolator);
        return bean;
    }

}